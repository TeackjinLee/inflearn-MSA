package com.dogmeeting.userservice.service;

import com.dogmeeting.userservice.dto.UserDto;
import com.dogmeeting.userservice.jpa.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();

    UserDto getUserDetailsByEmail(String userEmail);
}
