package com.dogmeeting.userService.service;

import com.dogmeeting.userService.dto.UserDto;
import com.dogmeeting.userService.entity.UserEntity;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}
