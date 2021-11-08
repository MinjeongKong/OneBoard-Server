package com.connect.oneboardserver.web.dto.lecture.notice;

import com.connect.oneboardserver.domain.lecture.notice.Notice;
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

    public Notice toEntity() {
        return Notice.builder()
                .title(title)
                .content(content)
                .exposeDt(exposeDt)
                .build();
    }
}
