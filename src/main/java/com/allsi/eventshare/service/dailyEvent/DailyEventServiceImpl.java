package com.allsi.eventshare.service.dailyEvent;

import com.allsi.eventshare.domain.entities.DailyEvent;
import com.allsi.eventshare.domain.entities.Event;
import com.allsi.eventshare.repository.DailyEventRepository;
import com.allsi.eventshare.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class DailyEventServiceImpl implements DailyEventService {
  private final DailyEventRepository dailyEventRepository;
  private final EventRepository eventRepository;

  @Autowired
  public DailyEventServiceImpl(DailyEventRepository dailyEventRepository, EventRepository eventRepository) {
    this.dailyEventRepository = dailyEventRepository;
    this.eventRepository = eventRepository;
  }

  @Scheduled(fixedRate = 30000)
  public void generateDailyEvents() {
    this.dailyEventRepository.deleteAll();
    List<Event> events = this.eventRepository
        .findAllByStartDatetimeAfter(LocalDateTime.now().plusHours(6));

    if (events.isEmpty()) {
      return;
    }

    Random rnd = new Random();

    DailyEvent dailyEvent = new DailyEvent();
    dailyEvent.setEvent(events.get(rnd.nextInt(events.size())));
    this.dailyEventRepository.save(dailyEvent);
  }
}
