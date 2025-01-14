package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.main.client.WrappingPaperClient;
import com.nhnacademy.bookstorefront.main.dto.WrappingPaperCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.WrappingPaperDto;
import com.nhnacademy.bookstorefront.main.dto.WrappingPaperModifyRequestDto;
import com.nhnacademy.bookstorefront.main.service.WrappingPaperService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WrappingPaperServiceImpl implements WrappingPaperService {
    private final WrappingPaperClient wrappingPaperClient;

    @Override
    public List<WrappingPaperDto> getWrappingPapers() {
        try {
            ResponseEntity<List<WrappingPaperDto>> response = wrappingPaperClient.getWrappingPapers();
            return response.getBody();
        } catch (FeignException e) {
            throw new RuntimeException("포장지를 가져오는 중 오류가 발생했습니다.");
        }
    }

    @Override
    public void createWrappingPaper(WrappingPaperCreateRequestDto createRequest) {
        try {
            ResponseEntity<Long> response = wrappingPaperClient.createWrappingPaper(createRequest);
        } catch (FeignException e) {
            throw new RuntimeException("포장지를 저장하는 중 오류가 발생했습니다.");
        }

    }

    @Override
    public void modifyWrappingPaper(Long wrappingPaperId, WrappingPaperModifyRequestDto modifyRequest) {
        try {
            ResponseEntity<Long> response = wrappingPaperClient.modifyWrappingPaper(wrappingPaperId, modifyRequest);
        } catch (FeignException e) {
            throw new RuntimeException("포장지를 수정하는 중 오류가 발생했습니다.");
        }

    }

    @Override
    public void removeWrappingPaper(Long wrappingPaperId) {
        try {
            ResponseEntity<Void> response = wrappingPaperClient.removeWrappingPaper(wrappingPaperId);
        } catch (FeignException e) {
            throw new RuntimeException("포장지를 삭제하는 중 오류가 발생했습니다.");
        }

    }

}
