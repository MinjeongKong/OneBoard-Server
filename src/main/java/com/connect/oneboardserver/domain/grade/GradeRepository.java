package com.connect.oneboardserver.domain.grade;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByLectureId(Long lectureId);

    List<Grade> findAllByLectureIdOrderByTotalScoreDesc(Long lectureId);

    Grade findByStudentIdAndLectureId(Long studentId, Long lectureId);

    @Transactional
    void deleteAllByLectureId(Long lectureId);
}
