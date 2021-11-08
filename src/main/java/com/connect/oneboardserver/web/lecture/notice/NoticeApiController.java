package com.connect.oneboardserver.web.lecture.notice;

import com.connect.oneboardserver.service.lecture.notice.NoticeService;
import com.connect.oneboardserver.web.dto.ReturnDto;
import com.connect.oneboardserver.web.dto.lecture.notice.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class NoticeApiController {

    private final NoticeService noticeService;

    // 과목 공지사항 목록 조회
    @GetMapping("/lecture/{lectureId}/notices")
    public ReturnDto findNoticeList(@PathVariable Long lectureId) {
        return noticeService.findNoticeList(lectureId);
    }

    // 과목 공지사항 등록
    @PostMapping("/lecture/{lectureId}/notice")
    public ReturnDto createNotice(@PathVariable Long lectureId, @RequestBody NoticeCreateRequestDto requestDto) {
        return noticeService.createNotice(lectureId, requestDto);
    }

    // 과목 공지사항 조회
    @GetMapping("/lecture/{lectureId}/notice/{noticeId}")
    public ReturnDto findNotice(@PathVariable Long lectureId, @PathVariable Long noticeId) {
        return noticeService.findNotice(lectureId, noticeId);
    }

   // 과목 공지사항 수정
    @PutMapping("/lecture/{lectureId}/notice/{noticeId}")
    public ReturnDto updateNotice(@PathVariable Long lectureId, @PathVariable Long noticeId,
                                                @RequestBody NoticeUpdateRequestDto requestDto) {
        return noticeService.updateNotice(lectureId, noticeId, requestDto);
    }


    // 과목 공지사항 삭제
    @DeleteMapping("/lecture/{lectureId}/notice/{noticeId}")
    public ReturnDto deleteNotice(@PathVariable Long lectureId, @PathVariable Long noticeId) {
        return noticeService.deleteNotice(lectureId, noticeId);
    }
}
