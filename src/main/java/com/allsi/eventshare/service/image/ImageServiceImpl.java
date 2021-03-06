package com.allsi.eventshare.service.image;

import com.allsi.eventshare.domain.entities.Image;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.errors.ImageNotFoundException;
import com.allsi.eventshare.errors.InvalidFileException;
import com.allsi.eventshare.repository.ImageRepository;
import com.allsi.eventshare.util.CloudService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.allsi.eventshare.common.GlobalConstants.IMAGE_NOT_FOUND;
import static com.allsi.eventshare.common.GlobalConstants.INVALID_FILE;

@Service
public class ImageServiceImpl implements ImageService {
  private final ImageRepository imageRepository;
  private final CloudService cloudService;
  private final ModelMapper modelMapper;

  @Autowired
  public ImageServiceImpl(ImageRepository imageRepository, CloudService cloudService, ModelMapper modelMapper) {
    this.imageRepository = imageRepository;
    this.cloudService = cloudService;
    this.modelMapper = modelMapper;
  }

  @Override
  public ImageServiceModel saveInDb(MultipartFile file) throws IOException {
    String url = this.cloudService.uploadImage(file);

    if (url == null){
      throw new InvalidFileException(INVALID_FILE);
    }

    Image image = new Image(url);
    this.imageRepository.saveAndFlush(image);

    ImageServiceModel imageServiceModel = this.modelMapper.map(image, ImageServiceModel.class);
    imageServiceModel.setUrl(url);

    return imageServiceModel;
  }

  @Override
  public ImageServiceModel findImageById(String pictureId) {
    Image image = this.imageRepository.findById(pictureId)
        .orElseThrow(() -> new ImageNotFoundException(IMAGE_NOT_FOUND));
    return this.modelMapper.map(image, ImageServiceModel.class);
  }

  @Override
  public void removeImageById(String pictureId) {
    this.imageRepository.deleteById(pictureId);
  }

}
