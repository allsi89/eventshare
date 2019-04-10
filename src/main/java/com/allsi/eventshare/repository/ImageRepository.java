package com.allsi.eventshare.repository;

import com.allsi.eventshare.domain.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {

  List<Image> findAllByEvent_Id(String id);
}
