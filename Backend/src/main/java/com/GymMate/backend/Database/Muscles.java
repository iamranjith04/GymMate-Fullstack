package com.GymMate.backend.Database;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Muscles {
    @Id
    String muscleName;
    List<String> Workouts;
    public Muscles() {
    }
    public Muscles(String muscleName, List<String> workouts) {
        this.muscleName = muscleName;
        Workouts = workouts;
    }
   
    public String getMuscleName() {
        return muscleName;
    }
    public void setMuscleName(String muscleName) {
        this.muscleName = muscleName;
    }
    public List<String> getWorkouts() {
        return Workouts;
    }
    public void setWorkouts(List<String> workouts) {
        Workouts = workouts;
    }
    
}
