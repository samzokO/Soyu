package com.ssafy.soyu.image.controller;

import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;

import com.ssafy.soyu.image.dto.response.ImageResponse;
import com.ssafy.soyu.image.entity.Image;
import com.ssafy.soyu.util.response.SuccessCode;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("image")
@RestController
public class ImageController {
  @Value("${file.path.upload-images}")
  String uploadImagePath;

  @GetMapping("/{sfolder}/{sfile}")
  public ResponseEntity<byte[]> getImageFile(@PathVariable("sfolder") String sfolder, @PathVariable("sfile") String sfile) throws IOException {
    String filePath = uploadImagePath + "/" + sfolder +  "/" + sfile;
    InputStream imageStream = new FileInputStream(filePath);
    byte[] imageByteArray = IOUtils.toByteArray(imageStream);
    imageStream.close();

    return new ResponseEntity<>(imageByteArray, new HttpHeaders(), HttpStatus.OK);
  }

  public static List<ImageResponse> getImageResponse(List<Image> image) {
    return image.stream()
        .map(i -> new ImageResponse(i.getSavePath(), i.getOriginalName(), i.getSaveName()))
        .collect(Collectors.toList());
  }
}
