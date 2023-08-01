package com.yallahnsafro.yallahnsafrobackend.services.implimentation;
import com.yallahnsafro.yallahnsafrobackend.entities.UserEntity;
import com.yallahnsafro.yallahnsafrobackend.repositories.UserRepository;
import com.yallahnsafro.yallahnsafrobackend.services.UserService;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        //check if user data exists by email
        UserEntity checkUser = userRepository.findByEmail(userDto.getEmail());
        if (checkUser != null){
            throw new RuntimeException("email already exists");
        }

        //Encrypte password


        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto,userEntity);

        UserEntity newUserEntity = userRepository.save(userEntity);
        UserDto newUserDto = new UserDto();
        BeanUtils.copyProperties(newUserEntity, newUserDto);
        return (newUserDto);
    }

    @Override
    public UserDto getUserById(long userId) throws UsernameNotFoundException {
            UserEntity foundUserEntity = new UserEntity();
            foundUserEntity = userRepository.findById(userId).orElse(null);
            UserDto foundUserDto = new UserDto();
            if (foundUserEntity == null) {
                throw new UsernameNotFoundException ("notfound");
            }
            BeanUtils.copyProperties(foundUserEntity, foundUserDto);
            return foundUserDto;
    }

    @Override
    public UserDto updateUser(UserDto userDtoUpdated) {
        UserDto userDtoAfterUpdate = new UserDto();
        UserEntity userEntityUpdate = new UserEntity();
        UserEntity userEntityAfterUpdate = new UserEntity();

        userEntityUpdate = userRepository.findById(userDtoUpdated.getId()).orElse(null);
        long id = userDtoUpdated.getId();
        if (userEntityUpdate != null) {
            BeanUtils.copyProperties(userDtoUpdated, userEntityUpdate);
            userEntityAfterUpdate = userEntityUpdate;
            BeanUtils.copyProperties(userEntityAfterUpdate, userDtoAfterUpdate);
            return (userDtoAfterUpdate);
        }

        return null;
    }

    @Override
    public void deleteUser(UserDto userDtoToDelete) {
        UserEntity userEntityToDelete = new UserEntity();
        BeanUtils.copyProperties(userEntityToDelete, userDtoToDelete);
        userRepository.delete(userEntityToDelete);
    }

    @Override
    public List<UserDto> getAllUsers() {
        UserDto temp = new UserDto();
        List<UserEntity> allUsersEntity = (List<UserEntity>) userRepository.findAll();
        List<UserDto> allUsersDto = new ArrayList<>();
        for (UserEntity userEntity : allUsersEntity){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            allUsersDto.add(userDto);
        }
        return (allUsersDto);
    }


}
