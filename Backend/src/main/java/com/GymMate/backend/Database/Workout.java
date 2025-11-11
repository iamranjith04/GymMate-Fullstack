package com.GymMate.backend.Database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "workouts")
public class Workout {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate date;
    
    private String muscleName;  

    @ElementCollection
    @CollectionTable(name = "workout_details", joinColumns = @JoinColumn(name = "workout_id"))
    private List<WorkoutDetails> workoutDetails = new ArrayList<>();
    
    public Workout() {
    }
    
    public Workout(LocalDate date, String muscleName, List<WorkoutDetails> workoutDetails) {
        this.date = date;
        this.muscleName = muscleName;
        this.workoutDetails = workoutDetails;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMuscleName() {
        return muscleName;
    }

    public void setMuscleName(String muscleName) {
        this.muscleName = muscleName;
    }

    public List<WorkoutDetails> getWorkoutDetails() {
        return workoutDetails;
    }

    public void setWorkoutDetails(List<WorkoutDetails> workoutDetails) {
        this.workoutDetails = workoutDetails;
    }

    public void addWorkoutDetail(WorkoutDetails detail) {
        this.workoutDetails.add(detail);
    }
}