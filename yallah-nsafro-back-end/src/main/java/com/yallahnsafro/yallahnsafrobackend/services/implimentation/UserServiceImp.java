package com.yallahnsafro.yallahnsafrobackend.services.implimentation;
import com.yallahnsafro.yallahnsafrobackend.entities.UserEntity;
import com.yallahnsafro.yallahnsafrobackend.repositories.UserRepository;
import com.yallahnsafro.yallahnsafrobackend.services.UserService;
import com.yallahnsafro.yallahnsafrobackend.shared.Utils;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImp implements UserService, UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(), userEntity.getPassword(),new ArrayList<>());
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils util;


    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDto createUser(UserDto userDto) {
        //check if user data exists by email
        UserEntity checkUser = userRepository.findByEmail(userDto.getEmail());
        if (checkUser != null){
            throw new RuntimeException("email already exists");
        }

        //Generate userID
        userDto.setUserId(util.generateUserId(32));

        //Encrypt password
        String encryptedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);
        //end of encryption


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
    public void deleteUser(String userId) {
        UserEntity userFound = userRepository.findByUserId(userId);

        if (userFound!= null){
            userRepository.delete(userFound);
        } else throw new UsernameNotFoundException(userId);
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

    @Override
    public UserDto getUserByUserId(String UserId) {
        return null;
    }

    public UserDto getUserForLogin(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null)
            throw new UsernameNotFoundException(email);

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity,userDto);

        return userDto;
    }



}
