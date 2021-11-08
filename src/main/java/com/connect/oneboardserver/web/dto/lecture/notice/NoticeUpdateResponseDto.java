package com.connect.oneboardserver.web.dto.lecture.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Getter
//class Data {
//    private Long noticeId;
//
//    public Data(Long noticeId) {
//        this.noticeId = noticeId;
//    }
//}

@Getter
@NoArgsConstructor
public class NoticeUpdateResponseDto {

    private String result;
    private Long noticeId;
//    private Data data;

//    @NoArgsConstructor
//    private class Data {
//        private Long noticeId;
//
//        private Data(Long noticeId) {
//            this.noticeId = noticeId;
//        }
//    }

    @Builder
    public NoticeUpdateResponseDto(String result, Long noticeId) {
        this.result = result;
        this.noticeId = noticeId;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

//    public void setData(Long noticeId) {
//        this.data = new Data(noticeId);
//    }
}
