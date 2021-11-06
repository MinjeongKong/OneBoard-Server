package com.connect.oneboardserver.web.dto.lecture.notice;

import com.connect.oneboardserver.domain.lecture.notice.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NoticeResponseDto {

    private String result;
    private List<Notice> noticeList;

    @Builder
    public NoticeResponseDto(String result, List<Notice> noticeList) {
        this.result = result;
        this.noticeList = noticeList;
    }
}
