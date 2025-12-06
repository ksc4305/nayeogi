package com.ssafy.nayeogi.attraction.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.nayeogi.attraction.model.dao.AttractionDao;
import com.ssafy.nayeogi.attraction.model.dto.AttractionResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

	private final AttractionDao attractionDao;

	@Override
	@Transactional(readOnly = true)
	public List<AttractionResponse> findByTitle(String title) {
		return attractionDao.findByTitle(title);
	}
}
