package com.ssafy.soyu.profileImage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
public class ProfileImage {

    @Id
    @GeneratedValue
    @Column(name = "profile_image_id")
    private Long id;

    private String savePath;

    private String originalName;

    private String saveName;

    public void makeImage(String savePath, String originalName, String saveName) {
        this.savePath = savePath;
        this.originalName = originalName;
        this.saveName = saveName;
    }
}
