package com.connect.oneboardserver.web.controller.assignment;

import com.connect.oneboardserver.service.assignment.AssignmentService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.assignment.AssignmentCreateRequestDto;
import com.connect.oneboardserver.web.dto.assignment.AssignmentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class AssignmentApiController {

    private final AssignmentService assignmentService;

    //과제 등록
    @PostMapping("/lecture/{lectureId}/assignment")
    public ResponseDto createAssignment(@PathVariable Long lectureId, @RequestBody AssignmentCreateRequestDto requestDto) {
        return assignmentService.createAssignment(lectureId, requestDto);
    }


    //과제 수정
    @PutMapping("/lecture/{lectureId}/assignment/{assignmentId}")
    public ResponseDto updateAssignment(@PathVariable Long lectureId, @PathVariable Long assignmentId,
                                        @RequestBody AssignmentUpdateRequestDto requestDto) {
        return assignmentService.updateAssignment(lectureId, assignmentId, requestDto);
    }


    //과제 삭제
    @DeleteMapping("/lecture/{lectureId}/assignment/{assignmentId}")
    public ResponseDto deleteAssignment(@PathVariable Long lectureId, @PathVariable Long assignmentId) {
        return assignmentService.deleteAssignment(lectureId, assignmentId);
    }


    //과제 자세히보기
    @GetMapping("/lecture/{lectureId}/assignment/{assignmentId}")
    public ResponseDto findAssignment(@PathVariable Long lectureId, @PathVariable Long assignmentId) {
        return assignmentService.findAssignment(lectureId, assignmentId);
    }


    //과제 목록 조회
    @GetMapping("/lecture/{lectureId}/assignments")
    public ResponseDto findAssignmentList(@PathVariable Long lectureId) {
        return assignmentService.findAssignmentList(lectureId);
    }

}
