package com.GymMate.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GymMate.backend.Database.UserDetail;
import com.GymMate.backend.Repo.UserRepo;
import com.GymMate.backend.Securitys.Component.JwtUtil;

@Service
public class LoginService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    public String addUser(String email, String name, int age, String password){
        UserDetail userExisit=userRepo.findByEmail(email);
        if(userExisit!=null){
            return "User already exists";
        }
        UserDetail newUser=new UserDetail();
        newUser.setEmail(email);    
        newUser.setName(name);  
        newUser.setAge(age);
        newUser.setPassword(password);
        userRepo.save(newUser);
        return jwtUtil.generateToken(email);
    } 

    public String validateUser(String email, String password){
        UserDetail user=userRepo.findByEmail(email);
        if(user==null){
            return "User does not exist";
        }
        if (user.getPassword() != null && user.getPassword().equals(password)) {
            return jwtUtil.generateToken(email);
        }else{
            return "Invalid password";
        }
    }
}
