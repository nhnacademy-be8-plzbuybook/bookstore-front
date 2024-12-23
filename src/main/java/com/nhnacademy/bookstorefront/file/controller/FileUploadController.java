package com.nhnacademy.bookstorefront.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/upload")
public class FileUploadController {


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
}
