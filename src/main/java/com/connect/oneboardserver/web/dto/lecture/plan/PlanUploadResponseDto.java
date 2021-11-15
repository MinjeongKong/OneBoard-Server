package com.connect.oneboardserver.web.dto.lecture.plan;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlanUploadResponseDto {

    private Long lectureId;

    @Builder
    public PlanUploadResponseDto(Long lectureId) {
        this.lectureId = lectureId;
    }
}
