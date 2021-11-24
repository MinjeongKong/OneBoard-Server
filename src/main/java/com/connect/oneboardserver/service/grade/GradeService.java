package com.connect.oneboardserver.service.grade;

import com.connect.oneboardserver.config.security.JwtTokenProvider;
import com.connect.oneboardserver.domain.assignment.Assignment;
import com.connect.oneboardserver.domain.assignment.AssignmentRepository;
import com.connect.oneboardserver.domain.assignment.Submit;
import com.connect.oneboardserver.domain.assignment.SubmitRepository;
import com.connect.oneboardserver.domain.attendance.Attendance;
import com.connect.oneboardserver.domain.attendance.AttendanceRepository;
import com.connect.oneboardserver.domain.grade.Grade;
import com.connect.oneboardserver.domain.grade.GradeRatio;
import com.connect.oneboardserver.domain.grade.GradeRatioRepository;
import com.connect.oneboardserver.domain.grade.GradeRepository;
import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.domain.login.MemberRepository;
import com.connect.oneboardserver.domain.relation.GradeRatioLectureRepository;
import com.connect.oneboardserver.domain.relation.MemberLecture;
import com.connect.oneboardserver.domain.relation.MemberLectureRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.attendance.AttendanceDto;
import com.connect.oneboardserver.web.dto.grade.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class GradeService {

    private final LectureRepository lectureRepository;
    private final AssignmentRepository assignmentRepository;
    private final SubmitRepository submitRepository;
    private final GradeRatioRepository gradeRatioRepository;
    private final GradeRatioLectureRepository gradeRatioLectureRepository;
    private final GradeRepository gradeRepository;
    private final MemberLectureRepository memberLectureRepository;
    private final LessonRepository lessonRepository;
    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Transactional
    public void deleteGrade(Long lectureId) {
        gradeRepository.deleteAllByLectureId(lectureId);
    }

    @Transactional
    public void init(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(()->new IllegalArgumentException("해당 과목이 없습니다. id="+lectureId));

        List<MemberLecture> studentList = memberLectureRepository.findAllByLectureIdAndMemberUserType(lectureId, "S");

        for (int s = 0; s < studentList.size(); s++) {
            Member student = studentList.get(s).getMember();

            Grade grade = gradeRepository.findByStudentIdAndLectureId(student.getId(), lectureId);
            if (grade == null) {
                gradeRepository.save(Grade.builder()
                        .lecture(lecture)
                        .student(student)
                        .submitScore(0f)
                        .attendScore(0f)
                        .totalScore(0f)
                        .build());
            } else {
                grade.init();
            }

        }

    }

    @Transactional
    public void updateGrade(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(()->new IllegalArgumentException("해당 과목이 없습니다. id="+lectureId));

        sumScore(lectureId, lecture);

        List<Grade> gradeList = gradeRepository.findAllByLectureIdOrderByTotalScoreDesc(lectureId);
        Long gradeRatioId = gradeRatioLectureRepository.findByLectureId(lectureId).getGradeRatio().getId();
        GradeRatio gradeRatio = gradeRatioRepository.getById(gradeRatioId);

        try {
            int length = gradeList.size();
            int cutA = length * gradeRatio.getAratio() / 100;
            int cutB = length * gradeRatio.getBratio() / 100;

            for (int i = 1; i <= length; i++) {
                Grade grade = gradeList.get(i - 1);
                if (grade.getChangeResult()==null) {
                    if (i <= cutB) {
                        if (i <= cutA) {
                            grade.setResult("A");
                        } else {
                            grade.setResult("B");
                        }
                    } else {
                        grade.setResult("C");
                    }
                }
            }
        } catch (Exception e) {

        }

    }

    @Transactional
    private void sumScore(Long lectureId, Lecture lecture) {
        List<MemberLecture> studentList = memberLectureRepository.findAllByLectureIdAndMemberUserType(lectureId, "S");
        List<Assignment> assignmentList = assignmentRepository.findAllByLectureId(lectureId);
        List<Lesson> lessonList = lessonRepository.findAllByLectureId(lectureId);

        for (int s = 0; s < studentList.size(); s++) {
            Float totalScore = 0f;
            Float attendScore = 0f;
            Float submitScore = 0f;
            Member student = studentList.get(s).getMember();
            Long studentId = student.getId();

            for (int a = 0; a < assignmentList.size(); a++) {
                Long assignmentId = assignmentList.get(a).getId();
                Submit submit = submitRepository.findByStudentIdAndAssignmentId(studentId, assignmentId);
                if (submit != null) {
                    if (submit.getScore() != null) {
                        submitScore += submit.getScore();
                    }
                }
            }

            for (int t = 0; t < lessonList.size(); t++) {
                Long lessonId = lessonList.get(t).getId();
                Attendance attendance = attendanceRepository.findByMemberIdAndLessonId(studentId, lessonId);
                try {
                    attendScore+=attendance.getStatus();
                } catch (NullPointerException e) {}
            }

            attendScore = attendScore*10/100;
            totalScore = attendScore+submitScore;

            Grade grade = gradeRepository.findByStudentIdAndLectureId(studentId, lectureId);
            grade.updateScore(submitScore, attendScore, totalScore);
        }
    }

    @Transactional
    private GradeFindResponseDto findGrade(Long lectureId, Member student, Lecture lecture) {

        Grade grade = gradeRepository.findByStudentIdAndLectureId(student.getId(), lectureId);

        List<Assignment> assignmentList = assignmentRepository.findAllByLectureId(lectureId);
        List<SubmitScoreResponseDto> submitScoreResponseDtoList = new ArrayList<>();

        for (Assignment assignment : assignmentList) {
            Submit submit = submitRepository.findByStudentIdAndAssignmentId(student.getId(), assignment.getId());
            if (submit != null) {
                SubmitScoreResponseDto submitScoreResponseDto = new SubmitScoreResponseDto(submit);
                submitScoreResponseDtoList.add(submitScoreResponseDto);
            }
        }

        List<Lesson> lessonList = lessonRepository.findAllByLectureId(lecture.getId());
        List<AttendanceDto> attendanceDtoList = new ArrayList<>();

        for(Lesson lesson : lessonList) {
            Attendance attendances = attendanceRepository.findByMemberIdAndLessonId(student.getId(), lesson.getId());
            if(attendances != null) {
                AttendanceDto attendanceDto = AttendanceDto.builder()
                        .lessonId(lesson.getId())
                        .lessonDate(lesson.getDate())
                        .status(attendances.getStatus())
                        .build();
                attendanceDtoList.add(attendanceDto);
            }
        }

        GradeFindResponseDto responseDto = new GradeFindResponseDto(grade, submitScoreResponseDtoList, attendanceDtoList);
        return responseDto;
    }

    @Transactional
    public ResponseDto findGradeStu(Long lectureId, ServletRequest request) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        Member student = (Member) userDetailsService.loadUserByUsername(jwtTokenProvider.getUserPk(token));

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(()->new IllegalArgumentException("해당 과목이 없습니다. id="+lectureId));

        MemberLecture memberLecture = memberLectureRepository.findByMemberIdAndLectureId(student.getId(), lectureId);
        if (memberLecture == null) {
            return new ResponseDto("FAIL");
        }

        updateGrade(lectureId);
        GradeFindResponseDto responseDto = findGrade(lectureId, student, lecture);

        if (responseDto == null) {
            return new ResponseDto("FAIL");
        } else {
            return new ResponseDto("SUCCESS", responseDto);
        }
    }

    @Transactional
    public ResponseDto findGradePro(Long lectureId, Long studentId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당 과목이 없습니다 : id = " + lectureId));

        Member student = memberRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생이 없습니다 : id = " + studentId));

        MemberLecture memberLecture = memberLectureRepository.findByMemberIdAndLectureId(studentId, lectureId);
        if (memberLecture == null) {
            return new ResponseDto("FAIL");
        }

        updateGrade(lectureId);
        GradeFindResponseDto responseDto = findGrade(lectureId, student, lecture);

        if (responseDto == null) {
            return new ResponseDto("FAIL");
        } else {
            return new ResponseDto("SUCCESS", responseDto);
        }

    }

    @Transactional
    public ResponseDto findGradeList(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당 과목이 없습니다 : id = " + lectureId));

        updateGrade(lectureId);

        List<MemberLecture> memberLectureList = memberLectureRepository.findAllByLectureId(lectureId);
        List<GradeFindListResponseDto> responseDtoList = new ArrayList<>();

        for (MemberLecture memberLecture : memberLectureList) {
            Grade grade = gradeRepository.findByStudentIdAndLectureId(memberLecture.getMember().getId(), lectureId);
            if (grade != null) {
                GradeFindListResponseDto responseDto = new GradeFindListResponseDto(grade);
                responseDtoList.add(responseDto);
            }
        }

        return new ResponseDto("SUCCESS", responseDtoList);

    }

    @Transactional
    public ResponseDto updateResult(Long lectureId, Long studentId, GradeUpdateRequestDto requestDto) {

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당 과목이 없습니다 : id = " + lectureId));

        Member student = memberRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생이 없습니다 : id = " + studentId));

        MemberLecture memberLecture = memberLectureRepository.findByMemberIdAndLectureId(studentId, lectureId);
        if (memberLecture == null) {
            return new ResponseDto("FAIL");
        }

        Grade grade = gradeRepository.findByStudentIdAndLectureId(studentId, lectureId);
        grade.setChangeResult(requestDto.getResult());
        grade.setResult(requestDto.getResult());

        return new ResponseDto("SUCCESS");

    }
}
