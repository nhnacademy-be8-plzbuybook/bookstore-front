package com.nhnacademy.bookstorefront.main.controller.admin;


import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.book.BookTagResponseDto;
import com.nhnacademy.bookstorefront.main.dto.book.TagRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.book.TagResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminTagController {

    @Autowired
    private final BookClient bookClient;


    @GetMapping("/admin/tags")
    public String adminTagList(
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page, // 페이지 번호
            @RequestParam(defaultValue = "10") int size, // 페이지 크기,
            @RequestParam(required = false) Long tagId,
            HttpServletRequest request) {

        if(tagId == null) {
            // 페이징된 태그 리스트 가져오기
            Page<TagResponseDto> tagResponseDtoPage = bookClient.getAllTags(keyword, page,size).getBody();

            // 모델에 페이지 데이터 추가
            model.addAttribute("tags", tagResponseDtoPage.getContent()); // 태그 목록
            model.addAttribute("keyword", keyword); // 검색어
            model.addAttribute("currentPage", page); // 현재 페이지
            model.addAttribute("totalPages", tagResponseDtoPage.getTotalPages()); // 전체 페이지 수
            model.addAttribute("totalElements", tagResponseDtoPage.getTotalElements()); // 전체 요소 수
            model.addAttribute("pageSize", size); // 페이지 크기

            return "admin/tag";
        } else {
            //태그를 하나 선택했을때

            List<BookTagResponseDto> bookTagResponseDto = bookClient.getAllBookTags(tagId).getBody();
            String tagName = bookClient.getTagNameByTagId(tagId).getBody();
            model.addAttribute("tagId", tagId);
            model.addAttribute("bookTags", bookTagResponseDto);
            model.addAttribute("currentUri", request.getRequestURI());
            model.addAttribute("tagName", tagName);

            return "admin/bookTagList";
        }

    }

    @PostMapping("/admin/tags")
    public String saveTag(@RequestParam String tagName){
        TagRegisterDto tagRegisterDto = new TagRegisterDto();
        tagRegisterDto.setTagName(tagName);
        bookClient.saveTag(tagRegisterDto);
        return "redirect:/admin/tags";
    }

    @RequestMapping(value = "/admin/tags/{tag-id}", method = RequestMethod.POST)
    public String deleteTag(@PathVariable("tag-id") Long tagId,
                                 @RequestParam("_method") String method) {
        if ("delete".equals(method)) {
            bookClient.deleteTag(tagId);
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/admin/books/{bookId}/tags/{tagId}")
    public String bookTagSave(@PathVariable Long bookId,@PathVariable Long tagId, Model model) {

        bookClient.saveBookTag(bookId, tagId);

        return "redirect:/admin/tags?tagId="   + tagId;
    }

    @RequestMapping(value = "/admin/book/{book-id}/tags/{tag-id}" , method = RequestMethod.POST)
    public String deleteBookTag(@PathVariable(name="tag-id") Long tagId, @PathVariable(name="book-id") Long bookId, @RequestParam("_method") String method, Model model) {
        if ("delete".equals(method)) {
            bookClient.deleteBookTag(bookId, tagId);
        }
        model.addAttribute("bookId", bookId);
        model.addAttribute("tagId", tagId);

        return "redirect:/admin/tags?tagId=" + tagId;
    }



}
