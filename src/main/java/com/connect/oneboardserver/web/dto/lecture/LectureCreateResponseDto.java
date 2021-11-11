package com.connect.oneboardserver.web.dto.lecture;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LectureCreateResponseDto {

    Long lectureId;

    @Builder
    public LectureCreateResponseDto(Long lectureId) {
        this.lectureId = lectureId;
    }
}
