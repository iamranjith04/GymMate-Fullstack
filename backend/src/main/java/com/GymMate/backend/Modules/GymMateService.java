package com.GymMate.backend.Modules;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.GymMate.backend.Resources.Muscles;
import com.GymMate.backend.Resources.User;
import com.GymMate.backend.Resources.Workout;
import com.GymMate.backend.Resources.WorkoutHistory;

@Service
public class GymMateService {
    
    @Autowired
    private GymMateRepo gymMateRepo; 

    @Autowired
    private workoutHistoryRepo workoutHistoryRepo;

    @Autowired
    private userRepo Repo;

    public List<String> getList(String muscleName) {
        return gymMateRepo.findByMuscleName(muscleName)
                        .map(Muscles::getWorkout)
                        .orElse(List.of());
    }


    public String getLastTime(String muscleName) {
    Optional<Muscles> optionalMuscle = gymMateRepo.findByMuscleName(muscleName);
            if (optionalMuscle.isPresent()) {
                Muscles muscle = optionalMuscle.get();
                LocalDate date = muscle.getLastWorkoutDate();
                return (date != null) ? date.toString() : "No workout date recorded";
            } else {
                return "Muscle not found";
            }
    }

    public List<String> getLastTimeProgress(String muscleName, String lastDate, String workoutName) {
        LocalDate date = LocalDate.parse(lastDate);
        return  workoutHistoryRepo.findWorkoutAndDate(muscleName, date,workoutName);
    
}

   public void addworkoutservice(String muscleName, String dateStr, Workout workout) {
        LocalDate date = LocalDate.parse(dateStr);    

        Optional<WorkoutHistory> existing = workoutHistoryRepo.findByMuscleNameAndDate(muscleName, date);

        if (existing.isPresent()) {
            WorkoutHistory history = existing.get();
            List<Workout> currentWorkouts = history.getWorkoutList();
            currentWorkouts.add(workout);
            history.setWorkoutList(currentWorkouts);
            workoutHistoryRepo.save(history);
        } else {
            List<Workout> newWorkoutList = new ArrayList<>();
            newWorkoutList.add(workout);
            WorkoutHistory newHistory = new WorkoutHistory(muscleName, newWorkoutList, date);
            workoutHistoryRepo.save(newHistory);
        }
       Optional<Muscles> optionalMuscle = gymMateRepo.findByMuscleName(muscleName);
        if (optionalMuscle.isPresent()) {
            Muscles muscle = optionalMuscle.get();
            muscle.setLastWorkoutDate(date);
            gymMateRepo.save(muscle); 
        }
    }

    public void updateLastDate(String muscleName,String ldate){
        LocalDate date = LocalDate.parse(ldate); 
        gymMateRepo.updateLastDate(muscleName,date);
    }

    public boolean VerifyService(String userMail,String Password){
        Optional<User> exist=Repo.findByEmail(userMail);
        if(exist.isPresent()){
            User user=exist.get();
            if(user.getPassword().equals(Password)){
                return true;
            }
        }
        return false;
    }


}
