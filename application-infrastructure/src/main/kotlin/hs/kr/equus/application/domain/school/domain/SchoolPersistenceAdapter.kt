package hs.kr.equus.application.domain.school.domain

import hs.kr.equus.application.domain.school.model.School
import hs.kr.equus.application.domain.school.spi.SchoolPort
import hs.kr.equus.application.global.feign.client.SchoolClient
import org.springframework.stereotype.Component

@Component
class SchoolPersistenceAdapter(
    private val schoolClient: SchoolClient,
) : SchoolPort {
    override fun isExistsSchoolBySchoolCode(schoolCode: String): Boolean {
        return querySchoolBySchoolCode(schoolCode) != null
    }

    override fun querySchoolBySchoolCode(school: String): School? {
        return schoolClient.getSchoolBySchoolCode(
            school = school,
        )?.let {
            School(
                code = it.SD_SCHUL_CODE,
                name = it.SCHUL_NM,
                tel = it.ORG_TELNO,
                type = it.SCHUL_KND_SC_NM,
                address = it.ORG_RDNMA
            )
        }
    }
}
