package hs.kr.equus.application.global.annotation.domain

/**
 *
 * 구현 기술에 의존하지 않고 도메인과 밀접한 관련이 있는 DomainService를 나타내는 어노테이션
 *
 * @author kimseongwon
 * @date 2023/09/20
 * @version 1.0.0
 **/
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class DomainService()
