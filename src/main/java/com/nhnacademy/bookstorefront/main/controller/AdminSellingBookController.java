package com.nhnacademy.bookstorefront.main.controller;


import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.SellingBookRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.book.SellingBookResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/admin/selling-books")
@RequiredArgsConstructor
public class AdminSellingBookController {


    private final BookClient bookClient;


    //TODO 판매책 수정후 저장하는 PUT
    //판매책
    @RequestMapping(value = "/{selling-book-id}", method = RequestMethod.POST)
    public String updateSellingBook(
            @PathVariable(name="selling-book-id") Long sellingBookId,
            @ModelAttribute @Valid SellingBookRegisterDto updateDto,  @RequestParam("_method") String method) {

        if ("put".equals(method)) {
            bookClient.updateSellingBook(sellingBookId, updateDto);
        }

        // 수정 완료 후 목록 페이지로 리다이렉트
        return "redirect:/admin/selling-books";
    }


    /**
     * //TODO 판매책 불러오는 get
     * 판매 책 목록 페이지 - 관리자 도서불러올때랑똑같은데 return 파일이름이 달라 .. // 뒤진다 진짜
     * @param page
     * @param size
     * @param model
     * @return
     */
    @GetMapping
    public String getSellingBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "sellingBookId") String sortBy, // 정렬 기준
            @RequestParam(defaultValue = "asc") String sortDir, // 정렬 방향

            Model model) {
        Page<SellingBookResponseDto> sellingBooks = bookClient.getBooks(page, size, sortBy, sortDir).getBody();

        sellingBooks.getContent().forEach(book -> log.debug("SellingBookRegisterDto: {}", book));


        model.addAttribute("sellingBooks", sellingBooks.getContent());
        model.addAttribute("currentPage", sellingBooks.getNumber());
        model.addAttribute("totalPages", sellingBooks.getTotalPages());
        return "admin/sellingbook"; // HTML 파일 이름
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteSellingBook(@PathVariable("id") Long tagId,  @RequestParam("_method") String method){
        if("delete".equals(method)){
            bookClient.deleteSellingBook(tagId);
        }

        return "redirect:/admin/selling-books";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        return "admin/sellingBookRegister";
    }
}
