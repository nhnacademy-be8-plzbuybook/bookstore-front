package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.main.client.WrappingPaperClient;
import com.nhnacademy.bookstorefront.main.dto.WrappingPaperDto;
import com.nhnacademy.bookstorefront.main.service.WrappingPaperService;
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
        ResponseEntity<List<WrappingPaperDto>> response = wrappingPaperClient.getWrappingPapers();
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("포장지 정보를 가져오는데 실패했습니다.");
        }
        return response.getBody();
    }

}
