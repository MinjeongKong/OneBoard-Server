package com.connect.oneboardserver.service.assignment;

import com.connect.oneboardserver.config.security.JwtTokenProvider;
import com.connect.oneboardserver.domain.assignment.Assignment;
import com.connect.oneboardserver.domain.assignment.AssignmentRepository;
import com.connect.oneboardserver.domain.assignment.Submit;
import com.connect.oneboardserver.domain.assignment.SubmitRepository;
import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.assignment.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubmitService {

    private final AssignmentRepository assignmentRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final SubmitRepository submitRepository;

    @Transactional
    public ResponseDto createSubmit(Long lectureId, Long assignmentId, ServletRequest request, SubmitCreateRequestDto requestDto) {

        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        Member student = (Member) userDetailsService.loadUserByUsername(jwtTokenProvider.getUserPk(token));


        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다. id="+assignmentId));

        if (!assignment.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            Submit submit = requestDto.toEntity();
            submit.setStudent(student);
            submit.setAssignment(assignment);

            submitRepository.save(submit);
            SubmitResponseDto responseDto = new SubmitResponseDto(submit);

            return new ResponseDto("SUCCESS", responseDto);
        }

    }

    @Transactional
    public ResponseDto checkSubmit(Long lectureId, Long assignmentId, Long submitId, SubmitCheckRequestDto requestDto) {

        Submit submit = submitRepository.findById(submitId)
                .orElseThrow(()->new IllegalArgumentException("해당 제출물이 없습니다. id="+submitId));

        if (!(submit.getAssignment().getId().equals(assignmentId)&&submit.getAssignment().getLecture().getId().equals(lectureId))) {
            return new ResponseDto("FAIL");
        } else {
            if(!(submit.getAssignment().getScore()>=requestDto.getScore()))
                return new ResponseDto("FAIL");

            submit.check(requestDto.getScore(), requestDto.getFeedback());
            SubmitResponseDto responseDto = new SubmitResponseDto(submit);

            return new ResponseDto("SUCCESS", responseDto);
        }
    }

    public ResponseDto findSubmit(Long lectureId, Long assignmentId, Long submitId) {
        Submit submit = submitRepository.findById(submitId)
                .orElseThrow(()->new IllegalArgumentException("해당 제출물이 없습니다. id="+submitId));

        if (!(submit.getAssignment().getId().equals(assignmentId)&&submit.getAssignment().getLecture().getId().equals(lectureId))) {
            return new ResponseDto("FAIL");
        } else {

            SubmitFindResponseDto responseDto = new SubmitFindResponseDto(submit);
            return new ResponseDto("SUCCESS", responseDto);
        }
    }

    public ResponseDto findSubmitList(Long lectureId, Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다. id="+assignmentId));

        if (!assignment.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            List<Submit> submitList = submitRepository.findAllByAssignmentId(assignmentId);
            List<SubmitFindResponseDto> submitFindResponseDtoList = new ArrayList<>();
            for (int i = 0; i < submitList.size(); i++) {
                submitFindResponseDtoList.add(SubmitFindResponseDto.toResponseDto(submitList.get(i)));
            }

            return new ResponseDto("SUCCESS", submitFindResponseDtoList);
        }
    }


    public ResponseDto findSubmitStu(Long lectureId, Long assignmentId, ServletRequest request) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        Member student = (Member) userDetailsService.loadUserByUsername(jwtTokenProvider.getUserPk(token));

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다. id="+assignmentId));

        if (!assignment.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            Submit submit = submitRepository.findByStudentIdAndAssignmentId(student.getId(), assignmentId);
            SubmitFindResponseDto responseDto = new SubmitFindResponseDto(submit);
            return new ResponseDto("SUCCESS", responseDto);
        }
    }

    public ResponseDto updateSubmit(Long lectureId, Long assignmentId, ServletRequest request, SubmitUpdateRequestDto requestDto) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        Member student = (Member) userDetailsService.loadUserByUsername(jwtTokenProvider.getUserPk(token));

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다. id="+assignmentId));

        if (!assignment.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            Submit submit = submitRepository.findByStudentIdAndAssignmentId(student.getId(), assignmentId);
            submit.update(requestDto.getContent(), requestDto.getFileUrl());
            SubmitResponseDto responseDto = new SubmitResponseDto(submit);
            return new ResponseDto("SUCCESS", responseDto);
        }
    }
}
