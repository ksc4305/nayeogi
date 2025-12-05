package com.ssafy.nayeogi.attraction.service;

import java.util.List;

import com.ssafy.nayeogi.attraction.model.dto.AttractionResponse;

public interface AttractionService {

	List<AttractionResponse> findByTitle(String title);
}
