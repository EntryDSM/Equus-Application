package hs.kr.equus.application.domain.file.presentation;

import hs.kr.equus.application.domain.file.presentation.converter.ImageFileConverter
import hs.kr.equus.application.domain.file.presentation.dto.response.UploadImageWebResponse
import hs.kr.equus.application.domain.file.usecase.UploadImageUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/files")
class WebFileAdapter(
    private val uploadImageUseCase: UploadImageUseCase,
) {
    @PostMapping
    fun uploadSingleImage(file: MultipartFile): UploadImageWebResponse {
        return UploadImageWebResponse(
            uploadImageUseCase.execute(
                file.let(ImageFileConverter::transferTo)
            )
        )
    }
}
