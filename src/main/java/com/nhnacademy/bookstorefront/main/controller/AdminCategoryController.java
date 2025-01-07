package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.book.CategorySimpleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminCategoryController {

    @Autowired
    private BookClient bookClient;


    @GetMapping("/admin/category")
    public String searchCategory(Model model,  @RequestParam(required = false) String keyword) {
        List<CategorySimpleResponseDto> categoryList = bookClient.searchCategories(keyword).getBody();
        model.addAttribute("categories", categoryList);
        model.addAttribute("keyword", keyword);
        return "admin/category";

    }

}
