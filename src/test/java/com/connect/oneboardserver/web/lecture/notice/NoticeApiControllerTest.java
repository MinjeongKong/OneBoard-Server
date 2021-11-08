package com.connect.oneboardserver.web.lecture.notice;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.notice.Notice;
import com.connect.oneboardserver.domain.lecture.notice.NoticeRepository;
import com.connect.oneboardserver.domain.lesson.Lesson;
import com.connect.oneboardserver.web.dto.lecture.notice.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoticeApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @AfterEach
    void cleanUp() {
        noticeRepository.deleteAll();
        lectureRepository.deleteAll();
    }

    @Test
    @DisplayName("과목 공지사항 등록 요청")
    void requestCreateNotice() {
        // given
        String lectureTitle = "test lecture";
        String lecturePlan = "test url";
        String semester = "2021-2";

        Long lectureId = lectureRepository.save(Lecture.builder()
                .title(lectureTitle)
                .lecturePlan(lecturePlan)
                .semester(semester)
                .build()).getId();

        String noticeTitle = "test notice";
        String content = "test content";
        LocalDateTime now = LocalDateTime.now();

        NoticeCreateRequestDto requestDto = NoticeCreateRequestDto.builder()
                .title(noticeTitle)
                .content(content)
                .exposeDt(now)
                .build();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/notice";

        // when
        ResponseEntity<NoticeCreateResponseDto> responseEntity
                = restTemplate.postForEntity(url, requestDto, NoticeCreateResponseDto.class, lectureId);
//        NoticeCreateResponseDto responseDto = restTemplate.postForObject(url, requestDto, NoticeCreateResponseDto.class, lectureId);

        // then
        System.out.println(responseEntity);
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Lecture> lectureList = lectureRepository.findAll();
        List<Notice> noticeList = noticeRepository.findAll();

        assertThat(noticeList.get(0).getTitle()).isEqualTo(noticeTitle);
        assertThat(noticeList.get(0).getExposeDt()).isEqualToIgnoringNanos(now);
        assertThat(noticeList.get(0).getLecture().getId()).isEqualTo(lectureList.get(0).getId());
    }

    @Test
    @DisplayName("과목 공지사항 조회")
    void requestFindNotice() {
        // given
        String lectureTitle = "test lecture";
        String lecturePlan = "test url";
        String semester = "2021-2";

        Lecture lecture = Lecture.builder()
                .title(lectureTitle)
                .lecturePlan(lecturePlan)
                .semester(semester)
                .build();

        Long lectureId = lectureRepository.save(lecture).getId();

        String noticeTitle = "test notice";
        String content = "test content";
        LocalDateTime now = LocalDateTime.now();

        Notice notice = Notice.builder()
                .lecture(lecture)
                .title(noticeTitle)
                .content(content)
                .exposeDt(now)
                .build();

        Long noticeId = noticeRepository.save(notice).getId();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/notice/{noticeId}";

        // when
        ResponseEntity<NoticeResponseDto> responseEntity
                = restTemplate.getForEntity(url, NoticeResponseDto.class, lectureId, noticeId);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(responseEntity.getBody().getNoticeList().get(0).getId()).isEqualTo(noticeId);
        assertThat(responseEntity.getBody().getNoticeList().get(0).getExposeDt()).isEqualToIgnoringNanos(now);
        assertThat(responseEntity.getBody().getNoticeList().get(0).getLecture().getId()).isEqualTo(lectureId);
    }

    @Test
    @DisplayName("과목 공지사항 수정")
    void requestUpdateNotice() {
        // given
        String lectureTitle = "test lecture";
        String lecturePlan = "test url";
        String semester = "2021-2";

        Lecture lecture = Lecture.builder()
                .title(lectureTitle)
                .lecturePlan(lecturePlan)
                .semester(semester)
                .build();

        Long lectureId = lectureRepository.save(lecture).getId();

        String noticeTitle = "test notice";
        String content = "test content";
        LocalDateTime now = LocalDateTime.now();

        Notice notice = Notice.builder()
                .lecture(lecture)
                .title(noticeTitle)
                .content(content)
                .exposeDt(now)
                .build();

        Long noticeId = noticeRepository.save(notice).getId();

        String updateTitle = "updated title";
        String updateContent = "updated content";
        NoticeUpdateRequestDto requestDto = NoticeUpdateRequestDto.builder()
                .title(updateTitle)
                .content(updateContent)
                .exposeDt(now).build();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/notice/{noticeId}";

        HttpEntity<NoticeUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<NoticeUpdateResponseDto> responseEntity
                = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, NoticeUpdateResponseDto.class, lectureId, noticeId);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Notice updatedNotice = noticeRepository.findById(responseEntity.getBody().getNoticeId()).orElseThrow();
        assertThat(updatedNotice.getTitle()).isEqualTo(updateTitle);
        assertThat(updatedNotice.getContent()).isEqualTo(updateContent);
    }
}
