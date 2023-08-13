package com.yallahnsafro.yallahnsafrobackend.controllers;

import com.yallahnsafro.yallahnsafrobackend.entities.ObjectMapperType;
import com.yallahnsafro.yallahnsafrobackend.exceptions.UserException;
import com.yallahnsafro.yallahnsafrobackend.responses.ErrorMessages;
import com.yallahnsafro.yallahnsafrobackend.security.SecurityConstants;
import com.yallahnsafro.yallahnsafrobackend.services.UserService;
import com.yallahnsafro.yallahnsafrobackend.shared.dto.UserDto;
import com.yallahnsafro.yallahnsafrobackend.utils.UserResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public boolean forgotPassword(@RequestParam String email, HttpServletResponse response) throws MessagingException{
        try {
            UserDto user = userService.getUserForLogin(email);
            if (user != null){
                String jwtToken = Jwts.builder()
                        .setExpiration(DateUtils.addMinutes(new Date(), restPassword_expiring_min))
                        .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
                        .claim("email", email)
                        .compact();
                return true;
            } else {
            throw new UserException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
        }catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new UserException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

    }


}
