package com.connect.oneboardserver.web.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinDto {

    @NotEmpty
    private String studentNumber;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
    @NotEmpty
    private String userType;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String university;
    @NotEmpty
    private String major;
}
