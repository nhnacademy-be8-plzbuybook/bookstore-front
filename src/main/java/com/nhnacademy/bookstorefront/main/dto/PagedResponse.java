package com.nhnacademy.bookstorefront.main.dto;

import lombok.Data;
import java.util.List;

@Data
public class PagedResponse<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int number;
    private int size;
}
