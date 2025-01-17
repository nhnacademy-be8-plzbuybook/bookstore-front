package com.nhnacademy.bookstorefront.main.controller.admin;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.book.CategoryRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.book.CategorySimpleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class AdminCategoryController {

    @Autowired
    private BookClient bookClient;


    @GetMapping("/admin/category")
    public String searchCategory(Model model,
                                 @RequestParam(required = false) String keyword,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {

        // API 호출 시 page와 size를 포함한 페이징된 요청
        Page<CategorySimpleResponseDto> categoryPage = bookClient.searchCategories(keyword, page, size).getBody();

        model.addAttribute("categories", Objects.requireNonNull(categoryPage).getContent());  // 현재 페이지에 해당하는 카테고리 목록
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPages", categoryPage.getTotalPages());  // 전체 페이지 수
        model.addAttribute("totalItems", categoryPage.getTotalElements());  // 전체 카테고리 개수
        model.addAttribute("currentPage", categoryPage.getNumber());  // 현재 페이지
        model.addAttribute("pageSize", size);  // 페이지 크기

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
    @RequestMapping(value = "/admin/category/{id}", method = RequestMethod.POST)
    public String deleteCategory(@PathVariable("id") Long categoryId,
                                 @RequestParam("_method") String method) {
        if ("delete".equals(method)) {
            bookClient.deleteCategory(categoryId);
        }
        return "redirect:/admin/category";
    }

}
