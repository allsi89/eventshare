package com.allsi.eventshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
public class EventshareApplication {

  public static void main(String[] args) {
    SpringApplication.run(EventshareApplication.class, args);
  }

  /*
  Config in database timezone so zones are saved same as on input - no conversion
   */
  @PostConstruct
  public void init(){
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));   // It will set UTC timezone
    System.out.println("Spring boot application running in UTC timezone :"+new Date());   // It will print UTC timezone
  }

}
