package hs.kr.equus.application.domain.application.presentation.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class GetApplicationStatusByRegionWebResponse(
    val regionList: List<RegionWebList>
)
data class RegionWebList(
    @JsonProperty("서울")
    val seoul: Int = 0,

    @JsonProperty("광주")
    val gwangju: Int = 0,

    @JsonProperty("대구")
    val daegu: Int = 0,

    @JsonProperty("대전")
    val daejeon: Int = 0,

    @JsonProperty("부산")
    val busan: Int = 0,

    @JsonProperty("세종")
    val sejong: Int = 0,

    @JsonProperty("울산")
    val ulsan: Int = 0,

    @JsonProperty("인천")
    val incheon: Int = 0,

    @JsonProperty("제주")
    val jeju: Int = 0,

    @JsonProperty("강원도")
    val gangwonDo: Int = 0,

    @JsonProperty("경기도")
    val gyeonggiDo: Int = 0,

    @JsonProperty("경상남도")
    val gyeongsangnamDo: Int = 0,

    @JsonProperty("경상북도")
    val gyeongsangbukDo: Int = 0,

    @JsonProperty("전라남도")
    val jeollanamDo: Int = 0,

    @JsonProperty("전라북도")
    val jeollabukDo: Int = 0,

    @JsonProperty("충청남도")
    val chungcheongnamDo: Int = 0,

    @JsonProperty("충청북도")
    val chungcheongbukDo: Int = 0
)
