package com.GymMate.backend.Database;

import jakarta.persistence.Embeddable;

@Embeddable
public class WorkoutDetails {
    private String workoutName;
    private float set1Weight;
    private int set1Reps;
    private float set2Weight;
    private int set2Reps;
    private float set3Weight;
    private int set3Reps;
    
    public WorkoutDetails() {
    }
    
    public WorkoutDetails(String workoutName, float set1Weight, int set1Reps, 
                         float set2Weight, int set2Reps, float set3Weight, int set3Reps) {
        this.workoutName = workoutName;
        this.set1Weight = set1Weight;
        this.set1Reps = set1Reps;
        this.set2Weight = set2Weight;
        this.set2Reps = set2Reps;
        this.set3Weight = set3Weight;
        this.set3Reps = set3Reps;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public float getSet1Weight() {
        return set1Weight;
    }

    public void setSet1Weight(float set1Weight) {
        this.set1Weight = set1Weight;
    }

    public int getSet1Reps() {
        return set1Reps;
    }

    public void setSet1Reps(int set1Reps) {
        this.set1Reps = set1Reps;
    }

    public float getSet2Weight() {
        return set2Weight;
    }

    public void setSet2Weight(float set2Weight) {
        this.set2Weight = set2Weight;
    }

    public int getSet2Reps() {
        return set2Reps;
    }

    public void setSet2Reps(int set2Reps) {
        this.set2Reps = set2Reps;
    }

    public float getSet3Weight() {
        return set3Weight;
    }

    public void setSet3Weight(float set3Weight) {
        this.set3Weight = set3Weight;
    }

    public int getSet3Reps() {
        return set3Reps;
    }

    public void setSet3Reps(int set3Reps) {
        this.set3Reps = set3Reps;
    }
}