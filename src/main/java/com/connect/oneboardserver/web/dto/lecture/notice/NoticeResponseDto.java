package com.connect.oneboardserver.web.dto.lecture.notice;

import com.connect.oneboardserver.domain.lecture.notice.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeResponseDto {

    private String result;
    private Notice notice;

    @Builder
    public NoticeResponseDto(String result, Notice notice) {
        this.result = result;
        this.notice = notice;
    }
}
