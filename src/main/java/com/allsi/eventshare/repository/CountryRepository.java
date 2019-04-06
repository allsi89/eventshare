package com.allsi.eventshare.repository;

import com.allsi.eventshare.domain.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
  //TODO
//  @Query("")
//  List<Country> findAllCountriesWithEvents();

  @Query("SELECT c FROM countries c ORDER BY c.niceName")
  List<Country> getAllCountriesOrOrderByNiceName();
}
