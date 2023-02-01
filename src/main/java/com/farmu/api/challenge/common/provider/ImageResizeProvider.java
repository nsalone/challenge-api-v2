package com.farmu.api.challenge.common.provider;

import com.farmu.api.challenge.common.exception.ImageResizeException;
import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;
import java.util.Objects;

import static com.farmu.api.challenge.common.utils.ImageUtils.toBufferedImage;
import static com.farmu.api.challenge.common.utils.ImageUtils.toByteArray;

public class ImageResizeProvider {

    public static byte[] simpleResizeImage(byte[] bytes, int targetWidth, int targetHeight) throws Exception {
        BufferedImage bufferedImage = Scalr.resize(toBufferedImage(bytes), targetWidth, targetHeight);

        if(Objects.isNull(bufferedImage.getType()))
            throw new ImageResizeException("Error on image resize");

        return toByteArray(bufferedImage, "png");
    }
}
