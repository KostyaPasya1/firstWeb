package com.example.servingwebcontent.service;


import com.example.servingwebcontent.domain.User;
import com.example.servingwebcontent.repos.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;

    }


    public User findByUsername(String username) {

        return userRepo.findByUsername(username);
    }
    public User saveUser (User user) {
        return userRepo.save(user);

    }




}
