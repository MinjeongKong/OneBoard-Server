package com.connect.oneboardserver.domain.lecture.notice;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NoticeRepositoryTest {

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    LectureRepository lectureRepository;

    @AfterEach
    public void cleanUp() {
        noticeRepository.deleteAll();
        lectureRepository.deleteAll();
    }

    @Test
    @DisplayName("공지사항 생성 및 조회")
    public void createNotice() {
        // given
        String lectureTitle = "test lecture";
        String lecturePlan = "test url";
        String semester = "2021-2";

        Lecture lecture = lectureRepository.save(Lecture.builder()
                .title(lectureTitle)
                .lecturePlan(lecturePlan)
                .semester(semester)
                .build());

        String noticeTitle = "test notice";
        String content = "test content";
        LocalDateTime now = LocalDateTime.now();

        noticeRepository.save(Notice.builder()
                .lecture(lecture)
                .title(noticeTitle)
                .content(content)
                .exposeDt(now)
                .build());

        // when
        List<Notice> noticeList = noticeRepository.findAll();

        // then
        Notice notice = noticeList.get(0);
        assertThat(notice.getTitle()).isEqualTo(noticeTitle);
        assertThat(notice.getLecture().getId()).isEqualTo(lecture.getId());
        assertThat(notice.getExposeDt()).isEqualTo(now);
        assertThat(notice.getCreatedDt()).isAfter(now);
    }
}
