package com.dogmeeting.userService.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
public class RequestUser {
    @NotNull(message = "Email cannot be null")
    @Size( min = 2, message = "Email not be less than two characters")
    @Email
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size( min = 8, message = "Password not be less than two characters")
    @Email
    private String pwd;

    @NotNull(message = "Name cannot be null")
    @Size( min = 2, message = "Name not be less than two characters")
    @Email
    private String name;
}
