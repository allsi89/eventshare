package com.allsi.eventshare;

import com.allsi.eventshare.web.controllers.FileUploadController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@SpringBootApplication
//@ComponentScan({"com.allsi.eventshare", "com.allsi.eventshare.web.controllers"})
public class EventshareApplication {

  public static void main(String[] args) {
//    new File(FileUploadController.uploadDirectory).mkdir();
    SpringApplication.run(EventshareApplication.class, args);
//    "com.allsi.eventshare.web.controllers"
  }

}
