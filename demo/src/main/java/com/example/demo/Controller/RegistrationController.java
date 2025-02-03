package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.MyAppUser;
import com.example.demo.Model.MyAppUserRepository;

@RestController
public class RegistrationController {

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/req/signup", consumes = "application/json")
    public MyAppUser createUser(@RequestBody MyAppUser user){
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Handle photo path if it's part of the registration process
        // user.setPhotoPath("path/to/photo"); // Set photo path (you can also get it dynamically if uploaded)

        return myAppUserRepository.save(user); // Save user
    }
}
