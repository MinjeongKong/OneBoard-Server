package com.connect.oneboardserver.service.assignment;

import com.connect.oneboardserver.domain.assignment.Assignment;
import com.connect.oneboardserver.domain.assignment.AssignmentRepository;
import com.connect.oneboardserver.domain.assignment.Submit;
import com.connect.oneboardserver.domain.assignment.SubmitRepository;
import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.service.storage.StorageService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.assignment.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AssignmentService {

    @Qualifier("FileStorageService")
    private final StorageService storageService;
    private final AssignmentRepository assignmentRepository;
    private final SubmitRepository submitRepository;
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
    public ResponseDto createAssignmentFile(Long lectureId, AssignmentCreateRequestDto requestDto, MultipartFile file) throws Exception{
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(()->new IllegalArgumentException("해당 과목이 없습니다. id="+lectureId));

        Assignment assignment = requestDto.toEntity();
        assignment.setLecture(lecture);

        assignmentRepository.save(assignment);
        AssignmentResponseDto responseDto = new AssignmentResponseDto(assignment);

        String uploadedFile = null;
        if (file != null) {
            String path = "/lecture_" + lectureId + "/assignment_" + assignment.getId();
            uploadedFile = storageService.store(path, file);

            assignment.setFileUrl(uploadedFile);
        }

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
    public ResponseDto updateAssignmentFile(Long lectureId, Long assignmentId, AssignmentUpdateRequestDto requestDto, MultipartFile file)throws Exception {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다. id="+assignmentId));

        if (!assignment.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            if (assignment.getFileUrl() != null) {
                if (storageService.delete(assignment.getFileUrl())) {
                    assignment.setFileUrl(null);
                }
            }
            assignment.update(requestDto.getTitle(),requestDto.getContent(),requestDto.getFileUrl(),
                    requestDto.getStartDt(),requestDto.getEndDt(), requestDto.getExposeDt(), requestDto.getScore());

            if (file != null) {
                String path = "/lecture_" + lectureId + "/assignment_" + assignment.getId();
                String uploadedFile = storageService.store(path, file);

                assignment.setFileUrl(uploadedFile);
            }
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
            submitRepository.deleteAllByAssignmentId(assignmentId);
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

        List<Assignment> assignmentList = assignmentRepository.findAllByLectureIdOrderByEndDt(lectureId);
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<AssignmentFindResponseDto> assignmentFindResponseDtoList = new ArrayList<>();

        for(int i = assignmentList.size()-1; i >= 0 ; i--) {
            Assignment assignment = assignmentList.get(i);
            if(assignment.getEndDt().compareTo(now) < 0) {
                break;
            }
            assignmentFindResponseDtoList.add(0, AssignmentFindResponseDto.toResponseDto(assignment));
            assignmentList.remove(i);
        }
        for (int i = 0; i < assignmentList.size(); i++) {
            assignmentFindResponseDtoList.add(AssignmentFindResponseDto.toResponseDto(assignmentList.get(i)));
        }

        return new ResponseDto("SUCCESS", assignmentFindResponseDtoList);
    }
}
