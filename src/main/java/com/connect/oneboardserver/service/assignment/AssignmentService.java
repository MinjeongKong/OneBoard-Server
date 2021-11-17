package com.connect.oneboardserver.service.assignment;

import com.connect.oneboardserver.domain.assignment.Assignment;
import com.connect.oneboardserver.domain.assignment.AssignmentRepository;
import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.assignment.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final LectureRepository lectureRepository;

    @Transactional
    public ResponseDto createAssignment(Long lectureId, AssignmentCreateRequestDto requestDto) {

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(()->new IllegalArgumentException("해당 과목이 없습니다. id="+lectureId));

        Assignment assignment = requestDto.toEntity();
        assignment.setLecture(lecture);

        assignmentRepository.save(assignment);
        AssignmentResponseDto responseDto = new AssignmentResponseDto(assignment);
        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    public ResponseDto updateAssignment(Long lectureId, Long assignmentId, AssignmentUpdateRequestDto requestDto) {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다. id="+assignmentId));

        if (!assignment.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            assignment.update(requestDto.getTitle(),requestDto.getContent(),requestDto.getFileUrl(),
                    requestDto.getStartDt(),requestDto.getEndDt(), requestDto.getExposeDt(), requestDto.getScore());
            AssignmentResponseDto responseDto = new AssignmentResponseDto(assignment);

            return new ResponseDto("SUCCESS", responseDto);
        }
    }

    @Transactional
    public ResponseDto deleteAssignment(Long lectureId, Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다. id="+assignmentId));

        if (!assignment.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            assignmentRepository.deleteById(assignmentId);
            return new ResponseDto("SUCCESS");
        }
    }

    public ResponseDto findAssignment(Long lectureId, Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다. id="+assignmentId));

        if (!assignment.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {

            AssignmentFindResponseDto responseDto = new AssignmentFindResponseDto(assignment);
            return new ResponseDto("SUCCESS", responseDto);
        }
    }

    public ResponseDto findAssignmentList(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(()->new IllegalArgumentException("해당 과목이 없습니다. id="+lectureId));

        List<Assignment> assignmentList = assignmentRepository.findAllByLectureId(lectureId);
        List<AssignmentFindResponseDto> assignmentFindResponseDtoList = new ArrayList<>();
        for (int i = 0; i < assignmentList.size(); i++) {
            assignmentFindResponseDtoList.add(AssignmentFindResponseDto.toResponseDto(assignmentList.get(i)));
        }

        return new ResponseDto("SUCCESS", assignmentFindResponseDtoList);
    }
}
