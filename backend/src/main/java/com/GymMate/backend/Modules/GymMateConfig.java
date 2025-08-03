/* 
package com.GymMate.backend.Modules;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.GymMate.backend.Resources.Muscles;
import com.GymMate.backend.Resources.User;


@Configuration
public class GymMateConfig {

    List<String> chestWorkout = List.of("Push up","Flat Bench Press","Flat machine press", "Flat Dumbbell Press", "Incline Bench Press","Inclined machine press","Incline Dumble Press", "Decline bench press","Decline Dumble press", "Chest dips", "Flying machine", "Dumble Fly", "cabel Fly");
    List<String> backWorkout = List.of("Pull ups","Bent rowen","Arm dumbell Row","Seated Cabel row","Straight arm pulldown","DeadLift","Wide grip lat Pull down","Close Grip lat pull down","T rod row");
    List<String> shoulderWorkout = List.of("Dumbbell sholder press","Arnold press", "Smith machine press", "barbell sholder press","Front rise", "Side dumble rise","Side cabel rise","Face cabel pull","Rear delt fly","Rear delt machine fly","Cabel upright row","Dumble shrug");
    List<String> legWorkout= List.of("Leg press","Squats","Barbel Back squats","Barbel front squats","Leg extension","Lying hamstring curls","sumo squarts","Goblet squats","Walking lunge","Calf rise","Calf machine","Calf smith machine");
    List<String> bicepWorkout=List.of("Hamer curl","Dumbel curl","EZ-bar curl","Cabel curl","barbel curl","Concentration curls","Machine curl");
    List<String> TricepWorkout=List.of("Overhead trice extension","Skull crush","Tricep cabel pushDown","single hande cabel trice push","dips","single arm overhed trice extension","close crip bench press","EZ-bar skull crush");
    List<String> cardioList=List.of("Tread mill","Running","Cycling","Swmming","Steps");

    @Bean
    CommandLineRunner commandLineRunner(GymMateRepo gymMateRepo, workoutHistoryRepo workoutHistoryRepo, userRepo Repo) {
        return args -> {
            // Insert muscles
            Muscles m1 = new Muscles("Chest", chestWorkout, LocalDate.of(2025, Month.JUNE, 11));
            Muscles m2 = new Muscles("Lats", backWorkout, LocalDate.of(2025, Month.JUNE, 11));
            Muscles m3 = new Muscles("Shoulder", shoulderWorkout, LocalDate.of(2025, Month.JUNE, 11));
            Muscles m4 = new Muscles("Leg", legWorkout, LocalDate.of(2025, Month.JUNE, 11));
            Muscles m5 = new Muscles("Bicep", bicepWorkout, LocalDate.of(2025, Month.JUNE, 11));
            Muscles m6 = new Muscles("Tricep", TricepWorkout, LocalDate.of(2025, Month.JUNE, 11));
            Muscles m7=new Muscles("Cardio",cardioList,LocalDate.of(2025, Month.JUNE, 11));

            gymMateRepo.saveAll(List.of(m1, m2, m3, m4, m5, m6,m7));

            User u1=new User("ranjithn2706@gmail.com","ranGym27");
            User u2=new User("ran@gmail.com","ranGym27");
            Repo.saveAll(List.of(u1,u2));
        };
    }
}
*/
