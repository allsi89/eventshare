package com.allsi.eventshare.repository;

import com.allsi.eventshare.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
  Optional<Category> findByName(String name);
}
