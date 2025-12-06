package com.ssafy.nayeogi.attraction.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.nayeogi.attraction.model.dto.AttractionResponse;

@Mapper
public interface AttractionDao {

	List<AttractionResponse> findByTitle(@Param("title") String title);
}
