package com.yallahnsafro.yallahnsafrobackend.controllers;

import com.yallahnsafro.yallahnsafrobackend.entities.ObjectMapperType;
import com.yallahnsafro.yallahnsafrobackend.security.SecurityConstants;
import com.yallahnsafro.yallahnsafrobackend.services.UserService;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.UserDto;
import com.yallahnsafro.yallahnsafrobackend.utils.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@RestController
@RequestMapping(value = "/users")
public class UserContoller {
    @Autowired
    UserService userService;

    ObjectMapperType objectMapper;

    {
        objectMapper = new ObjectMapperType();
    }


    @GetMapping("/{userId}")
    public String getUserById(@PathVariable String userId) throws Exception {
        System.out.println("used find by userId");
        UserDto userDto =userService.getUserByUserId(userId);
        System.out.println(userDto);

        return objectMapper.writerWithView(UserResponse.class).writeValueAsString(userDto);
    }

    @PreAuthorize("hasAuthority('" + SecurityConstants.USER_ROLE_ADMIN + "')")
    @GetMapping()
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


}
