package com.GymMate.backend.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GymMate.backend.RequestBody.LoginBody;
import com.GymMate.backend.Services.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "${Frontend_URL}") 
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/newLogin")
    public ResponseEntity<?> postMethodName(@RequestBody LoginBody body) {
        String response=loginService.addUser(body.email, body.name, body.age, body.password);
        if(!response.equals("User already exists")){
            return ResponseEntity.ok().body(response);
        }
        else{
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/existingLogin")
    public ResponseEntity<?>  existingLogin(@RequestBody LoginBody body) {
        String email=body.email;
        String password=body.password;
        System.out.println(password);
        String response=loginService.validateUser(email, password);
        if(response.equals("User does not exist") || response.equals("Invalid password")){
            return ResponseEntity.badRequest().body(response);
        }
        else{
            return ResponseEntity.ok().body(response);
        }
    }
    
}
