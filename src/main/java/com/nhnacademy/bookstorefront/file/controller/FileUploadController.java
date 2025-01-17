package com.nhnacademy.bookstorefront.file.controller;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;
import java.util.List;

@Controller
@RequestMapping("/upload")
@RequiredArgsConstructor
public class FileUploadController {
    private final BookClient bookClient;

    @GetMapping
    public String uploadForm() {
        return "file/upload"; // upload.html 뷰를 반환
    }

    @PostMapping("/files")
    public String uploadFiles(@RequestParam("files") List<MultipartFile> files,
                              Model model) {
        if (files == null || files.isEmpty()) {
            model.addAttribute("message", "No files provided for upload.");
            return "file/upload"; // 업로드 실패 메시지를 포함하여 뷰 반환
        }
        model.addAttribute("message", "Files uploaded successfully: " + files.size() + " files."); // 변경 필요
        return "file/upload";
    }

    @PostMapping("/url")
    public String uploadFileByUrl(@RequestParam("url") String imageUrl,
                                  Model model) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            model.addAttribute("message", "No URL provided for upload.");
            return "file/upload"; // 업로드 실패 메시지를 포함하여 뷰 반환
        }

        // 실제 URL 업로드 처리 로직이 필요합니다. 예를 들어, 백엔드 API 호출 등.
        model.addAttribute("message", "File uploaded successfully from URL: " + imageUrl); // 변경 필요

        return "file/upload";
    }


    @PostMapping(value = "/url-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<FileUploadResponse>> uploadFiles(@RequestPart("files") List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(List.of(new FileUploadResponse("No files provided for upload.")));
        }

        try {
            List<FileUploadResponse> responses = bookClient.uploadFiles(files).getBody();
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of(new FileUploadResponse("File upload failed: " + e.getMessage())));
        }
    }
}
