package com.edusyspro.api.repository;

import com.edusyspro.api.model.CourseProgramTiming;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseProgramTimingRepository extends JpaRepository<CourseProgramTiming, Long> {
}
