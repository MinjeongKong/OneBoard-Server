package com.connect.oneboardserver.web.controller.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.lesson.*;
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
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LessonApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LectureRepository lectureRepository;

    Random random = new Random();

    @AfterEach
    public void tearDown() throws Exception {
        lessonRepository.deleteAll();
        lectureRepository.deleteAll();
    }

    @Test
    @DisplayName("수업 생성 요청")
    public void requestCreateLesson(){
        // given
        Lecture expectedLecture = createLecture();

        String expectedTitle = "lesson" + random.nextInt(100);
        String expectedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Integer expectedType = 2;
        String expectedVideoUrl = "videoUrl" + random.nextInt(100);
        String expectedRoom = "room" + random.nextInt(100);

        LessonCreateRequestDto requestDto = LessonCreateRequestDto.builder()
                .title(expectedTitle)
                .date(expectedDate)
                .type(expectedType)
                .videoUrl(expectedVideoUrl)
                .room(expectedRoom)
                .build();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/lesson/test";

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.postForEntity(url, requestDto, ResponseDto.class, expectedLecture.getId());

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();
        ObjectMapper mapper = new ObjectMapper();
        LessonCreateResponseDto responseDto = mapper.convertValue(responseData, LessonCreateResponseDto.class);

        Lesson newLesson = lessonRepository.findById(responseDto.getLessonId()).orElseThrow();

        assertThat(newLesson.getLecture().getId()).isEqualTo(expectedLecture.getId());
        assertThat(newLesson.getTitle()).isEqualTo(expectedTitle);
        assertThat(newLesson.getDate()).isEqualTo(expectedDate);
        assertThat(newLesson.getType()).isEqualTo(expectedType);
        assertThat(newLesson.getVideoUrl()).isEqualTo(null);
        assertThat(newLesson.getRoom()).isEqualTo(expectedRoom);
    }

    @Test
    @DisplayName("수업 조회 요청")
    void requestFindLesson() {
        // given
        Lecture expectedLecture = createLecture();
        Lesson expectedLesson = createLesson(expectedLecture);

        String url = "http://localhost:" + port + "/lecture/{lectureId}/lesson/{lessonId}";

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.getForEntity(url, ResponseDto.class, expectedLecture.getId(), expectedLesson.getId());

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();
        ObjectMapper mapper = new ObjectMapper();
        LessonFindResponseDto responseDto = mapper.convertValue(responseData, LessonFindResponseDto.class);

        assertThat(responseDto.getLectureId()).isEqualTo(expectedLecture.getId());
        assertThat(responseDto.getLessonId()).isEqualTo(expectedLesson.getId());
        assertThat(responseDto.getTitle()).isEqualTo(expectedLesson.getTitle());
        assertThat(responseDto.getDate()).isEqualTo(expectedLesson.getDate());
        assertThat(responseDto.getType()).isEqualTo(expectedLesson.getType());
    }

    @Test
    @DisplayName("수업 삭제 요청")
    void requestDeleteLesson() {
        // given
        Lecture expectedLecture = createLecture();
        Lesson expectedLesson = createLesson(expectedLecture);

        String url = "http://localhost:" + port + "/lecture/{lectureId}/lesson/{lessonId}";

        // when
        restTemplate.delete(url, expectedLecture.getId(), expectedLesson.getId());

        // then
        assertThat(lessonRepository.findById(expectedLesson.getId())).isEmpty();
    }

    @Test
    @DisplayName("수업 수정 요청")
    void requestUpdateLesson() {
        // given
        Lecture expectedLecture = createLecture();
        Lesson expectedLesson = createLesson(expectedLecture);

        String updateTitle = "lesson" + random.nextInt(100);
        String updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Integer updateType = 2;
        String updateVideoUrl = null;
        String updateRoom = "팔 411";

        LessonUpdateRequestDto requestDto = LessonUpdateRequestDto.builder()
                .title(updateTitle)
                .date(updateDate)
                .type(updateType)
                .videoUrl(updateVideoUrl)
                .room(updateRoom)
                .build();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/lesson/{lessonId}/test";

        HttpEntity<LessonUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ResponseDto.class, expectedLecture.getId(), expectedLesson.getId());

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();

        ObjectMapper mapper = new ObjectMapper();
        LessonUpdateResponseDto responseDto = mapper.convertValue(responseData, LessonUpdateResponseDto.class);

        Lesson updatedLesson = lessonRepository.findById(responseDto.getLessonId()).orElseThrow();

        assertThat(updatedLesson.getLecture().getId()).isEqualTo(expectedLecture.getId());
        assertThat(updatedLesson.getId()).isEqualTo(expectedLesson.getId());
        assertThat(updatedLesson.getTitle()).isEqualTo(updateTitle);
        assertThat(updatedLesson.getDate()).isEqualTo(updateDate);
        assertThat(updatedLesson.getType()).isEqualTo(updateType);
    }

    @Test
    @DisplayName("수업 생성 시 디폴트 정보 요청")
    void requestFindLessonDefaultInfo() {
        // given
        Lecture expectedLecture = createLecture();
        Lesson expectedLesson = createLesson(expectedLecture);

        String expectedDefaultTitle = expectedLecture.getTitle() + " 수업 " + 2;
        String expectedDefaultRoom = expectedLecture.getDefaultRoom();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/lesson/default";

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.getForEntity(url, ResponseDto.class, expectedLecture.getId());

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();
        ObjectMapper mapper = new ObjectMapper();
        LessonFindDefaultResponseDto responseDto = mapper.convertValue(responseData, LessonFindDefaultResponseDto.class);

        assertThat(responseDto.getDefaultTitle()).isEqualTo(expectedDefaultTitle);
        assertThat(responseDto.getDefaultRoom()).isEqualTo(expectedDefaultRoom);
    }

    private Lecture createLecture() {
        String title = "lecture" + random.nextInt(100);
        String semester = "semester" + random.nextInt(100);
        String defaultDateTime = "화 12:00-14:00, 목 16:30-18:00";
        String defaultRoom = "room" + random.nextInt(100);
        String lecturePlanUrl = "lecturePlanUrl" + random.nextInt(100);

        return lectureRepository.save(Lecture.builder()
                .title(title)
                .semester(semester)
                .defaultDateTime(defaultDateTime)
                .defaultRoom(defaultRoom)
                .lecturePlanUrl(lecturePlanUrl)
                .build());
    }

    private Lesson createLesson(Lecture lecture) {
        String title = "lesson" + random.nextInt(100);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String noteUrl = "noteUrl" + random.nextInt(100);
        Integer type = 1;

        return lessonRepository.save(Lesson.builder()
                .lecture(lecture)
                .title(title)
                .date(date)
                .noteUrl(noteUrl)
                .type(type)
                .build());
    }

}