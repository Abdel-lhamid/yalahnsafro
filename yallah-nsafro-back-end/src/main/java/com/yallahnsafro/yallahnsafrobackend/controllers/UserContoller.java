package com.yallahnsafro.yallahnsafrobackend.controllers;

import com.yallahnsafro.yallahnsafrobackend.shared.dto.UserDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserContoller {
    @GetMapping
    public String getUser(){
        return "fuck you anass";
    }
    @PostMapping
    public String createUser(@RequestBody UserDto userDto){
        return userDto.getEmail();
    }


}
