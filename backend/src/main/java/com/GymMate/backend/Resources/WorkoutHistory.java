package com.GymMate.backend.Resources;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class WorkoutHistory {

    @Id
    @SequenceGenerator(
        name="GymMate_sequence",
        sequenceName="GymMate_sequence",
        allocationSize = 1)

    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "GymMate_sequence"
    )
    private int id;
    private String muscleName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "workout_history_workout_list",
        joinColumns = @JoinColumn(name = "workout_history_id"),
        inverseJoinColumns = @JoinColumn(name = "workout_id")
    )
    private List<Workout> WorkoutList;

    private LocalDate date;
    
    public WorkoutHistory() {
    }
    public WorkoutHistory(String muscleName, List<Workout> workoutList, LocalDate date) {
        this.muscleName = muscleName;
        WorkoutList = workoutList;
        this.date = date;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMuscleName() {
        return muscleName;
    }
    public void setMuscleName(String muscleName) {
        this.muscleName = muscleName;
    }
    public List<Workout> getWorkoutList() {
        return WorkoutList;
    }
    public void setWorkoutList(List<Workout> workoutList) {
        WorkoutList = workoutList;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    
    
    
}
