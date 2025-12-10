// src/main/java/com/ssafy/nayeogi/recommendation/model/dao/RecommendationDao.java
package com.ssafy.nayeogi.recommendation.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Param;

import com.ssafy.nayeogi.recommendation.model.dto.Recommendation;

@Mapper
public interface RecommendationDao {

    List<Recommendation> selectRecommendations(@Param("svdIds") List<Integer> svdIds);
}
