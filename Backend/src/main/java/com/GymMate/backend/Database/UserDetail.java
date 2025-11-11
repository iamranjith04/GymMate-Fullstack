package com.GymMate.backend.Database;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class UserDetail implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String email;
    private String name;
    private int age;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Workout> workoutHistory;
    public UserDetail() {
    }
    
    public UserDetail(int id, String email, String name, int age, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.age = age;
        this.password = password;
    }

    public UserDetail(int id, String email, String name, int age, String password, List<Workout> workoutHistory) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.age = age;
        this.password = password;
        this.workoutHistory = workoutHistory;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public List<Workout> getWorkoutHistory() {
        return workoutHistory;
    }
    public void setWorkoutHistory(List<Workout> workoutHistory) {
        this.workoutHistory = workoutHistory;
    }
    public void addWorkout(Workout workout) {
        this.workoutHistory.add(workout);
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    

}
