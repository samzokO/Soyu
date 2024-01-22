package com.ssafy.soyu.file;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "file")
@Getter
public class File {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    private String savePath;

    private String originalName;

    private String saveName;
}
