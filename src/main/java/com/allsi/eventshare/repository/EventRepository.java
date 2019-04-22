package com.allsi.eventshare.repository;

import com.allsi.eventshare.domain.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

  @Query("SELECT e FROM events e GROUP BY e.country")
  List<Event> findAllGroupByCountry();

  @Query("SELECT e FROM events e GROUP BY e.category")
  List<Event> findAllGroupByCategory();

  @Query("SELECT e FROM events e GROUP BY e.creator")
  List<Event> findAllGroupByCreator();

  List<Event> findAllByCreator_IdOrderByStartDatetimeDesc(String creatorId);

  List<Event> findAllByCategory_Id(String categoryId);

  List<Event> findAllByCountry_Id(String countryId);
}
