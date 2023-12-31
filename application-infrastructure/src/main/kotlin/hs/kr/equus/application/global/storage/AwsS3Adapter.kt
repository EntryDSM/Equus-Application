package hs.kr.equus.application.global.storage

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.internal.Mimetypes
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import hs.kr.equus.application.domain.file.exception.FileExceptions
import hs.kr.equus.application.domain.file.spi.CheckFilePort
import hs.kr.equus.application.domain.file.spi.UploadFilePort
import org.springframework.stereotype.Component
import java.io.File
import java.io.IOException
import java.net.URLDecoder

@Component
class AwsS3Adapter(
    private val awsProperties: AwsS3Properties,
    private val amazonS3Client: AmazonS3Client,
) : UploadFilePort, CheckFilePort {
    override fun upload(file: File): String {
        runCatching { inputS3(file) }
            .also { file.delete() }
            .onFailure { e ->
                e.printStackTrace()
                throw e
            }

        return getResource(file.name)
    }

    private fun inputS3(file: File) {
        try {
            val inputStream = file.inputStream()
            val objectMetadata =
                ObjectMetadata().apply {
                    contentLength = file.length()
                    contentType = Mimetypes.getInstance().getMimetype(file)
                }

            amazonS3Client.putObject(
                PutObjectRequest(
                    awsProperties.bucket,
                    file.name,
                    inputStream,
                    objectMetadata,
                ).withCannedAcl(CannedAccessControlList.PublicRead),
            )
        } catch (e: IOException) {
            e.printStackTrace()
            throw FileExceptions.IOInterrupted()
        }
    }

    private fun getResource(fileName: String): String {
        return amazonS3Client.getResourceUrl(awsProperties.bucket, fileName)
    }

    override fun existsPath(path: String): Boolean {
        val key =
            path.substringAfterLast('/', "").run {
                URLDecoder.decode(this, Charsets.UTF_8)
            }

        return amazonS3Client.doesObjectExist(awsProperties.bucket, key)
    }
}
