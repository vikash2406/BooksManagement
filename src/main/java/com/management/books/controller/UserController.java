package com.management.books.controller;

import com.management.books.exception.ResourceNotFoundException;
import com.management.books.model.Books;
import com.management.books.model.Users;
import com.management.books.service.UserService;
import com.management.books.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @PostMapping(path = "")
    public Users addUserDetail(@RequestBody Users data) {
        return userService.addUser(new Users(data.getUserName(), data.getPassword()));
    }

    @GetMapping(path = "/{id}")
    public Optional<Users> getUserDetail(@PathVariable(value = "id") String userId) {
        if(Objects.isNull(userId) || userId.isEmpty()){
            throw new ResourceNotFoundException(Constants.INVALID_ID_MSG);
        }
        return userService.getUser(userId);
    }
    @GetMapping(path = "")
    public List<Users> getAllUserDetail() {
        return userService.getUser();
    }
}
