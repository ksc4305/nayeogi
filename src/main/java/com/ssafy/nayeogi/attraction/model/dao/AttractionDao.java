package com.ssafy.nayeogi.attraction.model.dao;

import com.ssafy.nayeogi.attraction.model.dto.AttractionSearchCondition;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 관광지 DB 접근을 위한 매퍼 인터페이스
 */
@Mapper
public interface AttractionDao {
    // Attraction DTO가 없으므로, 반환 타입을 주석 처리합니다.
    // List<AttractionInfo> searchByCondition(AttractionSearchCondition searchCondition);
    // AttractionInfo findById(int contentId);
}
