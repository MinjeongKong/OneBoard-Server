package com.connect.oneboardserver.web.dto.login;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;

}
