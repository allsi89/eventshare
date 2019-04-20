package com.allsi.eventshare.service;

import com.allsi.eventshare.errors.InvalidFileException;
import com.allsi.eventshare.repository.ImageRepository;
import com.allsi.eventshare.service.image.ImageService;
import com.allsi.eventshare.service.image.ImageServiceImpl;
import com.allsi.eventshare.util.CloudService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ImageServiceTests {
  private static final String URL = "url";
  private static final String DATA = "data";
  private static final String FILE_NAME = "filename.jpeg";
  private static final String TYPE ="text/plain";
  private static final String CONTENT = "some text";
  @Mock
  private ImageRepository imageRepository;
  @Mock
  private CloudService cloudService;

  @Spy
  private ModelMapper modelMapper;

  private ImageService imageService;

  @Before
  public void init() {
    this.imageService = new ImageServiceImpl(this.imageRepository,
        this.cloudService, this.modelMapper);
  }

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
