package com.dogmeeting.userService.controller;

import com.dogmeeting.userService.dto.UserDto;
import com.dogmeeting.userService.service.UserService;
import com.dogmeeting.userService.vo.RequestUser;
import com.dogmeeting.userService.vo.GreetingVo;
import com.dogmeeting.userService.vo.ResponseUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j
public class UserController {

    private Environment env;
    private UserService userService;

    @Autowired
    private GreetingVo greetingVo;

    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/health-check")
    public String status(){
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port"));
    }

    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request){
        log.info("users.welcome ip: {}, {}, {}, {}"
                , request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), request.getRequestURL());
        //return env.getProperty("greeting.message"); // application.yml내용.
        return  greetingVo.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody RequestUser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
}
