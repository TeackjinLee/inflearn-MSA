package com.dogmeeting.userservice.controller;

import com.dogmeeting.userservice.vo.GreetingVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@AllArgsConstructor
@Slf4j
public class UserController {

    private Environment env;
    private GreetingVo greetingVo;

//    @Autowired
//    public UserController(Environment env, GreetingVo greetingVo) {
//        this.env = env;
//        this.greetingVo = greetingVo;
//    }

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


}
