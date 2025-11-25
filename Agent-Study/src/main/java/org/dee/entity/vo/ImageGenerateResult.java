package org.dee.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ImageGenerateResult {

    @Schema(description = "Image URL")
    private String imageUrl;

    @Schema(description = "Base64 Encoded Image")
    private String imageBase64;
    @Schema(description = "Image Name")
    private String imageName;


    public ImageGenerateResult(String imageUrl, String imageBase64, String imageName) {
        this.imageUrl = imageUrl;
        this.imageBase64 = imageBase64;
        this.imageName = imageName;
    }
}
