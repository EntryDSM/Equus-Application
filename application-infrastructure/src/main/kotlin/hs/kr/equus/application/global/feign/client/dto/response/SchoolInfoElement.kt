package hs.kr.equus.application.global.feign.client.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class SchoolInfoElement(
    val schoolInfo: List<SchoolInfo>,
)

data class SchoolInfo(
    val head: List<Head>?,
    val row: List<Row>?,
)

data class Head(
    @JsonProperty("list_total_count")
    val listTotalCount: Long?,
    @JsonProperty("RESULT")
    val result: Result?,
)

data class Result(
    @JsonProperty("CODE")
    val code: String,
    @JsonProperty("MESSAGE")
    val message: String,
)

data class Row(
    @JsonProperty("ATPT_OFCDC_SC_CODE")
    val atptOfcdcScCode: String,
    @JsonProperty("ATPT_OFCDC_SC_NM")
    val atptOfcdcScNm: String,
    @JsonProperty("SD_SCHUL_CODE")
    val sdSchulCode: String,
    @JsonProperty("SCHUL_NM")
    val schulNm: String,
    @JsonProperty("ENG_SCHUL_NM")
    val engSchulNm: String ? = null,
    @JsonProperty("SCHUL_KND_SC_NM")
    val schulKndScNm: String,
    @JsonProperty("LCTN_SC_NM")
    val lctnScNm: String,
    @JsonProperty("JU_ORG_NM")
    val juOrgNm: String,
    @JsonProperty("FOND_SC_NM")
    val fondScNm: String,
    @JsonProperty("ORG_RDNZC")
    val orgRdnzc: String,
    @JsonProperty("ORG_RDNMA")
    val orgRdnma: String,
    @JsonProperty("ORG_RDNDA")
    val orgRdnda: String,
    @JsonProperty("ORG_TELNO")
    val orgTelno: String,
    @JsonProperty("HMPG_ADRES")
    val hmpgAdres: String,
    @JsonProperty("COEDU_SC_NM")
    val coeduScNm: String,
    @JsonProperty("ORG_FAXNO")
    val orgFaxno: String? = null,
    @JsonProperty("HS_SC_NM")
    val hsScNm: Any?,
    @JsonProperty("INDST_SPECL_CCCCL_EXST_YN")
    val indstSpeclCccclExstYn: String,
    @JsonProperty("HS_GNRL_BUSNS_SC_NM")
    val hsGnrlBusnsScNm: Any?,
    @JsonProperty("SPCLY_PURPS_HS_ORD_NM")
    val spclyPurpsHsOrdNm: Any?,
    @JsonProperty("ENE_BFE_SEHF_SC_NM")
    val eneBfeSehfScNm: String,
    @JsonProperty("DGHT_SC_NM")
    val dghtScNm: String,
    @JsonProperty("FOND_YMD")
    val fondYmd: String,
    @JsonProperty("FOAS_MEMRD")
    val foasMemrd: String,
    @JsonProperty("LOAD_DTM")
    val loadDtm: String,
)
