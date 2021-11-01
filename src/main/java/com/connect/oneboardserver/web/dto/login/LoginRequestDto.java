package com.connect.oneboardserver.web.dto.login;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequestDto {

    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;

}
