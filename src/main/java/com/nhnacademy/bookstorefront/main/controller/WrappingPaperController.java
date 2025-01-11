package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.dto.WrappingPaperCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.WrappingPaperDto;
import com.nhnacademy.bookstorefront.main.dto.WrappingPaperModifyRequestDto;
import com.nhnacademy.bookstorefront.main.service.WrappingPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class WrappingPaperController {
    private final WrappingPaperService wrappingPaperService;

    @GetMapping("/admin/wrapping-papers")
    public String getWrappingPapers(Model model) {
        List<WrappingPaperDto> wrappingPapers = wrappingPaperService.getWrappingPapers();
        model.addAttribute("wrappingPapers", wrappingPapers);

        return "admin/wrappingPaper/wrapping_paper";
    }

    @PostMapping("/api/wrapping-papers")
    public String createWrappingPaper(@ModelAttribute WrappingPaperCreateRequestDto createRequest) {
        wrappingPaperService.createWrappingPaper(createRequest);
        return "redirect:/admin/wrapping-papers";
    }

    @ResponseBody
    @PutMapping("/api/wrapping-papers/{wrapping-paper-id}")
    public String modifyWrappingPaper(@PathVariable("wrapping-paper-id") Long wrappingPaperId,
                                                    @ModelAttribute WrappingPaperModifyRequestDto modifyRequest) {
        wrappingPaperService.modifyWrappingPaper(wrappingPaperId, modifyRequest);
        return "redirect:/admin/wrappingPaper/wrapping_paper";
    }

    @ResponseBody
    @DeleteMapping("/api/wrapping-papers/{wrapping-paper-id}")
    public ResponseEntity<Void> removeWrappingPaper(@PathVariable("wrapping-paper-id") Long wrappingPaperId) {
        wrappingPaperService.removeWrappingPaper(wrappingPaperId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
