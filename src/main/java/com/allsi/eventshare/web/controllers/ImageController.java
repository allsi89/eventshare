package com.allsi.eventshare.web.controllers;

import com.allsi.eventshare.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/images")
public class ImageController extends BaseController {
  private final ImageService imageService;

  @Autowired
  public ImageController(ImageService imageService) {
    this.imageService = imageService;
  }


//  public String upload(@RequestParam("file") MultipartFile file) throws IOException {
//    this.imageService.saveImgInDb(file)
//  }
}
