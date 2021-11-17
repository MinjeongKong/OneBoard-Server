package com.connect.oneboardserver.web.dto.grade;

import com.connect.oneboardserver.domain.grade.GradeRatio;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GradeRatioFindResponseDto {

    private Integer aratio;
    private Integer bratio;

    public GradeRatioFindResponseDto(GradeRatio entity) {
        this.aratio = entity.getAratio();
        this.bratio = entity.getBratio();
    }
}
