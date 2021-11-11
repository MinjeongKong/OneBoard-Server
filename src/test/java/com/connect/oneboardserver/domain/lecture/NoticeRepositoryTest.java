package com.connect.oneboardserver.domain.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.notice.Notice;
import com.connect.oneboardserver.domain.lecture.notice.NoticeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private LectureRepository lectureRepository;

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
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

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
        assertThat(notice.getCreatedDt()).isGreaterThanOrEqualTo(now);
    }

    @Test
    @DisplayName("과목 공지사항 목록 조회")
    void findAllByLectureId() {
        // given
        Lecture lecture1 = lectureRepository.save(Lecture.builder()
                .title("title1")
                .lecturePlan("plan1")
                .semester("semester1")
                .build());

        Lecture lecture2 = lectureRepository.save(Lecture.builder()
                .title("title2")
                .lecturePlan("plan2")
                .semester("semester2")
                .build());

        Notice notice1 = noticeRepository.save(Notice.builder()
                .lecture(lecture1)
                .title("title1")
                .content("content1")
                .exposeDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build());

        Notice notice2 = noticeRepository.save(Notice.builder()
                .lecture(lecture1)
                .title("title2")
                .content("content2")
                .exposeDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build());

        Notice notice3 = noticeRepository.save(Notice.builder()
                .lecture(lecture2)
                .title("title3")
                .content("content3")
                .exposeDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build());

        // when
        List<Notice> noticeList = noticeRepository.findAllByLectureId(lecture1.getId());

        // then
        System.out.println(noticeList);
        for (Notice n : noticeList) {
            System.out.println(n.getId());
            System.out.println(n.getTitle());
        }

        assertThat(noticeList.size()).isEqualTo(2);
        for (int i = 0; i < noticeList.size(); i++) {
            assertThat(noticeList.get(i).getLecture().getId()).isEqualTo(lecture1.getId());
        }
    }
}
