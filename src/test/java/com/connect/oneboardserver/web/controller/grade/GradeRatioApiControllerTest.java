package com.connect.oneboardserver.web.controller.grade;

import com.connect.oneboardserver.domain.grade.GradeRatio;
import com.connect.oneboardserver.domain.grade.GradeRatioRepository;
import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.relation.GradeRatioLecture;
import com.connect.oneboardserver.domain.relation.GradeRatioLectureRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.grade.GradeRatioCreateRequestDto;
import com.connect.oneboardserver.web.dto.grade.GradeRatioResponseDto;
import com.connect.oneboardserver.web.dto.grade.GradeRatioUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GradeRatioApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GradeRatioRepository gradeRatioRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private GradeRatioLectureRepository gradeRatioLectureRepository;

    @AfterEach
    void cleanUp() {
        gradeRatioLectureRepository.deleteAll();
        gradeRatioRepository.deleteAll();
        lectureRepository.deleteAll();
    }

    @Test
    @DisplayName("학점 비율 수정 요청")
    void requestUpdateGradeRatio() {
        // given
        String lectureTitle = "test lecture";
        String lecturePlan = "test url";
        String semester = "2021-2";

        Lecture lecture = lectureRepository.save(Lecture.builder()
                .title(lectureTitle)
                .lecturePlanUrl(lecturePlan)
                .semester(semester)
                .build());

        Long lectureId = lectureRepository.save(lecture).getId();
        Integer aratio = 30;
        Integer bRatio = 70;

        GradeRatio gradeRatio = gradeRatioRepository.save(GradeRatio.builder()
                .aratio(aratio)
                .bratio(bRatio)
                .build());

        GradeRatioLecture gradeRatioLecture = gradeRatioLectureRepository.save(GradeRatioLecture.builder()
                .lecture(lecture)
                .gradeRatio(gradeRatio)
                .build());

        Integer updateA = 20;
        Integer updateB = 80;

        GradeRatioUpdateRequestDto requestDto = GradeRatioUpdateRequestDto.builder()
                .aratio(updateA)
                .bratio(updateB)
                .build();


        String url = "http://localhost:" + port + "/lecture/{lectureId}/grade/ratio";

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.postForEntity(url, requestDto, ResponseDto.class, lectureId);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();

        ObjectMapper mapper = new ObjectMapper();
        GradeRatioResponseDto responseDto = mapper.convertValue(responseData, GradeRatioResponseDto.class);

        GradeRatio newGradeRatio = gradeRatioRepository.findById(responseDto.getGradeRatioId()).orElseThrow();

        assertThat(newGradeRatio.getAratio()).isEqualTo(updateA);
        assertThat(newGradeRatio.getBratio()).isEqualTo(updateB);

    }


//    @Test
//    @DisplayName("학점 비율 입력")
    void requestCreateGradeRatio() {
        // given
        String lectureTitle = "test lecture";
        String lecturePlan = "test url";
        String semester = "2021-2";

        Long lectureId = lectureRepository.save(Lecture.builder()
                .title(lectureTitle)
                .lecturePlanUrl(lecturePlan)
                .semester(semester)
                .build()).getId();

        Integer aratio = 30;
        Integer bRatio = 70;

        Integer aerror = 40;
        Integer berror = 80;

        GradeRatioCreateRequestDto requestDto1 = GradeRatioCreateRequestDto.builder()
                .aratio(aratio)
                .bratio(bRatio)
                .build();

        GradeRatioCreateRequestDto requestDto2 = GradeRatioCreateRequestDto.builder()
                .aratio(aerror)
                .bratio(berror)
                .build();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/grade/ratio-dev";

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.postForEntity(url, requestDto1, ResponseDto.class, lectureId);

        ResponseEntity<ResponseDto> responseEntity1
                = restTemplate.postForEntity(url, requestDto2, ResponseDto.class, lectureId);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity1.getBody().getResult()).isEqualTo("FAIL");

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();

        ObjectMapper mapper = new ObjectMapper();
        GradeRatioResponseDto responseDto = mapper.convertValue(responseData, GradeRatioResponseDto.class);

        GradeRatio newGradeRatio = gradeRatioRepository.findById(responseDto.getGradeRatioId()).orElseThrow();

        assertThat(newGradeRatio.getAratio()).isEqualTo(aratio);
        assertThat(newGradeRatio.getBratio()).isEqualTo(bRatio);
    }
}
