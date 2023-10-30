package hs.kr.equus.application.global

import hs.kr.equus.application.global.exception.DomainExceptions
import java.util.*

object DomainProperties : Properties() {
    override fun getProperty(key: String): String {
        return super.getProperty(key) ?: throw DomainExceptions.NotInitializationProperties()
    }
}
