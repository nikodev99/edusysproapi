package com.edusyspro.api.repository;

import com.edusyspro.api.model.Schedule;
import com.edusyspro.api.model.enums.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s where s.classeEntity.id = ?1 and s.dayOfWeek = ?2")
    List<Schedule> findAllByClasseEntityId(int classeId, Day currentDay);

}
