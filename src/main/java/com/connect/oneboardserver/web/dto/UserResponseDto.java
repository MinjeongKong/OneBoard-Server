package com.connect.oneboardserver.web.dto;

import com.connect.oneboardserver.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private String studentNumber;
    private String email;
//    private String password;
    private String name;
    private String userType;    // S = 학생, T = 강의자
    private String university;
    private String major;

    public UserResponseDto(User entity) {
        this.studentNumber = entity.getStudentNumber();
        this.email = entity.getEmail();
        this.name = entity.getName();
        this.userType = entity.getUserType();
        this.university = entity.getUniversity();
        this.major = entity.getMajor();
    }
}
