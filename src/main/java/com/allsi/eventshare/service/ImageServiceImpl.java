package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Image;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.repository.ImageRepository;
import com.allsi.eventshare.service.util.UploadImageHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.allsi.eventshare.constants.Constants.INVALID_FILE;

@Service
public class ImageServiceImpl implements ImageService {
  private static final String NO_SUCH_IMAGE = "Image not found!";
  private final ImageRepository imageRepository;
  private final UploadImageHelper uploadImageHelper;
  private final ModelMapper modelMapper;

  @Autowired
  public ImageServiceImpl(ImageRepository imageRepository, UploadImageHelper uploadImageHelper, ModelMapper modelMapper) {
    this.imageRepository = imageRepository;
    this.uploadImageHelper = uploadImageHelper;
    this.modelMapper = modelMapper;
  }

  @Override
  public ImageServiceModel saveImgInDb(MultipartFile imgPart) throws IOException {
    if (imgPart == null) {
      throw new IllegalArgumentException(INVALID_FILE);
    }
    ImageServiceModel imageServiceModel = new ImageServiceModel(this.uploadImageHelper.uploadImg(imgPart));
    try{
      Image image = this.modelMapper.map(imageServiceModel, Image.class);
      image = this.imageRepository.saveAndFlush(image);
      return this.modelMapper.map(image, ImageServiceModel.class);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Image findImageById(String id) {
    return this.imageRepository.findById(id)
        .orElseThrow(()-> new IllegalArgumentException(NO_SUCH_IMAGE));
  }


  //  private static String UPLOAD_ROOT = "upload-dir";
//
//  private final ImageRepository imageRepository;
//  private final ResourceLoader resourceLoader;
//
//  @Autowired
//  public ImageServiceImpl(ImageRepository imageRepository, ResourceLoader resourceLoader) {
//    this.imageRepository = imageRepository;
//    this.resourceLoader = resourceLoader;
//  }
//
//  public Resource findImage(String filename) {
//    return this.resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + filename);
//  }
//
//  public void createImage(MultipartFile file) throws IOException {
//    if (file.isEmpty()) {
//      Files.copy(file.getInputStream(), Paths.get(UPLOAD_ROOT, file.getOriginalFilename()));
////      Image image = new Image();
//      this.imageRepository.saveAndFlush(new Image(file.getOriginalFilename()));
//    }
//  }
//
//  public void deleteImage(String id) throws IOException {
//    Image image = this.imageRepository.findById(id).orElse(null);
//    if (image != null) {
//      this.imageRepository.delete(image);
//      Files.deleteIfExists(Paths.get(UPLOAD_ROOT, image.getName()));
//    }
//  }
}
