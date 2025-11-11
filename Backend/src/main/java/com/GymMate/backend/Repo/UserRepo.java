package com.GymMate.backend.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GymMate.backend.Database.UserDetail;



public interface UserRepo extends JpaRepository<UserDetail, Integer> {
    UserDetail findByEmail(String email);
}
