package hs.kr.equus.application.global.storage

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.internal.Mimetypes
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.util.IOUtils
import hs.kr.equus.application.domain.file.exception.FileExceptions
import hs.kr.equus.application.domain.file.spi.CheckFilePort
import hs.kr.equus.application.domain.file.spi.GenerateFileUrlPort
import hs.kr.equus.application.domain.file.spi.GetObjectPort
import hs.kr.equus.application.domain.file.spi.UploadFilePort
import org.springframework.stereotype.Component
import java.io.File
import java.io.IOException
import java.net.URLDecoder
import java.util.*


@Component
class AwsS3Adapter(
    private val awsProperties: AwsS3Properties,
    private val amazonS3Client: AmazonS3Client,
) : UploadFilePort, CheckFilePort, GenerateFileUrlPort, GetObjectPort {
    companion object {
        const val EXP_TIME = 1000 * 60 * 2
    }

    override fun upload(file: File, path: String): String {
        val fileName = UUID.randomUUID().toString() + file.name
        runCatching { inputS3(file, path, fileName) }
            .also { file.delete() }

        return fileName
    }

    override fun getObject(fileName: String, path: String): ByteArray {
        try {
            val `object` = amazonS3Client.getObject(awsProperties.bucket, path + fileName)
            return IOUtils.toByteArray(`object`.objectContent)
        } catch (e: Exception) {
            throw FileExceptions.PathNotFound()
        }
    }

    private fun inputS3(file: File, path: String, fileName: String) {
        try {
            val inputStream = file.inputStream()
            val objectMetadata = ObjectMetadata().apply {
                contentLength = file.length()
                contentType = Mimetypes.getInstance().getMimetype(file)
            }

            amazonS3Client.putObject(
                PutObjectRequest(
                    awsProperties.bucket,
                    path+fileName,
                    inputStream,
                    objectMetadata
                ).withCannedAcl(CannedAccessControlList.PublicRead)
            )
        } catch (e: IOException) {
            throw FileExceptions.IOInterrupted()
        }
    }

    override fun existsPath(path: String): Boolean {
        val key = URLDecoder.decode(path.substringAfterLast('/', ""), Charsets.UTF_8)
        return amazonS3Client.doesObjectExist(awsProperties.bucket, key)
    }

    override fun generateFileUrl(fileName: String, path: String): String {
        val expiration = Date().apply {
            time += EXP_TIME
            }
        return amazonS3Client.generatePresignedUrl(
            GeneratePresignedUrlRequest(
                awsProperties.bucket,
                "${path}$fileName"
            ).withMethod(HttpMethod.GET).withExpiration(expiration)
        ).toString()
        }
}
