package com.nhnacademy.bookstorefront.main.controller;


import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.book.BookTagResponseDto;
import com.nhnacademy.bookstorefront.main.dto.book.TagRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.book.TagResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class AdminTagController {

    @Autowired
    private final BookClient bookClient;

    @GetMapping("/admin/tag")
    public String adminTagList(Model model,@RequestParam(required = false) String keyword) {
        List<TagResponseDto> tagResponseDtoList = bookClient.getAllTags(keyword).getBody();
        model.addAttribute("tags", tagResponseDtoList);
        model.addAttribute("keyword", keyword);

        return "admin/tag";
    }

    @PostMapping("/admin/tag")
    public String saveTag(Model model,@RequestParam String tagName){
        TagRegisterDto tagRegisterDto = new TagRegisterDto();
        tagRegisterDto.setTagName(tagName);
        bookClient.saveTag(tagRegisterDto);
        return "redirect:/admin/tag";
    }

    @RequestMapping(value = "/admin/tag/{id}", method = RequestMethod.POST)
    public String deleteTag(@PathVariable("id") Long tagId,
                                 @RequestParam("_method") String method) {
        if ("delete".equals(method)) {
            bookClient.deleteTag(tagId);
        }
        return "redirect:/admin/tag";
    }

    @GetMapping("/admin/book-tag")
    public String bookTagList(Model model, @RequestParam Long tagId, HttpServletRequest request) {
        List<BookTagResponseDto> bookTagResponseDto = bookClient.getAllBookTags(tagId).getBody();
        String tagName = bookClient.getTagNameByTagId(tagId).getBody();
        model.addAttribute("tagId", tagId);
        model.addAttribute("bookTags", bookTagResponseDto);
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("tagName", tagName);

        return "admin/bookTagList";
    }

    @PostMapping("/admin/book-tag")
    public String bookTagSave(@RequestParam Long bookId,@RequestParam Long tagId, Model model) {
        bookClient.saveBookTag(bookId, tagId);
        return "redirect:/admin/book-tag?tagId=" + tagId;
    }

    @RequestMapping(value = "/admin/book-tag/delete", method = RequestMethod.POST)
    public String deleteBookTag(@RequestParam("tagId") Long tagId, @RequestParam Long bookId, @RequestParam("_method") String method) {
        if ("delete".equals(method)) {
            bookClient.deleteBookTag(bookId,tagId);
        }
        return "redirect:/admin/book-tag?tagId=" + tagId;
    }



}
