package com.connect.oneboardserver.web.dto.lecture.notice;

import com.connect.oneboardserver.domain.lecture.notice.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeCreateRequestDto {

    private String title;
    private String content;
    private LocalDateTime exposeDt;

    @Builder
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
