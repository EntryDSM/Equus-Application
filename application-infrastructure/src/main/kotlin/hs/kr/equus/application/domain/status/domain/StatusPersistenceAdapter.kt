package hs.kr.equus.application.domain.status.domain

import hs.kr.equus.application.domain.status.domain.entity.StatusCacheRedisEntity
import hs.kr.equus.application.domain.status.domain.repository.StatusCacheRepository
import hs.kr.equus.application.domain.status.model.Status
import hs.kr.equus.application.domain.status.model.StatusCache
import hs.kr.equus.application.domain.status.spi.StatusPort
import hs.kr.equus.application.global.feign.client.StatusClient
import org.springframework.stereotype.Component

@Component
class StatusPersistenceAdapter(
    private val statusClient: StatusClient,
    private val statusCacheRepository: StatusCacheRepository
): StatusPort {
    override fun queryStatusByReceiptCode(receiptCode: Long): Status? {
        return statusClient.getStatusByReceiptCode(receiptCode)?.let {
            Status(
                id = it.id,
                isSubmitted = it.isSubmitted,
                isPrintsArrived = it.isPrintsArrived,
                examCode = it.examCode,
                isFirstRoundPass = it.isFirstRoundPass,
                isSecondRoundPass = it.isSecondRoundPass,
                receiptCode = it.receiptCode
            )
        }
    }

    override fun queryStatusByReceiptCodeInCache(receiptCode: Long): StatusCache? {
        return statusCacheRepository.findById(receiptCode)
            .map {
                StatusCache(
                    receiptCode = it.receiptCode,
                    isPrintsArrived = it.isPrintsArrived,
                    isSubmitted = it.isSubmitted,
                    examCode = it.examCode,
                    isFirstRoundPass = it.isFirstRoundPass,
                    isSecondRoundPass = it.isSecondRoundPass,
                    ttl = it.ttl
                )
            }.orElse(null)
    }
}