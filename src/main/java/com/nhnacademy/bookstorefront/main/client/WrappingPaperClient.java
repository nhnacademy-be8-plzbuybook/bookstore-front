package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.WrappingPaperCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.WrappingPaperDto;
import com.nhnacademy.bookstorefront.main.dto.WrappingPaperModifyRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "GATEWAY", contextId = "wrappingPaperClient")
public interface WrappingPaperClient {
    @GetMapping("/api/wrapping-papers")
    ResponseEntity<List<WrappingPaperDto>> getWrappingPapers();

    @PostMapping(value = "/api/wrapping-papers", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Long> createWrappingPaper(@ModelAttribute WrappingPaperCreateRequestDto createRequest);

    @PutMapping(value = "/api/wrapping-papers/{wrapping-paper-id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Long> modifyWrappingPaper(@PathVariable("wrapping-paper-id") Long wrappingPaperId,
                                             @ModelAttribute WrappingPaperModifyRequestDto modifyRequest);

    @DeleteMapping("/api/wrapping-papers/{wrapping-paper-id}")
    ResponseEntity<Void> removeWrappingPaper(@PathVariable("wrapping-paper-id") Long wrappingPaperId);
}
