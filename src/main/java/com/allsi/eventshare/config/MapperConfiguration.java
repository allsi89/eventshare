package com.allsi.eventshare.config;

import com.allsi.eventshare.domain.entities.Organisation;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;
import com.allsi.eventshare.domain.models.service.UserServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
  private static final String DATE_FORMAT = "dd-MMM-yyyy";
  private static final String TIME_FORMAT = "hh:mm a";
  public static final String DATE_TIME_FORMAT = "dd-MMM-yyyy hh:mm a";

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();

    modelMapper.createTypeMap(UserServiceModel.class, User.class)
        .addMappings(mapper -> mapper.skip((User::setImage)))
        .addMappings(mapper -> mapper.skip(User::setId))
        .addMappings(mapper -> mapper.skip(User::setCreatedEvents))
        .addMappings(mapper -> mapper.skip(User::setAttendanceEvents))
        .addMappings(mapper -> mapper.skip(User::setRoles));

    modelMapper.createTypeMap(OrganisationServiceModel.class, Organisation.class)
        .addMappings(mapper -> mapper.skip(Organisation:: setId))
        .addMappings(mapper -> mapper.skip((Organisation::setImage)))
        .addMappings(mapper -> mapper.skip((Organisation::setCountry)))
        .addMappings(mapper -> mapper.skip((Organisation::setUser)));


    modelMapper.validate();
    return modelMapper;
  }
}
