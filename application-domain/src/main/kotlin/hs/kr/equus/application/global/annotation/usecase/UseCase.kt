package hs.kr.equus.application.global.annotation.usecase

import org.springframework.transaction.annotation.Transactional

/**
 *
 * 추가, 수정, 삭제 기능을 담당하는 사용자 UseCase를 나타내는 어노테이션
 *
 * @author kimseongwon
 * @date 2023/09/20
 * @version 1.0.0
 **/
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Transactional
annotation class UseCase()
