package com.nhnacademy.bookstorefront.main.controller.admin;

import com.nhnacademy.bookstorefront.common.handler.GlobalControllerExceptionHandler;
import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.AdminBookRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.BookRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.book.AuthorResponseDto;
import com.nhnacademy.bookstorefront.main.dto.book.BookResponseDto;
import com.nhnacademy.bookstorefront.main.dto.book.CategorySimpleResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminBookController.class)
class AdminBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookClient bookClient;

    @MockBean
    AuthenticationClient authenticationClient;

    @MockBean
    GlobalControllerExceptionHandler globalControllerExceptionHandler;

    @Test
    @DisplayName("도서 리스트 조회 (판매되지 않는 도서만)")
    void testAdminGetBooks_nonSelling() throws Exception {
        // Given
        Page<BookResponseDto> bookPage = new PageImpl<>(Collections.emptyList());
        when(bookClient.getBooksNotInSellingBooks(0, 10)).thenReturn(ResponseEntity.ok(bookPage));

        // When & Then
        mockMvc.perform(get("/admin/books")
                        .param("page", "0")
                        .param("size", "10")
                        .param("bookType", "non-selling"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/bookList"))
                .andExpect(model().attributeExists("books", "totalPages", "currentPage", "bookType"));

        verify(bookClient, times(1)).getBooksNotInSellingBooks(0, 10);
    }

    @Test
    @DisplayName("기본 도서 리스트 가져오기 - AdminBookRegisterDto")
    void testAdminGetBooks_DefaultBooks() throws Exception {
        // Given
        int page = 0;
        int size = 10;
        String bookType = null; // bookType이 null인 경우 기본 도서 API 호출

        // Mock 데이터 생성
        List<AdminBookRegisterDto> bookList = List.of(
                new AdminBookRegisterDto(
                        1L,
                        "Book1",
                        LocalDate.of(2022, 1, 1),
                        "Publisher1",
                        "1234567890123",
                        new BigDecimal("20000"),
                        List.of("http://image.url/book1.jpg"),
                        List.of(new AuthorResponseDto("Author1")),
                        List.of(new CategorySimpleResponseDto("Fiction"))
                ),
                new AdminBookRegisterDto(
                        2L,
                        "Book2",
                        LocalDate.of(2023, 5, 15),
                        "Publisher2",
                        "9876543210987",
                        new BigDecimal("30000"),
                        List.of("http://image.url/book2.jpg"),
                        List.of(new AuthorResponseDto("Author2")),
                        List.of(new CategorySimpleResponseDto("Non-Fiction"))
                )
        );
        Page<AdminBookRegisterDto> bookPage = new PageImpl<>(bookList, PageRequest.of(page, size), bookList.size());

        // bookClient.adminGetBooks 호출 시 Mock 데이터 반환 설정
        when(bookClient.adminGetBooks(page, size)).thenReturn(bookPage);

        // When & Then
        mockMvc.perform(get("/admin/books")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk()) // HTTP 상태 코드 200 확인
                .andExpect(view().name("admin/bookList")) // 뷰 이름 확인
                .andExpect(model().attributeExists("books")) // 모델 속성 확인
                .andExpect(model().attribute("books", hasSize(2))) // 도서 리스트 크기 확인
                .andExpect(model().attribute("totalPages", 1)) // 총 페이지 수 확인
                .andExpect(model().attribute("currentPage", page)); // 현재 페이지 확인

        // bookClient.adminGetBooks가 한 번 호출되었는지 검증
        verify(bookClient, times(1)).adminGetBooks(page, size);
    }



    @Test
    @DisplayName("도서 삭제")
    void testDeleteBook() throws Exception {
        // Given
        Long bookId = 1L;
        String bookType = "non-selling";

        // When & Then
        mockMvc.perform(post("/admin/books/{bookId}", bookId)
                        .param("_method", "delete")
                        .param("bookType", bookType))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/books?bookType=" + bookType));

        verify(bookClient, times(1)).deleteBook(bookId);
    }

    @Test
    @DisplayName("도서 등록")
    void testRegisterBook() throws Exception {
        // Given
        BookRegisterDto bookRegisterDto = new BookRegisterDto();
        BookRegisterDto.CategoryDto categoryDto = new BookRegisterDto.CategoryDto();
        BookRegisterDto.CategoryDto categoryDto2 = new BookRegisterDto.CategoryDto();
        categoryDto.setCategoryId(1);
        categoryDto.setCategoryName("Category1");
        categoryDto2.setCategoryId(2);
        categoryDto2.setCategoryName("Category2");


        bookRegisterDto.setBookTitle("Test Book");
        bookRegisterDto.setAuthors(List.of("Author1", "Author2"));
        bookRegisterDto.setCategories(List.of(categoryDto, categoryDto2));

        // When & Then
        mockMvc.perform(post("/admin/books/register")
                        .contentType("application/json")
                        .content("""
                        {
                          "bookTitle": "Test Book",
                          "bookIndex": "Test Index",
                          "bookDescription": "A description for testing",
                          "bookPubDate": "2025-01-01",
                          "bookPriceStandard": "10000",
                          "bookIsbn13": "1234567890123",
                          "publisher": "Test Publisher",
                          "imageUrl": "http://example.com/image.jpg",
                          "authors": ["Author1", "Author2"],
                          "categories": [
                            { "categoryId": 1, "categoryName": "Category1" },
                            { "categoryId": 2, "categoryName": "Category2" }
                          ]
                        }
                        """))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/books"));

        ArgumentCaptor<BookRegisterDto> captor = ArgumentCaptor.forClass(BookRegisterDto.class);
        verify(bookClient, times(1)).registerBook(captor.capture());
        BookRegisterDto capturedDto = captor.getValue();
        assert capturedDto.getBookTitle().equals("Test Book");
    }
//
//    @Test
//    @DisplayName("도서 수정")
//    void testUpdateBook() throws Exception {
//        // Given
//        Long bookId = 1L;
//        AdminBookRegisterDto updateDto = new AdminBookRegisterDto();
//        updateDto.setBookTitle("Updated Book");
//
//        // When & Then
//        mockMvc.perform(post("/admin/books/update/{bookId}", bookId)
//                        .contentType("application/json")
//                        .content("""
//                                {
//                                  "bookTitle": "Updated Book"
//                                }
//                                """))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/admin/books"));
//
//        ArgumentCaptor<AdminBookRegisterDto> captor = ArgumentCaptor.forClass(AdminBookRegisterDto.class);
//        verify(bookClient, times(1)).updateSellingBook(eq(bookId), captor.capture());
//        AdminBookRegisterDto capturedDto = captor.getValue();
//        assert capturedDto.getBookTitle().equals("Updated Book");
//    }

    @Test
    @DisplayName("도서 수정 페이지 로드")
    void testShowUpdatePage() throws Exception {
        // Given
        Long bookId = 1L;
        BookRegisterDto bookData = new BookRegisterDto();
        bookData.setBookTitle("Test Book");
        bookData.setAuthors(List.of("Author1", "Author2"));
        BookRegisterDto.CategoryDto categoryDto = new BookRegisterDto.CategoryDto();
        BookRegisterDto.CategoryDto categoryDto2 = new BookRegisterDto.CategoryDto();
        categoryDto.setCategoryId(1);
        categoryDto.setCategoryName("Category1");
        categoryDto2.setCategoryId(2);
        categoryDto2.setCategoryName("Category2");
        bookData.setCategories(List.of(
                categoryDto, categoryDto2
        ));

        // bookClient의 동작을 Mocking
        when(bookClient.showUpdatePage(bookId)).thenReturn(bookData);

        // When & Then
        mockMvc.perform(get("/admin/books/update/{bookId}", bookId))
                .andExpect(status().isOk()) // 상태 코드 확인
                .andExpect(view().name("admin/bookUpdate")) // 반환되는 뷰 이름 확인
                .andExpect(model().attributeExists("bookId")) // 모델에 bookId 존재 여부 확인
                .andExpect(model().attributeExists("bookData")) // 모델에 bookData 존재 여부 확인
                .andExpect(model().attribute("bookId", bookId)) // 모델의 bookId 값 확인
                .andExpect(model().attribute("bookData", bookData)); // 모델의 bookData 값 확인

        // bookClient가 올바른 bookId로 호출되었는지 검증
        verify(bookClient, times(1)).showUpdatePage(bookId);
    }


    @Test
    @DisplayName("도서 동기화 (ISBN) 테스트")
    void testSyncBooksByIsbns() throws Exception {
        // Given
        String isbnList = "1234567890,0987654321";

        // When & Then
        mockMvc.perform(post("/admin/books/sync/isbn")
                        .param("isbn", isbnList))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/books"));

        verify(bookClient, times(1)).syncBooksByIsbns(List.of("1234567890", "0987654321"));
    }

    @Test
    @DisplayName("도서 동기화 요청 성공")
    void testSyncBooksSuccess() throws Exception {
        // Given
        String queryType = "type";
        String searchTarget = "target";
        int start = 0;
        int maxResults = 10;

        // When & Then
        mockMvc.perform(post("/admin/books/sync")
                        .param("queryType", queryType)
                        .param("searchTarget", searchTarget)
                        .param("start", String.valueOf(start))
                        .param("maxResults", String.valueOf(maxResults)))
                .andExpect(status().is3xxRedirection()) // 성공 시 리다이렉트 확인
                .andExpect(redirectedUrl("/admin/books")); // 리다이렉트 URL 확인

        // bookClient가 올바른 매개변수로 호출되었는지 검증
        verify(bookClient, times(1)).syncBooksFromListApi(queryType, searchTarget, start, maxResults);
    }

    @Test
    @DisplayName("도서 동기화 요청 실패")
    void testSyncBooksFailure() throws Exception {
        // Given
        String queryType = "type";
        String searchTarget = "target";
        int start = 0;
        int maxResults = 10;

        // bookClient의 동작을 Mocking하여 예외 발생 시킴
        doThrow(new RuntimeException("API Error"))
                .when(bookClient).syncBooksFromListApi(queryType, searchTarget, start, maxResults);

        // When & Then
        mockMvc.perform(post("/admin/books/sync")
                        .param("queryType", queryType)
                        .param("searchTarget", searchTarget)
                        .param("start", String.valueOf(start))
                        .param("maxResults", String.valueOf(maxResults)))
                .andExpect(status().is3xxRedirection()) // 실패 시에도 리다이렉트 확인
                .andExpect(redirectedUrl("/admin/books?error=true")); // 에러 리다이렉트 URL 확인

        // bookClient가 호출되었는지 검증
        verify(bookClient, times(1)).syncBooksFromListApi(queryType, searchTarget, start, maxResults);
    }
    @Test
    @DisplayName("ISBN 동기화 - 실패")
    void testSyncBooksByIsbns_Failure() throws Exception {
        // Given
        String isbn = "9781234567890,9789876543210";
        List<String> isbnList = List.of("9781234567890", "9789876543210");

        // bookClient.syncBooksByIsbns 호출 시 예외 발생
        doThrow(new RuntimeException("동기화 실패")).when(bookClient).syncBooksByIsbns(isbnList);

        // When & Then
        mockMvc.perform(post("/admin/books/sync/isbn")
                        .param("isbn", isbn))
                .andExpect(status().is3xxRedirection()) // 리다이렉트 상태 확인
                .andExpect(redirectedUrl("/admin/books?error=true")); // 실패 시 리다이렉트 URL 확인

        // bookClient.syncBooksByIsbns가 올바르게 호출되었는지 검증
        verify(bookClient, times(1)).syncBooksByIsbns(isbnList);
    }

}
