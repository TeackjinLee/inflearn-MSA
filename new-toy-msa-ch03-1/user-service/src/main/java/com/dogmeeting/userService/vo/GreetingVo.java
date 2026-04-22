package com.dogmeeting.userService.vo;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class GreetingVo {
    @Value("${greeting.message}")
    private String message;
}
