package hs.kr.equus.application.domain.score.domain.repository

import hs.kr.equus.application.domain.score.domain.entity.ScoreJpaEntity
import org.springframework.data.repository.CrudRepository

interface ScoreJpaRepository : CrudRepository<ScoreJpaEntity, Long> {

    fun findByReceiptCode(receiptCode: Long): ScoreJpaEntity?

    fun findAllByReceiptCodeIn(receiptCode: List<Long>): List<ScoreJpaEntity?>
}
