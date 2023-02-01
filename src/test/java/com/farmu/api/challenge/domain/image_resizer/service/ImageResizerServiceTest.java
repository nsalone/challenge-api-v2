package com.farmu.api.challenge.domain.image_resizer.service;

import com.farmu.api.challenge.common.utils.TestUtils;
import com.farmu.api.challenge.domain.image_resizer.dto.ImageTO;
import com.farmu.api.challenge.domain.image_resizer.event.publisher.ImageResizePublisher;
import com.farmu.api.challenge.domain.image_resizer.store.ImageStore;
import lombok.SneakyThrows;
import org.jeasy.random.EasyRandom;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

@Test
public class ImageResizerServiceTest {

    protected static final EasyRandom RANDOM = TestUtils.newEasyRandom();

    @InjectMocks
    private ImageResizerService target;

    @Mock
    private ImageStore store;

    @Mock
    private ImageResizePublisher imageResizePublisher;


    @BeforeTest
    @SneakyThrows
    public void init() {
        openMocks(this).close();
    }

    @BeforeMethod
    public void resets() {
        reset(store, imageResizePublisher);
    }

    public void findById_test() {
        final ImageTO image = RANDOM.nextObject(ImageTO.class);
        when(store.findById(image.getId())).thenReturn(image);

        final ImageTO res = target.findById(image.getId());

        verify(store).findById(image.getId());
        assertEquals(image, res);
    }

    public void findByUUID_test() {
        final ImageTO image = RANDOM.nextObject(ImageTO.class);
        when(store.findByUUID(image.getUuid())).thenReturn(image);

        final ImageTO res = target.findByUUID(image.getUuid());

        verify(store).findByUUID(image.getUuid());
        assertEquals(image, res);
    }

    public void create() {
        final ImageTO req = RANDOM.nextObject(ImageTO.class);
        req.setId(null);
        req.setCreated(null);
        req.setUpdated(null);

        ImageTO expect = RANDOM.nextObject(ImageTO.class);
        expect.setId(1L);
        expect.setCreated(LocalDateTime.now());
        expect.setUpdated(LocalDateTime.now());

        when(store.save(req)).thenReturn(expect);

        final ImageTO res = target.create(req);

        verify(store).save(req);
        assertEquals(res, expect);
    }

    public void deleteById() {
        doNothing().when(store).deleteById(1L);
        target.deleteById(1L);
        verify(store).deleteById(1L);
    }

    public void uploadSingleFile_test() {

        ImageTO expect = RANDOM.nextObject(ImageTO.class);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "file.png",
                MediaType.IMAGE_PNG_VALUE,
                "200, 200".getBytes()
        );

        expect.setFileType(file.getContentType());
        expect.setFileName(file.getName());
        expect.setTargetHeight(100);
        expect.setTargetWidth(100);

        when(store.save(any(ImageTO.class))).thenReturn(expect);

        ImageTO result = target.uploadSingleFile(file, 100, 100);
        verify(store).save(any(ImageTO.class));
        verify(imageResizePublisher).publishEventToNotification(expect.getId());
        assertNotNull(result);
        assertEquals(result.getFileType(), expect.getFileType());
        assertEquals(result.getTargetHeight(), 100);
        assertEquals(result.getTargetWidth(), 100);

    }

    public void resize_test() throws IOException {

        byte[] bytes = Files.readAllBytes(Paths.get("src","test","resources","img", "hollow.png"));

        ImageTO expect = RANDOM.nextObject(ImageTO.class);
        MockMultipartFile file = new MockMultipartFile(
                "hollow",
                "hollow.png",
                MediaType.IMAGE_PNG_VALUE,
                bytes
        );

        expect.setFileType(file.getContentType());
        expect.setFileName(file.getName());
        expect.setTargetHeight(100);
        expect.setTargetWidth(100);

        when(store.save(any(ImageTO.class))).thenReturn(expect);

        byte[] result = target.resize(file, 100, 100);

        assertNotNull(result);
        assertTrue(result.length > 0);

    }
}
