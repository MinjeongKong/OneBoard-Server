package com.connect.oneboardserver.web.dto.lecture.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeUpdateResponseDto {

    private Long noticeId;

    @Builder
    public NoticeUpdateResponseDto(Long noticeId) {
        this.noticeId = noticeId;
    }
}
