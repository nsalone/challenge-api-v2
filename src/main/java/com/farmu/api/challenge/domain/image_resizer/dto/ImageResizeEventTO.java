package com.farmu.api.challenge.domain.image_resizer.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ImageResizeEventTO extends ApplicationEvent {

    private Long id;

    private int targetWidth;

    private int targetHeight;

    public ImageResizeEventTO(Object source, final Long id) {
        super(source);
        this.id = id;
    }

}
