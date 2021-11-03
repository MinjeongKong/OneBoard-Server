package com.connect.oneboardserver.web.dto.lecture.notice;

import com.connect.oneboardserver.domain.lecture.notice.Notice;

import java.time.LocalDateTime;

public class NoticeCreateRequestDto {

    private String title;
    private String content;
    private LocalDateTime exposeDt;

    public NoticeCreateRequestDto(String title, String content, LocalDateTime exposeDt) {
        this.title = title;
        this.content = content;
        this.exposeDt = exposeDt;
    }

    public Notice toEntity() {
        return Notice.builder()
                .title(title)
                .content(content)
                .exposeDt(exposeDt)
                .build();
    }
}
