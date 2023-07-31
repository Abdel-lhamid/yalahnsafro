package com.yallahnsafro.yallahnsafrobackend.controllers;

import com.yallahnsafro.yallahnsafrobackend.services.UserService;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserContoller {
    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    public String getUserById(Long userId) {
        UserDto userDto = userService.getUserById(userId);

        return ("ok");
    }

    @GetMapping()
    public String getAllUsers() {
        return ("*** ALL USERS **** ");
    }

    @PostMapping
    public String createUser(@RequestBody UserDto userDto) {
        UserDto newUser = userService.createUserDto(userDto);
        return (newUser.getEmail() + newUser.getId());
    }

    @PutMapping
    public String updateUserById(@RequestParam Long userId, @RequestBody UserDto userDtoUpdated) {
        UserDto updateUser = userService.updateUserById(userId, userDtoUpdated);
        return ("ok");
    }

    @DeleteMapping
    public void deleteUserById(Long userId) {
        userService.deleteUser(userId);
    }


}
