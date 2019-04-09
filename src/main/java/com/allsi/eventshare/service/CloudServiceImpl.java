package com.allsi.eventshare.service;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Component
public class CloudServiceImpl implements CloudService {
  private final Cloudinary cloudinary;

  @Autowired
  public CloudServiceImpl(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  @Override
  public String uploadImage(MultipartFile multipartFile) throws IOException {
    File file = File.createTempFile("temp", multipartFile.getOriginalFilename());
    multipartFile.transferTo(file);

    try{
      return this.cloudinary
           .uploader()
           .upload(file, new HashMap())
           .get("url").toString();

    } catch (Exception e) {

      e.printStackTrace();
      return null;
    }
  }
}
