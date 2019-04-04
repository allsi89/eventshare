package com.allsi.eventshare.service.util;

import com.google.api.services.storage.model.ObjectAccessControl;
import com.google.api.services.storage.model.StorageObject;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.allsi.eventshare.constants.Constants.INVALID_FILE;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class UploadImageHelperImpl implements UploadImageHelper {
  private static final String BUCKET_NAME = "event-share-bucket";
  private static final String PATTERN = "-YYYY-MM-dd-HHmmssSSS";
  private static final int ONE = 1;
  private static final String JPG = "jpg";
  private static final String JPEG = "jpeg";
  private static final String GIF = "gif";
  private static final String PNG = "png";
  private static final String INVALID_FILE_EXTENSION = "The selected file must be jpg, jpeg, gif or png image.";

  private final Storage storage;

  @Autowired
  public UploadImageHelperImpl(Storage storage) {
    this.storage = storage;
  }


  @Override
  public String uploadImg(MultipartFile imgPart) throws IOException {
    String dateTime = LocalDateTime
        .now()
        .format(DateTimeFormatter
            .ofPattern(PATTERN));

    String fileName = imgPart.getOriginalFilename();
    validateImage(fileName);
    String name = imgPart.getOriginalFilename() + dateTime;
    BlobId blobId = BlobId.of(BUCKET_NAME, name);

    BlobInfo blobInfo = BlobInfo
        .newBuilder(blobId)
        .setContentType("text/plain")
        .build();

//    BlobInfo blobInfo = this.storage.create(
//        BlobInfo.newBuilder(BUCKET_NAME, name)
//            .setAcl(new ArrayList<>(Collections.singletonList(Acl
//                .of(Acl.User.ofAllUsers(), Acl.Role.OWNER))))
//            .build(),
//        imgPart.getInputStream());
    Blob blob = this.storage.create(blobInfo, name.getBytes(UTF_8));

    return blob.getMediaLink();
  }

  private void validateImage(String fileName) {
    if (fileName == null || fileName.isEmpty() || !fileName.contains(".")) {
      throw new IllegalArgumentException(INVALID_FILE);
    }

    String extension = fileName.substring(fileName.lastIndexOf(".") + ONE);
    List<String> allowedExt = new ArrayList<>() {{
      add(JPG);
      add(JPEG);
      add(PNG);
      add(GIF);
    }};

    if (!allowedExt.contains(extension)) {
      throw new IllegalArgumentException(INVALID_FILE_EXTENSION);
    }
  }
}
