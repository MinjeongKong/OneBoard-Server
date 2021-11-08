package com.connect.oneboardserver.web.dto.lecture.notice;

import com.connect.oneboardserver.domain.lecture.notice.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NoticeListFindResponseDto {

    private List<Notice> noticeList;

    @Builder
    public NoticeListFindResponseDto(List<Notice> noticeList) {
        this.noticeList = noticeList;
    }
}
