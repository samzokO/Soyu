package com.ssafy.soyu.image.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageResponse {
  private String savePath;

  private String originalName;

  private String saveName;
}
