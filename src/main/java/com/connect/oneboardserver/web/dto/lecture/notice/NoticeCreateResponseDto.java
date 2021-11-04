package com.connect.oneboardserver.web.dto.lecture.notice;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeCreateResponseDto {

    private String result;
    private Long data;

    @Builder
    public NoticeCreateResponseDto(String result, Long data) {
        this.result = result;
        this.data = data;
    }
}
