package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.client.MemberClient;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import com.nhnacademy.bookstorefront.main.dto.Member.*;
import com.nhnacademy.bookstorefront.main.dto.mypage.MyPageDto;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final AuthenticationService authenticationService;
    private final AuthenticationClient authenticationClient;
    private final MemberClient memberClient;

    @GetMapping("/mypage")
    public String mypage( @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "16") int size,
                          Model model) {
        MyPageDto myPageDto = authenticationService.getMyPage();

        Page<BookDetailResponseDto> likedBooks = memberClient.getLikedBooks(page, size, myPageDto.getMemberId());

        model.addAttribute("books", likedBooks.getContent());
        model.addAttribute("currentPage", likedBooks.getNumber());
        model.addAttribute("totalPages", likedBooks.getTotalPages());

        //회원 수정전 정보 표시
        model.addAttribute("member", myPageDto);

        //주소 목록 추가
        List<MemberAddressResponseDto> addresses = memberClient.getAddressListByMemberEmail();
        model.addAttribute("addresses", addresses);

        Long memberId = myPageDto.getMemberId();

        Pageable pageable = PageRequest.of(0, 10);
        Page<MemberCouponGetResponseDto> response = memberClient.getMemberCouponsByMemberId(memberId, pageable);


        model.addAttribute("coupons", response.getContent());
        List<MemberPointResponseDto> points = memberClient.getMemberPoints(myPageDto.getMemberId());
        model.addAttribute("points", points);

        BigDecimal totalPoints = points.stream()
                .map(MemberPointResponseDto::getPoint)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("totalPoints", totalPoints);


        return "member/mypage";
    }

    @PostMapping("/mypage/update")
    public String updateMember(MemberModifyRequestDto modifyRequestDto, Model model) {
        ResponseEntity<String> response = authenticationClient.updateMember(modifyRequestDto);

        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("successMessage", "회원 정보가 성공적으로 수정되었습니다.");
        } else {
            model.addAttribute("errorMessage", "회원 정보 수정에 실패했습니다.");
        }

        return "redirect:/mypage";
    }

    @PostMapping("/mypage/address")
    public String handleAddressSubmission(@Valid MemberAddressRequestDto addressRequestDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "모든 필드를 올바르게 입력해주세요.");
            return "mypage";
        }

        try {
            memberClient.createAddress(addressRequestDto);
        } catch (Exception e) {
            model.addAttribute("error", "주소 등록에 실패했습니다.");
            return "mypage";
        }

        return "redirect:/mypage";
    }

    // 주소 삭제 메서드
    @PostMapping("/mypage/address/delete/{addressId}")
    public String deleteAddress(@PathVariable Long addressId, Model model) {
        try {
            ResponseEntity<Void> response = memberClient.deleteAddress(addressId);

            if (response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute("successMessage", "주소가 성공적으로 삭제되었습니다.");
            } else {
                model.addAttribute("errorMessage", "주소 삭제에 실패했습니다.");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "주소 삭제에 실패했습니다.");
        }
        return "redirect:/mypage";  // 삭제 후 마이페이지로 리디렉션
    }

    // 주소 수정 메서드
    @PostMapping("/mypage/address/update/{addressId}")
    public String updateAddress(@PathVariable("addressId") Long addressId, @Valid MemberAddressRequestDto memberAddressRequestDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("error", "모든 필드를 바르게 입력해주세요");
            return "mypage";
        }

        try{
            memberClient.updateAddress(addressId, memberAddressRequestDto);
            model.addAttribute("successMessage", "주소가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "주소 수정에 실패했습니다.");
        }

        return "redirect:/mypage";
    }
}