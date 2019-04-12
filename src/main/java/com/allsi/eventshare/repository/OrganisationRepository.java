package com.allsi.eventshare.repository;

import com.allsi.eventshare.domain.entities.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, String> {

  Optional<Organisation> findByUser_Id(String id);

  Optional<Organisation> findByName(String name);

  Optional<Organisation> findByEmail(String email);

  Optional<Organisation> findByUser_Username(String username);
}
