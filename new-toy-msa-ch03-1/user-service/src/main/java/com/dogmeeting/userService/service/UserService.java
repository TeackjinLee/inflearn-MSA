package com.dogmeeting.userService.service;

import com.dogmeeting.userService.dto.UserDto;
import com.dogmeeting.userService.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}
