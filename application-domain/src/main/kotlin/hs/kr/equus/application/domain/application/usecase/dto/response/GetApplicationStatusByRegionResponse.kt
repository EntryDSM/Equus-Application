package hs.kr.equus.application.domain.application.usecase.dto.response

data class GetApplicationStatusByRegionResponse (
    val seoul: Int = 0,
    val gwangju: Int = 0,
    val daegu: Int = 0,
    val daejeon: Int = 0,
    val busan: Int = 0,
    val sejong: Int = 0,
    val ulsan: Int = 0,
    val incheon: Int = 0,
    val jeju: Int = 0,
    val gangwonDo: Int = 0,
    val gyeonggiDo: Int = 0,
    val gyeongsangnamDo: Int = 0,
    val gyeongsangbukDo: Int = 0,
    val jeollanamDo: Int = 0,
    val jeollabukDo: Int = 0,
    val chungcheongnamDo: Int = 0,
    val chungcheongbukDo: Int = 0,
)
