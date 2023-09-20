package hs.kr.equus.application.domain.score.exception

import hs.kr.equus.application.global.exception.BusinessException

sealed class ScoreExceptions(
    override val status: Int,
    override val message: String
) : BusinessException(status, message) {

    companion object {

    }
}
