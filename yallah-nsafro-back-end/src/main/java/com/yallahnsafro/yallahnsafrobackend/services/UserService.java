package com.yallahnsafro.yallahnsafrobackend.services;

import com.yallahnsafro.yallahnsafrobackend.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService extends UserDetailsService {


    UserDto registerUser(UserDto userDto);

    UserDto getUserById(long userId);

    UserDto updateUser(UserDto userDto);
    boolean deleteUser(String userId);
    List<UserDto> getAllUsers(int page, int limit);

    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    UserDto getUserByUserId(String UserId);

    UserDto getUserForLogin(String email);

    boolean verifyEmail(String verificationToken);

    boolean forgotPassword(String email);

    boolean resetPassword(String newPassword, String verificationToken);

    boolean disableUser(String email);
    boolean enableUser(String email);


}
