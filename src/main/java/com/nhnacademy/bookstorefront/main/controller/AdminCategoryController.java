package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.book.CategoryRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.book.CategorySimpleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/admin/category")
    public String saveCategory(@RequestParam String categoryName, @RequestParam(required = false) Long parentCategoryId) {
        CategoryRegisterDto categoryRegisterDto = new CategoryRegisterDto();
        categoryRegisterDto.setNewCategoryName(categoryName);
        categoryRegisterDto.setParentCategoryId(parentCategoryId);
        bookClient.saveCategory(categoryRegisterDto);

        return "redirect:/admin/category";
    }

}
