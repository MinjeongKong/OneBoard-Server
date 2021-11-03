package com.connect.oneboardserver.service.lecture.notice;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.notice.Notice;
import com.connect.oneboardserver.domain.lecture.notice.NoticeRepository;
import com.connect.oneboardserver.web.dto.lecture.notice.NoticeCreateRequestDto;
import com.connect.oneboardserver.web.dto.lecture.notice.NoticeCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final LectureRepository lectureRepository;
    private final NoticeRepository noticeRepository;

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
}
