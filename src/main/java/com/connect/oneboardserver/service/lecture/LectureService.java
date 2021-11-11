package com.connect.oneboardserver.service.lecture;

import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.LectureCreateRequestDto;
import com.connect.oneboardserver.web.dto.lecture.LectureCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LectureService {

    private final LectureRepository lectureRepository;

    public ResponseDto createLecture(LectureCreateRequestDto requestDto) {
        LectureCreateResponseDto responseDto = LectureCreateResponseDto.builder()
                .lectureId(lectureRepository.save(requestDto.toEntity()).getId())
                .build();
        return new ResponseDto("SUCCESS", responseDto);
    }
}
