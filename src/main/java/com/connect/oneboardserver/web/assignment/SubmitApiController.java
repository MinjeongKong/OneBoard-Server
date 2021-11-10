package com.connect.oneboardserver.web.assignment;

import com.connect.oneboardserver.service.assignment.SubmitService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.assignment.SubmitCheckRequestDto;
import com.connect.oneboardserver.web.dto.assignment.SubmitCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class SubmitApiController {

    private final SubmitService submitService;

    //과제 제출
    @PostMapping("/lecture/{lectureId}/assignment/{assignmentId}/submit/user/{userId}")
    public ResponseDto createSubmit(@PathVariable Long lectureId, @PathVariable Long assignmentId,
                                    @PathVariable Long userId, @RequestBody SubmitCreateRequestDto requestDto) {
        return submitService.createSubmit(lectureId, assignmentId, userId, requestDto);
    }

    //과제 피드백 입력
    @PostMapping("/lecture/{lectureId}/assignment/{assignmentId}/submit/{submitId}")
    public ResponseDto checkSubmit(@PathVariable Long lectureId, @PathVariable Long assignmentId,
                                   @PathVariable Long submitId, @RequestBody SubmitCheckRequestDto requestDto) {
        return submitService.checkSubmit(lectureId, assignmentId, submitId, requestDto);
    }

    //과제 피드백 확인
    @GetMapping("/lecture/{lectureId}/assignment/{assignmentId}/submit/{submitId}")
    public ResponseDto findSubmit(@PathVariable Long lectureId, @PathVariable Long assignmentId,
                                  @PathVariable Long submitId) {
        return submitService.findSubmit(lectureId, assignmentId, submitId);
    }
}
