package com.ssafy.nayeogi.attraction.model.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "관광지 검색 응답")
public class AttractionResponse {
    @Schema(description = "관광지 ID", example = "12540")
    private Integer id;
    @Schema(description = "콘텐츠 ID", example = "12540")
    private Integer content_id;
    @Schema(description = "관광지 제목", example = "해운대 해수욕장")
    private String title;
    @Schema(description = "콘텐츠 타입 ID", example = "12")
    private Integer content_type_id;
    @Schema(description = "지역 코드", example = "6")
    private Integer area_code;
    @Schema(description = "시군구 코드", example = "23")
    private Integer si_gun_gu_code;
    @Schema(description = "대표 이미지 URL 1", example = "https://image-server.com/first.jpg")
    private String first_image1;
    @Schema(description = "대표 이미지 URL 2", example = "https://image-server.com/second.jpg")
    private String first_image2;
    @Schema(description = "지도 줌 레벨", example = "12")
    private Integer map_level;
    @Schema(description = "위도", example = "35.1587")
    private BigDecimal latitude;
    @Schema(description = "경도", example = "129.1604")
    private BigDecimal longitude;
    @Schema(description = "연락처", example = "031-000-0000")
    private String tel;
    @Schema(description = "주소", example = "부산광역시 해운대구 우동")
    private String addr1;
    @Schema(description = "상세 주소", example = "동백섬로 52")
    private String addr2;
    @Schema(description = "홈페이지 URL", example = "https://www.haeundae.go.kr")
    private String homepage;
    @Schema(description = "관광지 소개", example = "국내 최대 규모의 해수욕장으로 여름철 인기가 높습니다.")
    private String overview;
}
