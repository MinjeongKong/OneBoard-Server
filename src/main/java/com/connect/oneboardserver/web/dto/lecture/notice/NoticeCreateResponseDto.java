package com.connect.oneboardserver.web.dto.lecture.notice;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeCreateResponseDto {

    private Long noticeId;

    @Builder
    public NoticeCreateResponseDto(String result, Long noticeId) {
        this.noticeId = noticeId;
    }
}
