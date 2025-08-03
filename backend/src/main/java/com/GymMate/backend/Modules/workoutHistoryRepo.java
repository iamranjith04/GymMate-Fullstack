package com.GymMate.backend.Modules;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.GymMate.backend.Resources.WorkoutHistory;

@Repository
public interface workoutHistoryRepo extends JpaRepository<WorkoutHistory, Integer>{
    Optional<WorkoutHistory> findByMuscleNameAndDate(String muscleName, LocalDate date);

    @Query("SELECT w.sets FROM WorkoutHistory wh JOIN wh.WorkoutList w WHERE wh.muscleName = :muscleName AND wh.date = :date AND w.workoutName = :workoutName")
    List<String> findWorkoutAndDate(@Param("muscleName") String muscleName, @Param("date") LocalDate date, @Param("workoutName") String workoutName);
}