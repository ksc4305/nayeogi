package com.ssafy.nayeogi.attraction.model.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AttractionResponse {
    private Integer id;
    private Integer content_id;
    private String title;
    private Integer content_type_id;
    private Integer area_code;
    private Integer si_gun_gu_code;
    private String first_image1;
    private String first_image2;
    private Integer map_level;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String tel;
    private String addr1;
    private String addr2;
    private String homepage;
    private String overview;
}
