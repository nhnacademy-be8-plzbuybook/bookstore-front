package com.nhnacademy.bookstorefront.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {
    //    private Long bookId;
    private String url; // 업로드된 파일의 URL
}

