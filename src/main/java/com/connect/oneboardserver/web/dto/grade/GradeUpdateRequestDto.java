package com.connect.oneboardserver.web.dto.grade;

import com.connect.oneboardserver.domain.grade.Grade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GradeUpdateRequestDto {

    private String result;

    @Builder
    public GradeUpdateRequestDto(String result) {
        this.result = result;
    }
}
