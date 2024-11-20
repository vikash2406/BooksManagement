package com.management.books.service;

import com.management.books.model.Users;
import com.management.books.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users addUser(Users user){
        if (user.getId() == null) {
            user.setId(UUID.randomUUID().toString());
        }
        userRepository.save(user);
        return user;
    }
    public Optional<Users> getUser(String id){
        return userRepository.findById(id);
    }

    public List<Users> getUser(){
        return userRepository.findAll();
    }
}
