package com.allsi.eventshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class EventshareApplication {

  public static void main(String[] args) {
    SpringApplication.run(EventshareApplication.class, args);
  }

  @PostConstruct
  public void init(){
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

}
