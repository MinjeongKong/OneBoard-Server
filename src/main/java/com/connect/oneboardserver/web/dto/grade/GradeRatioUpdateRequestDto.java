package com.connect.oneboardserver.web.dto.grade;

import com.connect.oneboardserver.domain.grade.GradeRatio;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GradeRatioUpdateRequestDto {

    private Integer aRatio;
    private Integer bRatio;

    @Builder
    public GradeRatioUpdateRequestDto(Integer aRatio, Integer bRatio) {
        this.aRatio = aRatio;
        this.bRatio = bRatio;
    }

    public GradeRatio toEntity() {
        return GradeRatio.builder()
                .aRatio(aRatio)
                .bRatio(bRatio)
                .build();
    }
}
