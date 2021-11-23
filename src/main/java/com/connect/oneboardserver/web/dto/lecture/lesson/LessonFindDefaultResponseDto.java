package com.connect.oneboardserver.web.dto.lecture.lesson;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LessonFindDefaultResponseDto {

    private String defaultTitle;
    private String defaultDateTime;
    private String defaultRoom;

    @Builder
    public LessonFindDefaultResponseDto(String defaultTitle, String defaultDateTime, String defaultRoom) {
        this.defaultTitle = defaultTitle;
        this.defaultDateTime = defaultDateTime;
        this.defaultRoom = defaultRoom;
    }
}
