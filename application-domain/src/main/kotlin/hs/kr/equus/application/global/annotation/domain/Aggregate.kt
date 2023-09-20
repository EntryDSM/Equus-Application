package hs.kr.equus.application.global.annotation.domain

/**
 *
 * 모델의 일관성을 관리하는 기준이 되는 Aggregate를 나타내는 어노테이션
 *
 * @author kimseongwon
 * @date 2023/09/20
 * @version 1.0.0
 **/
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Aggregate()