package com.allsi.eventshare.repository;

import com.allsi.eventshare.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
  Optional<Category> findByName(String name);

  @Query("SELECT c FROM categories c ORDER BY c.name")
  List<Category> findAllOrderByName();
}
