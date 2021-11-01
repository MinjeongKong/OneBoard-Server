package com.connect.oneboardserver.web.dto.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@JsonInclude(JsonInclude.Include.ALWAYS)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

    private String studentNumber;
    private String name;
    private String user_type;
    private String email;
    private String university;
    private String major;
    private String lecture_id;

}
