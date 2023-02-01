package com.farmu.api.challenge.domain.image_resizer.service;

import com.farmu.api.challenge.domain.image_resizer.dto.ImageTO;
import com.farmu.api.challenge.domain.image_resizer.dto.Status;
import com.farmu.api.challenge.domain.image_resizer.event.publisher.ImageResizePublisher;
import com.farmu.api.challenge.domain.image_resizer.store.ImageRepository;
import com.farmu.api.challenge.domain.image_resizer.store.ImageStore;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.farmu.api.challenge.common.provider.ImageResizeProvider.simpleResizeImage;
import static com.farmu.api.challenge.common.utils.ValidationUtils.checkArg;

@Service
public class ImageResizerService {

    @Autowired
    private ImageStore store;
    @Autowired
    private ImageResizePublisher imageResizePublisher;

    public ImageTO findById(final Long id) {
        checkArg(id, "id");
        return store.findById(id);
    }

    public ImageTO findByUUID(final String uuid) {
        checkArg(uuid, "uuid");
        return store.findByUUID(uuid);
    }

    public ImageTO create(final ImageTO image) {
        checkArg(image, "image");
        return store.save(image);
    }

    public ImageTO update(final ImageTO image) {
        checkArg(image, "image");
        return store.update(image);
    }

    public void deleteById(final Long id) {
        checkArg(id, "id");
        store.deleteById(id);
    }

    @SneakyThrows
    public ImageTO uploadSingleFile(MultipartFile file, int targetHeight, int targetWidth) {
        checkArg(file, "file");
        checkArg(targetHeight, "targetHeight");
        checkArg(targetWidth, "targetWidth");

        ImageTO image = create(buildImage(file, targetHeight, targetWidth));
        imageResizePublisher.publishEventToNotification(image.getId());

        return image;
    }

    @SneakyThrows
    public byte[] resize(MultipartFile file, int targetWidth, int targetHeight) {
        checkArg(file, "file");
        checkArg(targetHeight, "targetHeight");
        checkArg(targetWidth, "targetWidth");
        return simpleResizeImage(file.getBytes(), targetWidth, targetHeight);
    }

    @SneakyThrows
    private static ImageTO buildImage(MultipartFile file, int targetHeight, int targetWidth) {
        return ImageTO.builder()
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .targetHeight(targetHeight)
                .targetWidth(targetWidth)
                .data(file.getBytes())
                .uuid(UUID.randomUUID().toString())
                .status(Status.UPLOAD)
                .build();
    }

}