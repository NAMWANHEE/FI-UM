package com.example.pium.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class ImageUploadController {

    private static final String UPLOAD_DIR = "/app/images";

    @PostMapping("/image")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("request to /api/v1//image [Method: POST]");
        try {
            // 원래 파일의 확장자 가져오기
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // UUID를 이용하여 고유한 파일명 생성
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // 파일 저장 경로 설정
            Path path = Paths.get(UPLOAD_DIR, uniqueFilename);

            // 파일 저장
            file.transferTo(new File(path.toString()));

            String imageUrl = "image/" + uniqueFilename;
            return new ResponseEntity<>(imageUrl, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            log.error("INTERNAL_SERVER_ERROR");
            return new ResponseEntity<>("Failed to upload the file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/image/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        log.info("request to /api/v1//image/{filename} [Method: GET]");
        Path file = Paths.get(UPLOAD_DIR).resolve(filename);
        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                String contentType = determineContentType(filename);
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
            } else {
                log.error("NOT_FOUND ERROR");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (MalformedURLException e) {
            log.error("NOT_FOUND ERROR");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    private String determineContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "png":
                return "image/png";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "gif":
                return "image/gif";
            // 추가적으로 필요한 다른 이미지 형식에 대한 케이스를 추가할 수 있습니다.
            default:
                return "application/octet-stream";
        }
    }







}