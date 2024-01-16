package com.spring.SpringBoot.Controllers;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.spring.SpringBoot.Models.ResponseObject;
import com.spring.SpringBoot.Service.IStorageService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/api/v1/FileUpload")
public class FileUploadController {

    @Autowired
    private IStorageService storageService;

    // This controller receive file/image from client
    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String generatedFileName = storageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "upload file successfully", generatedFileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    // get image's url
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = storageService.readFileContent(fileName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getUploadFile() {
        try {
            List<String> urls = storageService.loadAll()
                    .map(path -> {
                        String urlPath = MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class, "readDetailFile",
                                        path.getFileName().toString())
                                .build().toUri().toString();
                        return urlPath;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseObject("ok", "List file successfully", urls));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseObject("failed", "List files failed", new String[] {}));
        }
    }

}
