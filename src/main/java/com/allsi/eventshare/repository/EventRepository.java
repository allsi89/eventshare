package com.allsi.eventshare.repository;

import com.allsi.eventshare.domain.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

//  List<Event> findAllByCountry_Id(int id);
//
//  List<Event> findAllByCity_Id(String id);
//
//  List<Event> findAllByCountry_IdAndCity_Id(int countryId, String cityId);
//
//  List<Event> findAllByCreator_Username(String username);
//
//  //TODO
//  @Query("SELECT e FROM events e\n" +
//      "JOIN users_attendance_events uae\n" +
//      "  on e.id = uae.event_id\n" +
//      "JOIN users u\n" +
//      "  on uae.used_id = u.id\n" +
//      "WHERE u.id = :id")
//  List<Event> getUserAttendanceEvents(@Param("id") String id);
}
