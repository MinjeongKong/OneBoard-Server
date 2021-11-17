package com.connect.oneboardserver.web.dto.grade;

import com.connect.oneboardserver.domain.grade.GradeRatio;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class GradeRatioUpdateRequestDto {

    @NotNull
    private Integer aratio;
    @NotNull
    private Integer bratio;

    @Builder
    public GradeRatioUpdateRequestDto(Integer aratio, Integer bratio) {
        this.aratio = aratio;
        this.bratio = bratio;
    }

    public GradeRatio toEntity() {
        return GradeRatio.builder()
                .aratio(aratio)
                .bratio(bratio)
                .build();
    }
}
