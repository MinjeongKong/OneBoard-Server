package com.connect.oneboardserver.web.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.LectureCreateRequestDto;
import com.connect.oneboardserver.web.dto.lecture.LectureCreateResponseDto;
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
    @DisplayName("과목 등록 요청")
    void requestCreateLecture() {
        // given
        String title = "test lecture";
        String semester = "2021-2";

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
}