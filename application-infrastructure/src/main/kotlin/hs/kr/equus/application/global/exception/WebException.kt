package hs.kr.equus.application.global.exception

abstract class WebException(
    open val status: Int,
    override val message: String,
) : RuntimeException()
