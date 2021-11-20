package com.connect.oneboardserver.web.controller.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.notice.Notice;
import com.connect.oneboardserver.domain.lecture.notice.NoticeRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.notice.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        String lecturePlanUrl = "test url";
        String semester = "2021-2";

        Long lectureId = lectureRepository.save(Lecture.builder()
                .title(lectureTitle)
                .lecturePlanUrl(lecturePlanUrl)
                .semester(semester)
                .build()).getId();

        String noticeTitle = "test notice";
        String content = "test content";
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        NoticeCreateRequestDto requestDto = NoticeCreateRequestDto.builder()
                .title(noticeTitle)
                .content(content)
                .exposeDt(now)
                .build();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/notice";

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.postForEntity(url, requestDto, ResponseDto.class, lectureId);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();

        ObjectMapper mapper = new ObjectMapper();
        NoticeCreateResponseDto responseDto = mapper.convertValue(responseData, NoticeCreateResponseDto.class);

        Notice newNotice = noticeRepository.findById(responseDto.getNoticeId()).orElseThrow();

        assertThat(newNotice.getTitle()).isEqualTo(noticeTitle);
        assertThat(newNotice.getExposeDt()).isEqualTo(now);
        assertThat(newNotice.getLecture().getId()).isEqualTo(lectureId);
    }

    @Test
    @DisplayName("과목 공지사항 조회 요청")
    void requestFindNotice() {
        // given
        String lectureTitle = "test lecture";
        String lecturePlanUrl = "test url";
        String semester = "2021-2";

        Lecture lecture = Lecture.builder()
                .title(lectureTitle)
                .lecturePlanUrl(lecturePlanUrl)
                .semester(semester)
                .build();

        Long lectureId = lectureRepository.save(lecture).getId();

        String noticeTitle = "test notice";
        String content = "test content";
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Notice notice = Notice.builder()
                .lecture(lecture)
                .title(noticeTitle)
                .content(content)
                .exposeDt(now)
                .build();

        Long noticeId = noticeRepository.save(notice).getId();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/notice/{noticeId}";

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.getForEntity(url, ResponseDto.class, lectureId, noticeId);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();

        ObjectMapper mapper = new ObjectMapper();
        NoticeFindResponseDto responseDto = mapper.convertValue(responseData, NoticeFindResponseDto.class);

        assertThat(responseDto.getId()).isEqualTo(noticeId);
        assertThat(responseDto.getExposeDt()).isEqualTo(now);
        assertThat(responseDto.getLectureId()).isEqualTo(lectureId);
    }

    @Test
    @DisplayName("과목 공지사항 수정 요청")
    void requestUpdateNotice() {
        // given
        String lectureTitle = "test lecture";
        String lecturePlanUrl = "test url";
        String semester = "2021-2";

        Lecture lecture = Lecture.builder()
                .title(lectureTitle)
                .lecturePlanUrl(lecturePlanUrl)
                .semester(semester)
                .build();

        Long lectureId = lectureRepository.save(lecture).getId();

        String noticeTitle = "test notice";
        String content = "test content";
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

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
                .exposeDt(now)
                .build();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/notice/{noticeId}";

        HttpEntity<NoticeUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ResponseDto.class, lectureId, noticeId);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();

        ObjectMapper mapper = new ObjectMapper();
        NoticeUpdateResponseDto responseDto = mapper.convertValue(responseData, NoticeUpdateResponseDto.class);

        Notice updatedNotice = noticeRepository.findById(responseDto.getNoticeId()).orElseThrow();

        assertThat(updatedNotice.getTitle()).isEqualTo(updateTitle);
        assertThat(updatedNotice.getContent()).isEqualTo(updateContent);
    }

    @Test
    @DisplayName("과목 공지사항 삭제 요청")
    void requestDeleteNotice() {
        // given
        String lectureTitle = "test lecture";
        String lecturePlanUrl = "test url";
        String semester = "2021-2";

        Lecture lecture = Lecture.builder()
                .title(lectureTitle)
                .lecturePlanUrl(lecturePlanUrl)
                .semester(semester)
                .build();

        Long lectureId = lectureRepository.save(lecture).getId();

        String noticeTitle = "test notice";
        String content = "test content";
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Notice notice = Notice.builder()
                .lecture(lecture)
                .title(noticeTitle)
                .content(content)
                .exposeDt(now)
                .build();

        Long noticeId = noticeRepository.save(notice).getId();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/notice/{noticeId}";

        // when
        restTemplate.delete(url, lectureId, noticeId);

        // then
        assertThat(noticeRepository.findById(noticeId)).isEmpty();
    }
}
