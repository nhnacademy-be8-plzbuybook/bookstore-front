package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.WrappingPaperDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "GATEWAY", contextId = "wrappingPaperClient")
public interface WrappingPaperClient {
    @GetMapping("/api/wrapping-papers")
    ResponseEntity<List<WrappingPaperDto>> getWrappingPapers();
}
