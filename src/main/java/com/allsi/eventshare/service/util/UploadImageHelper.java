package com.allsi.eventshare.service.util;

import com.google.cloud.storage.Blob;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadImageHelper {
  String uploadImg(MultipartFile imgPart) throws IOException;
}
