package com.connect.oneboardserver.service.lecture.notice;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.notice.Notice;
import com.connect.oneboardserver.domain.lecture.notice.NoticeRepository;
import com.connect.oneboardserver.web.dto.lecture.notice.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final LectureRepository lectureRepository;
    private final NoticeRepository noticeRepository;

    public NoticeResponseDto findNoticeList(Long lectureId) {
        List<Notice> noticeList = noticeRepository.findAllByLectureId(lectureId);
        return NoticeResponseDto.builder()
                .result("SUCCESS")
                .noticeList(noticeList)
                .build();
    }

    @Transactional
    public NoticeCreateResponseDto createNotice(Long lectureId, NoticeCreateRequestDto requestDto) {
        Lecture lecture = null;

        try {
            lecture = lectureRepository.findById(lectureId)
                    .orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return NoticeCreateResponseDto.builder()
                    .result("FAIL")
                    .build();
        }

        Notice notice = requestDto.toEntity();
        notice.setLecture(lecture);

        return NoticeCreateResponseDto.builder()
                .result("SUCCESS")
                .data(noticeRepository.save(notice).getId())
                .build();
    }

    public NoticeResponseDto findNotice(Long lectureId, Long noticeId) {
        List<Notice> noticeList = new ArrayList<>();
        try {
            noticeList.add(noticeRepository.findById(noticeId).orElseThrow(Exception::new));
        } catch (Exception e) {
            e.printStackTrace();
            return NoticeResponseDto.builder()
                    .result("FAIL")
                    .build();
        }

        if(!lectureId.equals(noticeList.get(0).getLecture().getId())) {
            return NoticeResponseDto.builder()
                    .result("FAIL")
                    .build();
        } else {
            return NoticeResponseDto.builder()
                    .result("SUCCESS")
                    .noticeList(noticeList)
                    .build();
        }
    }

    @Transactional
    public NoticeUpdateResponseDto updateNotice(Long lectureId, long noticeId, NoticeUpdateRequestDto requestDto) {
        Notice notice = null;
        NoticeUpdateResponseDto responseDto = NoticeUpdateResponseDto.builder()
                .result("FAIL")
                .build();
        try {
            notice = noticeRepository.findById(noticeId)
                    .orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return responseDto;
        }

        notice.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getExposeDt());

        responseDto.setResult("SUCCESS");
//        responseDto.setNoticeId(notice.getId());
        responseDto.setData(notice.getId());

        return responseDto;
    }
}
