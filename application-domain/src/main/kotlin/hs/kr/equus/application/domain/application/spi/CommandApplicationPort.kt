package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.model.Application

interface CommandApplicationPort {
    fun save(application: Application): Application
}
