package com.example.controllers;

import com.example.models.ResponseObject;
import com.example.services.IStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/file-upload")
public class FileUploadController {
    
    @Autowired
    private IStorageService _storageService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file")MultipartFile file) {
        try {
            String generatedFileName = _storageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Upload file successfully", generatedFileName)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("failed", e.getMessage(), "")
            );
        }
    }
    
    //get image's file
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> readFile(@PathVariable String fileName) {
        try {
            byte[] bytes = _storageService.readFileContent(fileName);
            return ResponseEntity.ok()
                                .contentType(MediaType.IMAGE_JPEG)
                                .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    //Load all upload files
    @GetMapping("/get-all-file")
    public ResponseEntity<ResponseObject> getUpLoadFiles() {
        try {
            List<String> urls = _storageService.loadAll()
                    .map(path -> {
                        //convert fileName to url(send request "readDetailFile")
                        String urlPath = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "readFile", path.getFileName().toString()).build().toUri().toString();
                        return urlPath;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(
                new ResponseObject("ok", "List files successfully", urls)
            );                   
        } catch (Exception e) {
            return ResponseEntity.ok(
                new ResponseObject("failed", "List files failed", new String[] {})
            );
        }
    }
}
