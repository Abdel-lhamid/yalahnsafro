package com.yallahnsafro.yallahnsafrobackend.services.implimentation;
import com.yallahnsafro.yallahnsafrobackend.entities.UserEntity;
import com.yallahnsafro.yallahnsafrobackend.repositories.UserRepository;
import com.yallahnsafro.yallahnsafrobackend.services.UserService;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUserDto(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto,userEntity);
        UserEntity newUserEntity = userRepository.save(userEntity);
        UserDto newUserDto = new UserDto();
        BeanUtils.copyProperties(newUserEntity, newUserDto);
        return (newUserDto);
    }

    @Override
    public UserDto getUserById(Long userId) {
        return null;
    }

    @Override
    public UserDto updateUserById(Long userId, UserDto userDtoUpdated) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }


}
