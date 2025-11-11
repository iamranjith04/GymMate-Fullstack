package com.GymMate.backend.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GymMate.backend.Database.WorkoutDetails;
import com.GymMate.backend.RequestBody.RequestWorkout;
import com.GymMate.backend.RequestBody.SaveWorkout;
import com.GymMate.backend.Services.GymMateService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/User/")
@CrossOrigin(origins = "http://localhost:5173")
public class GymMateControl {
    
    @Autowired
    private GymMateService gymMateService;
    
    private Authentication gymAuthenticator() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth;
    }

    @PostMapping("/ListWorkout")
    public ResponseEntity<?> getWorkoutList(@RequestBody Map<String, String> body) {
        Authentication auth = gymAuthenticator();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized user"));
        }

        String muscleName = body.get("muscleName");
        List<String> workouts = gymMateService.returnWorkouts(muscleName);

        return ResponseEntity.ok(workouts);
    }

    @PostMapping("/addWorkout")
    public ResponseEntity<?> addWorkout(@RequestBody SaveWorkout body) {
        Authentication auth = gymAuthenticator();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized user"));
        }
        try {
            WorkoutDetails workoutToSave = new WorkoutDetails();
            workoutToSave.setWorkoutName(body.workoutName);
            workoutToSave.setSet1Weight(body.set1Weight);
            workoutToSave.setSet1Reps(body.set1Reps);
            workoutToSave.setSet2Weight(body.set2Weight);
            workoutToSave.setSet2Reps(body.set2Reps);
            workoutToSave.setSet3Weight(body.set3Weight);
            workoutToSave.setSet3Reps(body.set3Reps);

            boolean result = gymMateService.CheckExistingAndSave(
                    body.email, body.date, body.muscleName, workoutToSave);

            if (result) {
                return ResponseEntity.ok(Map.of("message", "Saved Successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Error in Saving"));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Server Error", "details", e.getMessage()));
        }
    }

    @PostMapping("/getWorkouts")
    public ResponseEntity<?> getWorkout(@RequestBody RequestWorkout body) {
        Authentication auth = gymAuthenticator();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized user"));
        }
        List<WorkoutDetails> workoutDetails = gymMateService.getWorkout(
                body.email, body.date, body.muscleName);

        if (workoutDetails == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(Map.of("message", "No workouts found"));
        }

        return ResponseEntity.ok(workoutDetails);
    }

    @PostMapping("/getLastWorkoutDate")
    public ResponseEntity<?> getLastWorkoutDate(@RequestBody Map<String, String> body) {
        Authentication auth = gymAuthenticator();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized user"));
        }
        String lastDate = gymMateService.getLastWorkoutDate(
                body.get("email"), body.get("muscleName"));

        if (lastDate == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(Map.of("message", "No last workout found"));
        }

        return ResponseEntity.ok(Map.of("lastWorkoutDate", lastDate));
    }
}
