package com.allsi.eventshare.config;

import com.allsi.eventshare.domain.entities.Event;
import com.allsi.eventshare.domain.entities.Organisation;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.domain.models.binding.EventBindingModel;
import com.allsi.eventshare.domain.models.service.EventServiceModel;
import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;
import com.allsi.eventshare.domain.models.service.UserServiceModel;
import com.allsi.eventshare.domain.models.view.EventViewModel;
import com.allsi.eventshare.domain.models.view.OrganisationViewModel;
//import com.allsi.eventshare.util.ModelMapper;
//import com.allsi.eventshare.util.ModelMapperExt;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.print.attribute.standard.Destination;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Configuration
public class ApplicationBeanConfiguration {
  private static final String PLUS_CHAR = "+";
  private static final String FORMAT_STR = "%s%s%s";


//  @Bean
//  public ModelMapper modelMapper() {
//    return new ModelMapperExt();
//  }

//  @Bean
//  public ModelMapper modelMapper() {
//    ModelMapper modelMapper = new ModelMapperExt();
//    modelMapper.getConfiguration()
//        .setPropertyCondition(Conditions.isNotNull());


//    modelMapper.createTypeMap(UserServiceModel.class, User.class)
//        .addMappings(mapper -> mapper.skip((User::setImage)));
//
//    modelMapper.createTypeMap(OrganisationServiceModel.class, Organisation.class)
//        .addMappings(mapper -> mapper.skip((Organisation::setImage)));

//    modelMapper.createTypeMap(EventBindingModel.class, EventServiceModel.class)
//        .addMapping(EventBindingModel::getStartsOnDate,
//            (dest, value) -> dest.setStartsOnDate(Instant
//                .ofEpochMilli((Long) value)
//                .atZone(ZoneId.systemDefault())
//                .toLocalDate()));





//    modelMapper.createTypeMap(
//        EventServiceModel.class, EventViewModel.class)
//        .addMapping(src->src.getCountry().getNiceName(),
//            (dest,value) -> dest.setCountry((String)value));

//    return new ModelMapper();
//  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }


}
