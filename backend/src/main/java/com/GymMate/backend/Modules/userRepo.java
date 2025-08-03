package com.GymMate.backend.Modules;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.GymMate.backend.Resources.User;
import java.util.Optional;



@Repository
public interface userRepo extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
