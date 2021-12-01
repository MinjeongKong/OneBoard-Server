package com.connect.oneboardserver.service.assignment;

import com.connect.oneboardserver.config.security.JwtTokenProvider;
import com.connect.oneboardserver.domain.assignment.Assignment;
import com.connect.oneboardserver.domain.assignment.AssignmentRepository;
import com.connect.oneboardserver.domain.assignment.Submit;
import com.connect.oneboardserver.domain.assignment.SubmitRepository;
import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.service.storage.StorageService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.assignment.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubmitService {

    @Qualifier("FileStorageService")
    private final StorageService storageService;
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
    public ResponseDto createSubmitFile(Long lectureId, Long assignmentId, ServletRequest request,
                                        SubmitCreateRequestDto requestDto, MultipartFile file) throws Exception{
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

            String uploadedFile = null;
            if (file != null) {
                String path = "/lecture_" + lectureId + "/assignment_" + assignment.getId() + "/submit_" + student.getStudentNumber();
                uploadedFile = storageService.store(path, file);
                submit.setFileUrl(uploadedFile);

                String loadUrl = "/lecture/" + lectureId + "/assignment/" + assignment.getId() + "/submit/" + submit.getId() + "/file";
                submit.setLoadUrl(loadUrl);
            }

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

    @Transactional
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

    @Transactional
    public ResponseDto updateSubmitFile(Long lectureId, Long assignmentId, ServletRequest request,
                                        SubmitUpdateRequestDto requestDto, MultipartFile file) throws Exception {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        Member student = (Member) userDetailsService.loadUserByUsername(jwtTokenProvider.getUserPk(token));

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다. id="+assignmentId));

        if (!assignment.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            Submit submit = submitRepository.findByStudentIdAndAssignmentId(student.getId(), assignmentId);

            if (submit.getFileUrl() != null) {
                if (storageService.delete(submit.getFileUrl())) {
                    submit.setFileUrl(null);
                }
            }
            submit.update(requestDto.getContent(), requestDto.getFileUrl());

            if (file != null) {
                String path = "/lecture_" + lectureId + "/assignment_" + assignment.getId() + "/submit_" + student.getStudentNumber();
                String uploadedFile = storageService.store(path, file);
                submit.setFileUrl(uploadedFile);

                String loadUrl = "/lecture/" + lectureId + "/assignment/" + assignment.getId() + "/submit/" + submit.getId() + "/file";
                submit.setLoadUrl(loadUrl);
            } else {
                submit.setLoadUrl(null);
            }
            SubmitResponseDto responseDto = new SubmitResponseDto(submit);
            return new ResponseDto("SUCCESS", responseDto);
        }
    }

    public ResponseEntity<Resource> loadSubmitFile(Long lectureId, Long assignmentId, Long submitId) throws Exception{
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new IllegalArgumentException("해당 과제가 없습니다. id="+assignmentId));

        Submit submit = submitRepository.findById(submitId)
                .orElseThrow(()->new IllegalArgumentException("해당 제출물이 없습니다. id="+submitId));

        if (!(submit.getAssignment().getId().equals(assignmentId)&&submit.getAssignment().getLecture().getId().equals(lectureId))) {
            throw new Exception("Wrong api path");
        }
        Resource resource = null;
        try {
            String filePath = submit.getFileUrl();
            resource = storageService.load(filePath);
            String contentDisposition = "attachment; filename=\"" + assignment.getTitle() + "_" +
                    submit.getStudent().getStudentNumber() + filePath.substring(filePath.lastIndexOf(".")) + "\"";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Fail to load submit file : submitId = " + submitId);
        }
    }
}
