package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.status.model.Status

interface ApplicationCommandStatusPort {
    fun save(status: Status) //todo message send라서 반환 없음
}
