package hs.kr.equus.application.domain.schedule.exception

import hs.kr.equus.application.global.exception.BusinessException

sealed class ScheduleExceptions(
    override val status: Int,
    override val message: String,
) : BusinessException(status, message) {

    class ScoreNotFoundException(message: String = SCORE_NOT_FOUND_EXCEPTION):
        ScheduleExceptions(404, message)

    class AdmissionUnavailableException(message: String = ADMISSION_UNAVAILABLE):
        ScheduleExceptions(404, message)

    companion object {
        private const val SCORE_NOT_FOUND_EXCEPTION = "점수가 존재하지 않습니다"
        private const val ADMISSION_UNAVAILABLE = "합격여부를 확인할 수 없습니다"
    }
}