package com.GymMate.backend.Repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.GymMate.backend.Database.Muscles;

public interface MuscelsRepo extends JpaRepository<Muscles, String> {
    Muscles findByMuscleName(String muscleName);
} 
