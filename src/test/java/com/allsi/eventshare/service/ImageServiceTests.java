package com.allsi.eventshare.service;

import com.allsi.eventshare.errors.InvalidFileException;
import com.allsi.eventshare.repository.ImageRepository;
import com.allsi.eventshare.service.image.ImageService;
import com.allsi.eventshare.util.CloudService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageServiceTests {
  private static final String URL = "url";
  private static final String DATA = "data";
  private static final String FILE_NAME = "filename.jpeg";
  private static final String TYPE ="text/plain";
  private static final String CONTENT = "some text";
  @MockBean
  private ImageRepository imageRepository;
  @MockBean
  private CloudService cloudService;

  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private ImageService imageService;

  @Test
  public void saveInDb_withCorrectFile_returnsCorrect() throws IOException {
    MultipartFile file = new MockMultipartFile(DATA, FILE_NAME, TYPE , CONTENT.getBytes());

    when(this.cloudService.uploadImage(file))
        .thenReturn(URL);

    this.imageService.saveInDb(file);

    verify(this.imageRepository).saveAndFlush(any());
  }

  @Test(expected = InvalidFileException.class)
  public void saveInDb_withNotCorrectFile_throwsException() throws IOException {
    MultipartFile file = new MockMultipartFile(DATA, FILE_NAME, TYPE , CONTENT.getBytes());

    when(this.cloudService.uploadImage(file))
        .thenReturn(null);

    this.imageService.saveInDb(file);
  }

  @Test(expected = InvalidFileException.class)
  public void saveInDb_withNullCorrectFile_throwsException() throws IOException {
    this.imageService.saveInDb(null);
  }
}
