package com.GymMate.backend.Modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GymMate.backend.Resources.User;
import com.GymMate.backend.Resources.Workout;

import java.util.List;

@RestController
@RequestMapping("/api/ranjith/")
@CrossOrigin(origins = "http://localhost:5173") 
public class GymMateControl {
    
    @Autowired
    GymMateService gymMateService;

    @GetMapping("muscle/{muscleName}")
    public List<String> getWorkoutList(@PathVariable("muscleName") String muscleName){
        return gymMateService.getList(muscleName);
    }

    @GetMapping("muscle/{muscleName}/last-date")
    public String getWorkoutLastTime(@PathVariable String muscleName){
        return gymMateService.getLastTime(muscleName);
    }
    
    @GetMapping("muscleName/{muscleName}/{lastDate}/{workoutName}")
    public List<String> getLastTimeProgress(@PathVariable String muscleName, @PathVariable String lastDate, @PathVariable String workoutName){
        return gymMateService.getLastTimeProgress(muscleName, lastDate, workoutName);
    }
    
    @PostMapping("muscleName/{muscleName}/date/{date}")
    public void addWorkout(@PathVariable String muscleName, @PathVariable String date, @RequestBody Workout workout){
        gymMateService.addworkoutservice(muscleName, date, workout);
        gymMateService.updateLastDate(muscleName,date);
    }

    @PostMapping("user/login")
    public String verify(@RequestBody User user){
        boolean verified=gymMateService.VerifyService(user.getEmail(),user.getPassword());
        if(verified){
            return "true";
        }
        return "false";
    }
}