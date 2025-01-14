package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.WrappingPaperCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.WrappingPaperDto;
import com.nhnacademy.bookstorefront.main.dto.WrappingPaperModifyRequestDto;

import java.util.List;

public interface WrappingPaperService {
    List<WrappingPaperDto> getWrappingPapers();

    void createWrappingPaper(WrappingPaperCreateRequestDto createRequest);

    void modifyWrappingPaper(Long wrappingPaperId, WrappingPaperModifyRequestDto modifyRequest);

    void removeWrappingPaper(Long wrappingPaperId);

}
