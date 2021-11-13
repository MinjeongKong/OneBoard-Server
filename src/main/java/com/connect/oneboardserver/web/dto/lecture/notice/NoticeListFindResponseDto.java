package com.connect.oneboardserver.web.dto.lecture.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NoticeListFindResponseDto {

    private List<NoticeFindResponseDto> noticeFindResponseDtoList;

    @Builder
    public NoticeListFindResponseDto(List<NoticeFindResponseDto> noticeFindResponseDtoList) {
        this.noticeFindResponseDtoList = noticeFindResponseDtoList;
    }
}
