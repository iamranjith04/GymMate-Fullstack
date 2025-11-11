package com.GymMate.backend.Services;



import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.GymMate.backend.Database.Muscles;
import com.GymMate.backend.Database.UserDetail;
import com.GymMate.backend.Database.Workout;
import com.GymMate.backend.Database.WorkoutDetails;
import com.GymMate.backend.Repo.MuscelsRepo;
import com.GymMate.backend.Repo.UserRepo;




@Service
public class GymMateService {
    @Autowired
    private MuscelsRepo muscelsRepo;

    @Autowired
    private UserRepo userRepo;

    public  List<String> returnWorkouts(String muscleName){
        Muscles muscles= muscelsRepo.findByMuscleName(muscleName);
         if (muscles == null) {
            throw new RuntimeException("Muscle not found: " + muscleName);
        }
        return muscles.getWorkouts();
    }

    public boolean CheckExistingAndSave( String email, LocalDate date, String muscleName, WorkoutDetails workoutDetails) {
        
        UserDetail user = userRepo.findByEmail(email);
        List<Workout> workoutHistory = user.getWorkoutHistory();
        for (Workout workout : workoutHistory) {
            if(workout.getDate().equals(date) && workout.getMuscleName().equals(muscleName)) {
                workout.getWorkoutDetails().add(workoutDetails);
                userRepo.save(user);
                return true;
            }
        }
        Workout newWorkout = new Workout(date, muscleName, List.of(workoutDetails));
        workoutHistory.add(newWorkout);
        user.setWorkoutHistory(workoutHistory);
        userRepo.save(user);
        return true;
    }

    public List<WorkoutDetails> getWorkout(String email, String date, String muscleName) {
        UserDetail user = userRepo.findByEmail(email);
        List<Workout> workoutHistory = user.getWorkoutHistory();
        LocalDate localDate = LocalDate.parse(date);
        for (Workout workout : workoutHistory) {
            if(workout.getDate().equals(localDate) && workout.getMuscleName().equals(muscleName)) {
                return workout.getWorkoutDetails();
            }
        }
        return null;
    }

    public String getLastWorkoutDate(String email, String muscleName){
        UserDetail user=userRepo.findByEmail(email);
        List<Workout> workoutHistory=user.getWorkoutHistory();
        LocalDate lastDate=null;
        for(Workout workout:workoutHistory){
            if(workout.getMuscleName().equals(muscleName)){
                if(lastDate==null || workout.getDate().isAfter(lastDate)){
                    lastDate=workout.getDate();
                }
            }
        }
        if(lastDate!=null){
            return lastDate.toString();
        }
        return "No previous workouts found for the specified muscle group.";
    }

    public UserDetail getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

}
