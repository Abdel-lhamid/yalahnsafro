package com.yallahnsafro.yallahnsafrobackend.services;

import com.yallahnsafro.yallahnsafrobackend.shared.dto.UserDto;
import org.apache.catalina.User;

import java.util.Date;

public interface UserService {


    UserDto createUserDto(UserDto userDto);

    UserDto getUserById(Long userId);

    UserDto updateUserById(Long userId, UserDto userDto);
    void deleteUser(Long userId);
}
