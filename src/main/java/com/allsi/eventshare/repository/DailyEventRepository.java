package com.allsi.eventshare.repository;

import com.allsi.eventshare.domain.entities.DailyEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyEventRepository extends JpaRepository<DailyEvent, String> {
}
