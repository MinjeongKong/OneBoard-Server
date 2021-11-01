package com.connect.oneboardserver.web.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private String studentNumber;
    private String name;
    private String password;
    private String user_type;
    private String email;
    private String university;
    private String major;
    private String lecture_id;

}
