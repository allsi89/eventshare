package com.allsi.eventshare.repository;

import com.allsi.eventshare.domain.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

  List<Event> findAllByCreator_UsernameAndStartDatetimeAfterOrderByStartDatetime(String username, LocalDateTime dateTime);

  Optional<Event> findByIdAndStartDatetimeAfter(String id, LocalDateTime dateTime);

  @Query("SELECT e FROM events e GROUP BY e.country")
  List<Event> findAllGroupByCountry();

  @Query("SELECT e FROM events e GROUP BY e.category")
  List<Event> findAllGroupByCategory();

  @Query("SELECT e FROM events e GROUP BY e.creator")
  List<Event> findAllGroupByCreator();

  @Query(value = "SELECT e FROM events e " +
      "WHERE e.country.id LIKE :countryId")
  List<Event> findAllByCountry(@Param(value = "countryId") String countryId);


  @Query(value = "SELECT e FROM events e " +
      "WHERE e.category.id LIKE :categoryId")
  List<Event> findAllByCategory(@Param(value = "categoryId") String categoryId);

  @Query(value = "SELECT e FROM events e " +
      "WHERE e.creator.id LIKE :creatorId")
  List<Event> findAllByCreator(@Param(value = "creatorId") String creatorId);
}
