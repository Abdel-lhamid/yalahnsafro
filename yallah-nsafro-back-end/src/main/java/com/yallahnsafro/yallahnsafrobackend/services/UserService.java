package com.yallahnsafro.yallahnsafrobackend.services;

import com.yallahnsafro.yallahnsafrobackend.shared.dto.UserDto;
import java.util.List;

public interface UserService {


    UserDto createUser(UserDto userDto);

    UserDto getUserById(long userId);

    UserDto updateUser(UserDto userDto);
    void deleteUser(UserDto userDtoToDelete);
    List<UserDto> getAllUsers();


}
