package com.allsi.eventshare.config;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationBeanConfiguration {
//  @Bean
//  public CommandLineRunner setUp(){
//
//  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public Storage storage() {
    return StorageOptions.getDefaultInstance().getService();
  }
}
