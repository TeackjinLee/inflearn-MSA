package com.dogmeeting.userService.security;

import com.dogmeeting.userService.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {
    private UserService userService;
    private Environment env;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final String ALLOWED_IP_ADDRESS = "127.0.0.1";
    public static final String SUBNET = "/32";
    public static final IpAddressMatcher ALLOWED_IP_ADDRESS_MATCHER = new IpAddressMatcher(ALLOWED_IP_ADDRESS + SUBNET);

//    public WebSecurity(UserService userService, Environment environment, BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.userService = userService;
//        this.environment = environment;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http, UserService userService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder); // userService에 에러가남. 이유는 UserService에 UserDetailsService의존성을 추가해줘야 한다.

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.csrf((csrf) -> {
            try {
                csrf.disable()
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/**").access(
                                        new WebExpressionAuthorizationManager(
                                                "hasIpAddress('102.0.0.1') or hasIpAddress('::1') or " +
                                                        "hasIpAddress('10.18.141.153') or hasIpAddress('::1')")) // host pc ip address
                                .anyRequest().authenticated()
                        )
                        .authenticationManager(authenticationManager)
                        .addFilter(getAuthenticationFilter(authenticationManager))
                        .httpBasic(Customizer.withDefaults()) // <- Basic 인증 추가
                        .headers((headers) -> headers
                                .frameOptions((frameOptions) -> frameOptions.sameOrigin()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return http.build();
    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager);
        return authenticationFilter;
    }
}
