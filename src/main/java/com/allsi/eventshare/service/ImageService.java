package com.allsi.eventshare.service;


import com.allsi.eventshare.domain.entities.Image;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
  ImageServiceModel saveImgInDb(MultipartFile imgPart) throws IOException;

  Image findImageById(String id);
}