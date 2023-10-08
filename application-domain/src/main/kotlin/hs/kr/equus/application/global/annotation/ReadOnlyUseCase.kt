package hs.kr.equus.application.global.annotation

import org.springframework.transaction.annotation.Transactional

/**
 *
 * 조회 기능을 담당하는 사용자 UseCase를 나타내는 어노테이션
 *
 * @author kimseongwon
 * @date 2023/09/20
 * @version 1.0.0
 **/
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Transactional(readOnly = true)
annotation class ReadOnlyUseCase()
