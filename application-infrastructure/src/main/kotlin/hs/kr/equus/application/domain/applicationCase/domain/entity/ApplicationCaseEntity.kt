package hs.kr.equus.application.domain.applicationCase.domain.entity

import hs.kr.equus.application.domain.applicationCase.domain.entity.vo.ExtraScoreItem
import javax.persistence.*

@MappedSuperclass
abstract class ApplicationCaseEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val receiptCode: Long,
    @Embedded
    val extraScoreItem: ExtraScoreItem
)