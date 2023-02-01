package com.farmu.api.challenge.domain.image_resizer.event.publisher;

import com.farmu.api.challenge.domain.image_resizer.dto.ImageResizeEventTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ImageResizePublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    public ImageResizePublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Async
    public void publishEventToNotification(final Long id) {
        log.info("[publishEventToNotification]::START");
        applicationEventPublisher.publishEvent(new ImageResizeEventTO(this, id));
        log.info("[publishEventToNotification::END]");
    }
}
