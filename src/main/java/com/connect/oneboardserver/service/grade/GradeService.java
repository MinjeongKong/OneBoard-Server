package com.connect.oneboardserver.service.grade;

import com.connect.oneboardserver.domain.assignment.Assignment;
import com.connect.oneboardserver.domain.assignment.AssignmentRepository;
import com.connect.oneboardserver.domain.assignment.Submit;
import com.connect.oneboardserver.domain.assignment.SubmitRepository;
import com.connect.oneboardserver.domain.grade.Grade;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void createGrade(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(()->new IllegalArgumentException("해당 과목이 없습니다. id="+lectureId));

        //lecture의 모든 student 찾기
        //lecture의 모든 assignment 찾기 -> 각 assignment 의 해당 student submit 찾아 sum
        //lecture의 모든 lesson 찾기 -> 각 lesson 의 해당 student attendance 찾아 sum

        List<MemberLecture> studentList = memberLectureRepository.findAllByLectureIdAndMemberUserType(lectureId, "S");
        List<Assignment> assignmentList = assignmentRepository.findAllByLectureId(lectureId);
        List<Lesson> lessonList = lessonRepository.findAllByLectureId(lectureId);

        for (int s = 0; s < studentList.size(); s++) {
            Float score = 0f;
            Member student = studentList.get(s).getMember();
            Long studentId = student.getId();
            for (int a = 0; a < assignmentList.size(); a++) {
                Long assignmentId = assignmentList.get(a).getId();
                Submit submit = submitRepository.findByStudentIdAndAssignmentId(studentId, assignmentId);
                score+=submit.getScore();
            }

            gradeRepository.save(Grade.builder()
                    .lecture(lecture)
                    .student(student)
                    .score(score)
                    .build());
        }

    }

}
