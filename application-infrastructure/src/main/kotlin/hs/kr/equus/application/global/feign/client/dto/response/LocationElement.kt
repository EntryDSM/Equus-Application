package hs.kr.equus.application.global.feign.client.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class LocationElement(
    val documents: List<Document>,
    val meta: Meta,
)

data class Document(
    val address: Address,
    @JsonProperty("address_name")
    val addressName: String,
    @JsonProperty("address_type")
    val addressType: String,
    @JsonProperty("road_address")
    val roadAddress: RoadAddress,
    val x: String,
    val y: String,
)

data class Address(
    @JsonProperty("address_name")
    val addressName: String,
    @JsonProperty("b_code")
    val bCode: String,
    @JsonProperty("h_code")
    val hCode: String,
    @JsonProperty("main_address_no")
    val mainAddressNo: String,
    @JsonProperty("mountain_yn")
    val mountainYn: String,
    @JsonProperty("region_1depth_name")
    val region1depthName: String,
    @JsonProperty("region_2depth_name")
    val region2depthName: String,
    @JsonProperty("region_3depth_h_name")
    val region3depthHName: String,
    @JsonProperty("region_3depth_name")
    val region3depthName: String,
    @JsonProperty("sub_address_no")
    val subAddressNo: String,
    val x: String,
    val y: String,
)

data class RoadAddress(
    @JsonProperty("address_name")
    val addressName: String,
    @JsonProperty("building_name")
    val buildingName: String,
    @JsonProperty("main_building_no")
    val mainBuildingNo: String,
    @JsonProperty("region_1depth_name")
    val region1depthName: String,
    @JsonProperty("region_2depth_name")
    val region2depthName: String,
    @JsonProperty("region_3depth_name")
    val region3depthName: String,
    @JsonProperty("road_name")
    val roadName: String,
    @JsonProperty("sub_building_no")
    val subBuildingNo: String,
    @JsonProperty("underground_yn")
    val undergroundYn: String,
    val x: String,
    val y: String,
    @JsonProperty("zone_no")
    val zoneNo: String,
)

data class Meta(
    @JsonProperty("is_end")
    val isEnd: Boolean,
    @JsonProperty("pageable_count")
    val pageableCount: Long,
    @JsonProperty("total_count")
    val totalCount: Long,
)
