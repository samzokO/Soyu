package com.ssafy.soyu.profileImage.dto.response;

import com.ssafy.soyu.profileImage.ProfileImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileImageResponse {
  private String savePath;

  private String originalName;

  private String saveName;

  static public ProfileImageResponse getProfileImageResponse(ProfileImage profileImage) {
    ProfileImageResponse profileImageResponse = new ProfileImageResponse();
    if(profileImage == null) return profileImageResponse;
    profileImageResponse.savePath = profileImage.getSavePath();
    profileImageResponse.originalName = profileImage.getOriginalName();
    profileImageResponse.saveName = profileImage.getSaveName();
    return profileImageResponse;
  }
}
