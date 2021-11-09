package com.connect.oneboardserver.web.dto.lecture.notice;

import com.connect.oneboardserver.domain.lecture.notice.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeFindResponseDto {

    private Notice notice;

    @Builder
    public NoticeFindResponseDto(Notice notice) {
        this.notice = notice;
    }
}
