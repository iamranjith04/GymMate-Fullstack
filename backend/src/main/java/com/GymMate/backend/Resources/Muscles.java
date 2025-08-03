package com.GymMate.backend.Resources;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table
public class Muscles {
    @Id
    private String muscleName;

    @ElementCollection
    private List<String> workout;
    private LocalDate lastWorkoutDate;
    
    public Muscles() {
    }

    public Muscles(String muscleName, List<String> workout, LocalDate lastWorkoutDate) {
        this.muscleName = muscleName;
        this.workout = workout;
        this.lastWorkoutDate = lastWorkoutDate;
    }

    public String getMuscleName() {
        return muscleName;
    }

    public void setMuscleName(String muscleName) {
        this.muscleName = muscleName;
    }

    public List<String> getWorkout() {
        return workout;
    }

    public void setWorkout(List<String> workout) {
        this.workout = workout;
    }

    public LocalDate getLastWorkoutDate() {
        return lastWorkoutDate;
    }

    public void setLastWorkoutDate(LocalDate lastWorkoutDate) {
        this.lastWorkoutDate = lastWorkoutDate;
    }

    
    
}
