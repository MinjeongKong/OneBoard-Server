package com.connect.oneboardserver.web.dto.lecture.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeUpdateRequestDto {

    private String title;
    private String content;
    private String exposeDt;

    @Builder
    public NoticeUpdateRequestDto(String title, String content, String exposeDt) {
        this.title = title;
        this.content = content;
        this.exposeDt = exposeDt;
    }
}
