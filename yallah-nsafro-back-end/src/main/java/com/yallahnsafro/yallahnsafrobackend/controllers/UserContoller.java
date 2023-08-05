package com.yallahnsafro.yallahnsafrobackend.controllers;

import com.yallahnsafro.yallahnsafrobackend.services.UserService;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserContoller {
    @Autowired
    UserService userService;



    @GetMapping("/users/{userId}")
    public UserDto getUserById(@PathVariable long userId) {
        System.out.println("used find by ID");
        return userService.getUserById(userId);
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        List<UserDto> usersDtoAll = new ArrayList<>();
        usersDtoAll = userService.getAllUsers();
        System.out.println("use get all");
        return (usersDtoAll);
    }

    @PostMapping(path = "/users")
    public String createUser(@RequestBody UserDto userDto) {
        UserDto newUser = userService.createUser(userDto);
        return (newUser.getEmail() + newUser.getId());
    }

    @PutMapping(path = "/users")
    public String updateUserById(@RequestBody UserDto userDtoUpdated) {
        UserDto updateUser = userService.updateUser(userDtoUpdated);
        return ("ok");
    }

    @DeleteMapping(path = "/users/{userId}")
    public ResponseEntity<Object> deleteUserById(@PathVariable String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
