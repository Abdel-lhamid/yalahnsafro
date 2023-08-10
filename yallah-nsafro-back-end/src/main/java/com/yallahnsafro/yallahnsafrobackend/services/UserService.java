package com.yallahnsafro.yallahnsafrobackend.services;

import com.yallahnsafro.yallahnsafrobackend.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {


    UserDto registerUser(UserDto userDto);

    UserDto getUserById(long userId);

    UserDto updateUser(UserDto userDto);
    void deleteUser(String userId);
    List<UserDto> getAllUsers();

    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    UserDto getUserByUserId(String UserId);

    UserDto getUserForLogin(String email);



}
