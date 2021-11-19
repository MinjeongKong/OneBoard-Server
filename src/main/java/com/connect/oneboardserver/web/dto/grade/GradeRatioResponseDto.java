package com.connect.oneboardserver.web.dto.grade;

import com.connect.oneboardserver.domain.grade.GradeRatio;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GradeRatioResponseDto {

    private Long gradeRatioId;

    public GradeRatioResponseDto(GradeRatio entity) {
        this.gradeRatioId = entity.getId();
    }
}
