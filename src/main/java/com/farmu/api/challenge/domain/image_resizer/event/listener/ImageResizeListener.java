package com.farmu.api.challenge.domain.image_resizer.event.listener;

import com.farmu.api.challenge.common.provider.ImageResizeProvider;
import com.farmu.api.challenge.domain.image_resizer.dto.ImageResizeEventTO;
import com.farmu.api.challenge.domain.image_resizer.dto.ImageTO;
import com.farmu.api.challenge.domain.image_resizer.dto.Status;
import com.farmu.api.challenge.domain.image_resizer.service.ImageResizerService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ImageResizeListener implements ApplicationListener<ImageResizeEventTO> {

    @Autowired
    private ImageResizerService service;

    @SneakyThrows
    @Override
    public void onApplicationEvent(final ImageResizeEventTO event) {
        log.info("[onApplicationEvent]::INI");
        ImageTO image = service.findById(event.getId());
        log.info("Image name: {}", image.getFileName());

        try {
            byte[] dataResize = ImageResizeProvider.simpleResizeImage(image.getData(), image.getTargetWidth(), image.getTargetHeight());
            image.setDataResize(dataResize);
            image.setStatus(Status.OK);

        } catch (Exception e) {
            log.error("Error on resize image", e);
            image.setStatus(Status.FAIL);
        }

        service.update(image);
        log.info("[onApplicationEvent]::END");
    }

}
