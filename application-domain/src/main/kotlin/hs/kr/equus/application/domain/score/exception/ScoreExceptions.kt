package hs.kr.equus.application.domain.score.exception

import hs.kr.equus.application.domain.graduationInfo.exception.GraduationInfoExceptions
import hs.kr.equus.application.global.exception.BusinessException

sealed class ScoreExceptions(
    override val status: Int,
    override val message: String,
) : BusinessException(status, message) {
    class ScoreNotFoundException(message: String = SCORE_NOT_FOUND) :
        ScoreExceptions(404, message)

    class ScoreExistsException(message: String = SCORE_EXISTS) :
        ScoreExceptions(409, message)

    companion object {
        private const val SCORE_NOT_FOUND = "해당 점수가 없습니다"
        private const val SCORE_EXISTS = "점수가 이미 존재합니다"
    }
}
