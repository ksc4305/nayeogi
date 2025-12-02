package com.ssafy.nayeogi.recommendation.mapper;

import com.ssafy.nayeogi.recommendation.dao.Recommendation;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecommendationMapper {

    List<Recommendation> selectRecommendations(@Param("svdIds") List<Integer> svdIds);
}
