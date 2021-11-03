package com.connect.oneboardserver.web.lecture.notice;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.service.lecture.notice.NoticeService;
import com.connect.oneboardserver.web.dto.LessonCreateRequestDto;
import com.connect.oneboardserver.web.dto.lecture.notice.NoticeCreateRequestDto;
import com.connect.oneboardserver.web.dto.lecture.notice.NoticeCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class NoticeApiController {

    private final NoticeService noticeService;

    // 과목 공지사항 등록
    @PostMapping("/lecture/{lectureId}/notice")
    public NoticeCreateResponseDto createNotice(@PathVariable Long lectureId, @RequestBody NoticeCreateRequestDto requestDto) {
        return noticeService.createNotice(lectureId, requestDto);
    }

    // 과목 공지사항 수정
//    @PostMapping("/lecture/{lectureId}/notice/{noticeId}")


    // 과목 공지사항 목록 조회
//    @GetMapping("/lecture/{lectureId}/notice")
}
