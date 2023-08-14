package com.yallahnsafro.yallahnsafrobackend.controllers;

import com.yallahnsafro.yallahnsafrobackend.entities.ObjectMapperType;

import com.yallahnsafro.yallahnsafrobackend.security.SecurityConstants;
import com.yallahnsafro.yallahnsafrobackend.services.UserService;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.UserDto;
import com.yallahnsafro.yallahnsafrobackend.utils.UserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.*;


@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Value("${url}")
    private String url;


    @Value("${company.name}")
    private String company;


    @Value("${login.token.expiry_minutes}")
    private int restPassword_expiring_min;


    @Autowired
    UserService userService;

    ObjectMapperType objectMapper = new ObjectMapperType();



    @GetMapping("/{userId}")
    public String getUserById(@PathVariable String userId) throws Exception {
        System.out.println("used find by userId");
        UserDto userDto =userService.getUserByUserId(userId);
        System.out.println(userDto);

        return objectMapper.writerWithView(UserResponse.class).writeValueAsString(userDto);
    }

    @PreAuthorize("hasAuthority('" + SecurityConstants.USER_ROLE_ADMIN + "')")
    @GetMapping("/allUsers")
    public String  getAllUsers()throws Exception {
        List<UserDto> usersDtoAll = new ArrayList<>();
        usersDtoAll = userService.getAllUsers();
        System.out.println("use get all");
        StringJoiner users = new StringJoiner(",\n");
        for (UserDto userDto : usersDtoAll) {
            users.add(objectMapper.writerWithView(UserResponse.class)
                            .writeValueAsString(userDto));
        }
        return (users.toString());
    }

    @PostMapping(path = "/registration")
    public String registerUser(@RequestBody UserDto userDto) {
        UserDto newUser = userService.registerUser(userDto);

        // email verification

        return ("Account Created Successfully");
    }

    @PutMapping()
    public String updateUserById(@RequestBody UserDto userDtoUpdated) {
        UserDto updateUser = userService.updateUser(userDtoUpdated);
        return ("ok");
    }

    @PreAuthorize("hasAuthority('" + SecurityConstants.USER_ROLE_ADMIN + "')")
    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Object> deleteUserById(@PathVariable String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("verifyEmail")
    public boolean verifyEmail(@RequestParam String verificationToken){
        if (userService.verifyEmail(verificationToken))
            return true;
        return false;
    }

    @PostMapping("/forgotPassword")
    public boolean forgotPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        if (userService.forgotPassword(email))
                return true;

        return false;
    }

    @PostMapping("/resetPassword")
    public boolean resetPassword(@RequestBody Map<String, String> requestBody){

        String newPassword = requestBody.get("newPassword");
        String verificationToken = requestBody.get("verificationToken");
        if (userService.resetPassword(newPassword, verificationToken))
            return true;
        return false;
    }

    @PreAuthorize("hasAuthority('" + SecurityConstants.USER_ROLE_ADMIN + "')")
    @PostMapping("/disableUser")
    public boolean disableUser(@RequestBody Map<String, String> requestBody){
        String email = requestBody.get("email");
        if (userService.disableUser(email))
            return true;

        return false;
    }

    @PreAuthorize("hasAuthority('" + SecurityConstants.USER_ROLE_ADMIN + "')")
    @PostMapping("/enableUser")
    public boolean enableUser(@RequestBody Map<String, String> requestBody){
        String email = requestBody.get("email");
        if (userService.enableUser(email))
            return true;

        return false;
    }



}
