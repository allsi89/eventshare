package com.allsi.eventshare.config;

import com.allsi.eventshare.domain.models.service.EventServiceModel;
import com.allsi.eventshare.domain.models.service.OrganisationServiceModel;
import com.allsi.eventshare.domain.models.view.EventViewModel;
import com.allsi.eventshare.domain.models.view.OrganisationViewModel;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationBeanConfiguration {
  private static final String PLUS_CHAR = "+";
  private static final String FORMAT_STR = "%s%s%s";

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
        .setPropertyCondition(Conditions.isNotNull());

//    modelMapper.createTypeMap(
//        EventServiceModel.class, EventViewModel.class)
//        .addMapping(src->src.getCountry().getNiceName(),
//            (dest,value) -> dest.setCountry((String)value));

    return new ModelMapper();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }


}
