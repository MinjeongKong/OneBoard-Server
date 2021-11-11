package com.connect.oneboardserver.service.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.notice.Notice;
import com.connect.oneboardserver.domain.lecture.notice.NoticeRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
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

    public ResponseDto findNoticeList(Long lectureId) {
        Lecture lecture = null;

        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }
        List<Notice> noticeList = noticeRepository.findAllByLectureId(lecture.getId());
        List<NoticeFindResponseDto> noticeFindResponseDtoList = new ArrayList<>();
        for (int i = 0; i < noticeList.size(); i++) {
            noticeFindResponseDtoList.add(NoticeFindResponseDto.toResponseDto(noticeList.get(i)));
        }
        return new ResponseDto("SUCCESS", noticeFindResponseDtoList);
    }

    @Transactional
    public ResponseDto createNotice(Long lectureId, NoticeCreateRequestDto requestDto) {
        Lecture lecture = null;

        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }

        Notice notice = requestDto.toEntity();
        notice.setLecture(lecture);

        NoticeCreateResponseDto responseDto = NoticeCreateResponseDto.builder()
                .noticeId(noticeRepository.save(notice).getId())
                .build();
        return new ResponseDto("SUCCESS", responseDto);
    }

    public ResponseDto findNotice(Long lectureId, Long noticeId) {
        Notice notice = null;

        try {
            notice = noticeRepository.findById(noticeId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }

        if(!notice.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            NoticeFindResponseDto responseDto = NoticeFindResponseDto.toResponseDto(notice);
            return new ResponseDto("SUCCESS", responseDto);
        }
    }

    @Transactional
    public ResponseDto updateNotice(Long lectureId, long noticeId, NoticeUpdateRequestDto requestDto) {
        Notice notice = null;

        try {
            notice = noticeRepository.findById(noticeId)
                    .orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }

        if(!notice.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            notice.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getExposeDt());
            NoticeUpdateResponseDto responseDto = NoticeUpdateResponseDto.builder()
                    .noticeId(notice.getId())
                    .build();
            return new ResponseDto("SUCCESS", responseDto);
        }
    }

    @Transactional
    public ResponseDto deleteNotice(Long lectureId, Long noticeId) {
        Notice notice = null;

        try {
            notice = noticeRepository.findById(noticeId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }

        if(!notice.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            noticeRepository.deleteById(noticeId);
            return new ResponseDto("SUCCESS");
        }

    }
}
