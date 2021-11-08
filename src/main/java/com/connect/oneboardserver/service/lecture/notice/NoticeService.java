package com.connect.oneboardserver.service.lecture.notice;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.notice.Notice;
import com.connect.oneboardserver.domain.lecture.notice.NoticeRepository;
import com.connect.oneboardserver.web.dto.ReturnDto;
import com.connect.oneboardserver.web.dto.lecture.notice.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final LectureRepository lectureRepository;
    private final NoticeRepository noticeRepository;

    public ReturnDto findNoticeList(Long lectureId) {
        Lecture lecture = null;

        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnDto("FAIL");
        }
        List<Notice> noticeList = noticeRepository.findAllByLectureId(lecture.getId());
        NoticeListFindResponseDto responseDto = NoticeListFindResponseDto.builder()
                .noticeList(noticeList)
                .build();
        return new ReturnDto("SUCCESS", responseDto);
    }

    @Transactional
    public ReturnDto createNotice(Long lectureId, NoticeCreateRequestDto requestDto) {
        Lecture lecture = null;

        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnDto("FAIL");
        }

        Notice notice = requestDto.toEntity();
        notice.setLecture(lecture);

        NoticeCreateResponseDto responseDto = NoticeCreateResponseDto.builder()
                .noticeId(noticeRepository.save(notice).getId())
                .build();
        return new ReturnDto("SUCCESS", responseDto);
    }

    public ReturnDto findNotice(Long lectureId, Long noticeId) {
        Notice notice = null;

        try {
            notice = noticeRepository.findById(noticeId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnDto("FAIL");
        }

        if(!notice.getLecture().getId().equals(lectureId)) {
            return new ReturnDto("FAIL");
        } else {
            NoticeFindResponseDto responseDto = NoticeFindResponseDto.builder()
                    .notice(notice)
                    .build();
            return new ReturnDto("SUCCESS", responseDto);
        }
    }

    @Transactional
    public ReturnDto updateNotice(Long lectureId, long noticeId, NoticeUpdateRequestDto requestDto) {
        Notice notice = null;

        try {
            notice = noticeRepository.findById(noticeId)
                    .orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnDto("FAIL");
        }

        if(!notice.getLecture().getId().equals(lectureId)) {
            return new ReturnDto("FAIL");
        } else {
            notice.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getExposeDt());
            NoticeUpdateResponseDto responseDto = NoticeUpdateResponseDto.builder()
                    .noticeId(notice.getId())
                    .build();
            return new ReturnDto("SUCCESS", responseDto);
        }
    }

    @Transactional
    public ReturnDto deleteNotice(Long lectureId, Long noticeId) {
        Notice notice = null;

        try {
            notice = noticeRepository.findById(noticeId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnDto("FAIL");
        }

        if(!notice.getLecture().getId().equals(lectureId)) {
            return new ReturnDto("FAIL");
        } else {
            noticeRepository.deleteById(noticeId);
            return new ReturnDto("SUCCESS");
        }

    }
}
