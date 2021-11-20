package com.connect.oneboardserver.web.controller.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.LectureCreateRequestDto;
import com.connect.oneboardserver.web.dto.lecture.LectureCreateResponseDto;
import com.connect.oneboardserver.web.dto.lecture.LectureFindResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LectureApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LectureRepository lectureRepository;

    @AfterEach
    void cleanUp() {
        lectureRepository.deleteAll();
    }

    @Test
    @DisplayName("과목 생성 요청")
    void requestCreateLecture() {
        // given
        String title = "lecture" + createRandomNumber();
        String semester = "semester" + createRandomNumber();

        LectureCreateRequestDto requestDto = LectureCreateRequestDto.builder()
                .title(title)
                .semester(semester)
                .build();

        String url = "http://localhost:" + port + "/lecture";

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.postForEntity(url, requestDto, ResponseDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();

        ObjectMapper mapper = new ObjectMapper();
        LectureCreateResponseDto responseDto = mapper.convertValue(responseData, LectureCreateResponseDto.class);

        Lecture newLecture = lectureRepository.findById(responseDto.getLectureId()).orElseThrow();

        assertThat(newLecture.getTitle()).isEqualTo(title);
        assertThat(newLecture.getSemester()).isEqualTo(semester);
    }

    // 과목 등록 테스트

    @Test
    @DisplayName("과목 정보 조회 요청")
    void requestFindLecture() {
        // given
        String title = "lecture" + createRandomNumber();
        String semester = "semester" + createRandomNumber();

        Lecture lecture = lectureRepository.save(Lecture.builder()
                .title(title)
                .semester(semester)
                .build());

        String url = "http://localhost:" + port + "/lecture/{lectureId}";

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.getForEntity(url, ResponseDto.class, lecture.getId());

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();

        ObjectMapper mapper = new ObjectMapper();
        LectureFindResponseDto responseDto = mapper.convertValue(responseData, LectureFindResponseDto.class);

        assertThat(responseDto.getTitle()).isEqualTo(title);
        assertThat(responseDto.getSemester()).isEqualTo(semester);
    }

    private int createRandomNumber() {
        return (int)(Math.random() * 100);
    }
}