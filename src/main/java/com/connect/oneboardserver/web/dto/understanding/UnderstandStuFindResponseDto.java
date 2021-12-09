package com.connect.oneboardserver.web.dto.understanding;

import com.connect.oneboardserver.domain.understanding.UnderstandStu;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnderstandStuFindResponseDto {

    private Long studentId;
    private String studentName;
    private String studentNumber;
    private Integer response;

    @Builder
    public UnderstandStuFindResponseDto(UnderstandStu entity) {
        this.studentId = entity.getStudent().getId();
        this.studentName = entity.getStudent().getName();
        this.studentNumber = entity.getStudent().getStudentNumber();
        this.response = entity.getResponse();
    }

}
