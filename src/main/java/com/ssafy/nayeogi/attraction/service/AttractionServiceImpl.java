package com.ssafy.nayeogi.attraction.service;

import com.ssafy.nayeogi.attraction.model.dao.AttractionDao;
import com.ssafy.nayeogi.attraction.model.dto.AttractionSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 관광지 관련 비즈니스 로직 처리를 위한 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionDao attractionDao;

    // @Override
    // public List<AttractionInfo> search(AttractionSearchCondition searchCondition) {
    //     return attractionDao.searchByCondition(searchCondition);
    // }

    // @Override
    // public AttractionInfo findById(int contentId) {
    //     return attractionDao.findById(contentId);
    // }
}
