package com.GymMate.backend.Resources;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String workoutName;
    
    @ElementCollection
    private List<String> sets;

    public Workout() {
    }

    public Workout(String workoutName, List<String> sets) {
        this.workoutName = workoutName;
        this.sets = sets;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getWorkoutName() {
        return workoutName;
    }
    
    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }
    
    public List<String> getSets() {
        return sets;
    }
    
    public void setSets(List<String> sets) {
        this.sets = sets;
    }
}