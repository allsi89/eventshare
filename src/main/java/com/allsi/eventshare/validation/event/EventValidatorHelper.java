package com.allsi.eventshare.validation.event;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.allsi.eventshare.common.GlobalConstants.DATE_TIME_FORMAT;
import static com.allsi.eventshare.common.GlobalConstants.DATE_TIME_STR_TO_FORMAT;

@Component
public class EventValidatorHelper {

  boolean validateDate(String startDate, String startTime) {
    LocalDateTime start = LocalDateTime
        .parse(String.format(DATE_TIME_STR_TO_FORMAT,
            startDate, startTime),
            DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

    return !start.isBefore(LocalDateTime.now().plusDays(1));
  }
}
