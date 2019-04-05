package com.allsi.eventshare.repository;

import com.allsi.eventshare.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
  List<Role> findAllByAuthorityNot(String authority);

  Optional<Role> findByAuthority(String authority);
}
