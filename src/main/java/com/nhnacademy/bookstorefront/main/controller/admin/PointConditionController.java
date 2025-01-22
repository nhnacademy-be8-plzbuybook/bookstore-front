package com.nhnacademy.bookstorefront.main.controller.admin;

import com.nhnacademy.bookstorefront.main.client.PointClient;
import com.nhnacademy.bookstorefront.main.dto.point.PointConditionRequestDto;
import com.nhnacademy.bookstorefront.main.dto.point.PointConditionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/points-conditions")
public class PointConditionController {

    private final PointClient pointClient;

    // 포인트 조건 목록 조회
    @GetMapping
    public String viewPointConditions(Model model) {
        ResponseEntity<List<PointConditionResponseDto>> pointResponse = pointClient.getAllPointConditions();
        List<PointConditionResponseDto> pointConditions = pointResponse.getBody();
        model.addAttribute("pointConditions", pointConditions);
        return "admin/pointCondition"; // 템플릿 파일 이름 수정
    }

    // 포인트 조건 생성
    @PostMapping("/create")
    public String createPointCondition(
            @ModelAttribute PointConditionRequestDto pointConditionRequestDto,
            RedirectAttributes redirectAttributes) {
        if (pointConditionRequestDto.getConditionPoint() == null && pointConditionRequestDto.getConditionPercentage() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "포인트와 비율 중 하나를 입력해야 합니다.");
            return "redirect:/admin/points-conditions";
        }

        if (pointConditionRequestDto.getConditionPoint() != null && pointConditionRequestDto.getConditionPercentage() != null) {
            redirectAttributes.addFlashAttribute("errorMessage", "포인트와 비율 중 하나만 입력할 수 있습니다.");
            return "redirect:/admin/points-conditions";
        }

        pointClient.createPointCondition(pointConditionRequestDto);
        return "redirect:/admin/points-conditions";
    }

    // 포인트 조건 수정
    @PostMapping("/update/{id}")
    public String updatePointCondition(
            @PathVariable Long id,
            @ModelAttribute PointConditionRequestDto pointConditionRequestDto,
            RedirectAttributes redirectAttributes) {
        if (pointConditionRequestDto.getConditionPoint() == null && pointConditionRequestDto.getConditionPercentage() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "포인트와 비율 중 하나를 입력해야 합니다.");
            return "redirect:/admin/points-conditions";
        }

        if (pointConditionRequestDto.getConditionPoint() != null && pointConditionRequestDto.getConditionPercentage() != null) {
            redirectAttributes.addFlashAttribute("errorMessage", "포인트와 비율 중 하나만 입력할 수 있습니다.");
            return "redirect:/admin/points-conditions";
        }

        pointClient.updatePointCondition(id, pointConditionRequestDto);
        return "redirect:/admin/points-conditions";
    }
}
