package com.farmu.api.challenge.domain.image_resizer.controller;

import com.farmu.api.challenge.common.controller.ControllerTest;
import com.farmu.api.challenge.common.utils.TestUtils;
import com.farmu.api.challenge.domain.image_resizer.dto.ImageTO;
import com.farmu.api.challenge.domain.image_resizer.service.ImageResizerService;
import org.apache.commons.lang3.StringUtils;
import org.jeasy.random.EasyRandom;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

@Test
public class ImageResizerControllerTest extends ControllerTest {

    private static final EasyRandom RANDOM = TestUtils.newEasyRandom();

    @InjectMocks
    private ImageResizerController target;
    @Mock
    private ImageResizerService service;

    @Override
    protected Object getTarget() {
        return target;
    }

    public void upload_test() throws Exception {

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "file.png",
                MediaType.IMAGE_PNG_VALUE,
                "200, 200".getBytes()
        );

        when(service.uploadSingleFile(file, 100, 100))
                .thenReturn(RANDOM.nextObject(ImageTO.class));

        MvcResult result = mockMvc.perform(multipart("/api/v1/image-resizer/upload")
                        .file(file)
                        .param("targetHeight", String.valueOf(100))
                        .param("targetWidth", String.valueOf(100))
                )
                .andExpect(status().isOk())
                .andReturn();

        verify(service).uploadSingleFile(file, 100, 100);
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
        assertTrue(StringUtils.containsAny(result.getResponse().getContentAsString(), "file.png"));
    }

    public void resize_test() throws Exception {

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "file.png",
                MediaType.IMAGE_PNG_VALUE,
                "200, 200".getBytes()
        );

        when(service.uploadSingleFile(file, 100, 100))
                .thenReturn(RANDOM.nextObject(ImageTO.class));

        MvcResult result = mockMvc.perform(multipart("/api/v1/image-resizer/resize")
                        .file(file)
                        .param("targetHeight", String.valueOf(100))
                        .param("targetWidth", String.valueOf(100))
                )
                .andExpect(status().isOk())
                .andReturn();

        verify(service).resize(file, 100, 100);
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    public void getImage_test() throws Exception {

        final String id = RANDOM.nextObject(String.class);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "file.png",
                MediaType.IMAGE_PNG_VALUE,
                "200, 200".getBytes()
        );

        ImageTO image = RANDOM.nextObject(ImageTO.class);
        image.setData(image.getData());
        image.setFileType("image/png");

        when(service.findByUUID(eq(id))).thenReturn(image);

        MvcResult result = mockMvc.perform(get("/api/v1/image-resizer/" + id))
                .andExpect(status().isOk())
                .andReturn();

        verify(service).findByUUID(id);
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

    public void getOriginalImage_test() throws Exception {

        final String id = RANDOM.nextObject(String.class);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "file.png",
                MediaType.IMAGE_PNG_VALUE,
                "200, 200".getBytes()
        );

        ImageTO image = RANDOM.nextObject(ImageTO.class);
        image.setData(image.getData());
        image.setFileType("image/png");

        when(service.findByUUID(eq(id))).thenReturn(image);

        MvcResult result = mockMvc.perform(get("/api/v1/image-resizer/original/" + id))
                .andExpect(status().isOk())
                .andReturn();

        verify(service).findByUUID(id);
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());
    }

}
