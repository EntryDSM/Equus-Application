package hs.kr.equus.application.global.annotation

/**
 *
 * 복잡한 도메인 클래스 생성 로직을 담당하는 factory를 나타내는 어노테이션
 *
 * @author kimseongwon
 * @date 2023/10/30
 * @version 1.0.0
 **/
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Factory()
