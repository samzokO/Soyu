package com.ssafy.soyu.profileImage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileImageResponse {
  private String savePath;

  private String originalName;

  private String saveName;
}
