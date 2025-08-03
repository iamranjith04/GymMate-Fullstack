package com.GymMate.backend.Modules;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.GymMate.backend.Resources.Muscles;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface GymMateRepo extends JpaRepository<Muscles, String>{
    Optional<Muscles> findByMuscleName(String muscleName);

    @Modifying
    @Transactional
    @Query("UPDATE Muscles m SET m.lastWorkoutDate = :date WHERE m.muscleName = :muscleName")
    int updateLastDate(@Param("muscleName") String muscleName, @Param("date") LocalDate date);
;
}
