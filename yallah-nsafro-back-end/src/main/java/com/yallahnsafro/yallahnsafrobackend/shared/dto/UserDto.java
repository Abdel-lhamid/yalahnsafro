package com.yallahnsafro.yallahnsafrobackend.shared.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.yallahnsafro.yallahnsafrobackend.shared.UserRole;
import com.yallahnsafro.yallahnsafrobackend.utils.UserResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
public class UserDto implements Serializable {
    private static final long serialVersionUID = 6407688734661559517L;
    private long id;

    @JsonView(value = {UserResponse.class})
    private String userId;
    @JsonView(value = {UserResponse.class})
    private String firstname;
    @JsonView(value = {UserResponse.class})
    private String lastname;
    private String password;
    @JsonView(value = {UserResponse.class})
    private String email;
    @JsonView(value = {UserResponse.class})
    private String phonenumber;
    @JsonView(value = {UserResponse.class})
    private UserRole role;

    private Date created_at;

    private Date update_at;


    @JsonView(value = {UserResponse.class})
    private boolean locked;

    @JsonView(value = {UserResponse.class})
    private boolean enabled;



}
