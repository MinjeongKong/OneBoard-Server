package com.connect.oneboardserver.web.dto.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

//@JsonInclude(JsonInclude.Include.ALWAYS)
@Getter
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
