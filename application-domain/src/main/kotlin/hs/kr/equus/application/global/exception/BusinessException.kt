package hs.kr.equus.application.global.exception

/**
 *
 * 원하는 상황에 예외를 발생시켜 알맞게 처리하기 위해 RuntimeException을 상속받은 BusinessException
 *
 * @author kimseongwon
 * @date 2023/09/20
 * @version 1.0.0
 **/
abstract class BusinessException(
    open val status: Int,
    override val message: String
) : RuntimeException()
