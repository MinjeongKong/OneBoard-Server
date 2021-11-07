package com.connect.oneboardserver.web.dto.login;

import com.connect.oneboardserver.domain.login.Member;
import lombok.*;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private String studentNumber;
    private String name;
    private String userType;
    private String email;
    private String university;
    private String major;

    public MemberResponseDto(Member entity) {
        this.studentNumber = entity.getStudentNumber();
        this.name = entity.getName();
        this.userType = entity.getUserType();
        this.email = entity.getEmail();
        this.university = entity.getUniversity();
        this.major = entity.getMajor();
    }



}
