package com.farmu.api.challenge.domain.image_resizer.controller;

import com.farmu.api.challenge.domain.image_resizer.dto.ImageTO;
import com.farmu.api.challenge.domain.image_resizer.service.ImageResizerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/image-resizer")
public class ImageResizerController {

    @Autowired
    private ImageResizerService service;

    @PostMapping("/upload")
    public ResponseEntity uploadSingleFile( @RequestParam("file") MultipartFile file,
                                            @RequestParam("targetHeight") int targetHeight,
                                            @RequestParam("targetWidth") int targetWidth) {
        log.info("[uploadSingleFile]::");
        return ResponseEntity.ok(service.uploadSingleFile(file, targetHeight, targetWidth));
    }

    @PostMapping("/resize")
    public ResponseEntity<byte[]> resize( @RequestParam("file") MultipartFile file,
                                            @RequestParam("targetHeight") int targetHeight,
                                            @RequestParam("targetWidth") int targetWidth) {
        log.info("[resize]::");
        byte[] bytes = service.resize(file, targetHeight, targetWidth);
        return ResponseEntity.ok().contentType(MediaType.valueOf(Objects.requireNonNull(file.getContentType()))).body(bytes);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<byte[]> getImage(@PathVariable String uuid) {
        log.info("[getImage]::");
        ImageTO image = service.findByUUID(uuid);
        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getFileType())).body(image.getDataResize());
    }

    @GetMapping("/original/{uuid}")
    public ResponseEntity<byte[]> getOriginalImage(@PathVariable String uuid) {
        log.info("[getOriginalImage]::");
        ImageTO image = service.findByUUID(uuid);
        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getFileType())).body(image.getData());
    }

}