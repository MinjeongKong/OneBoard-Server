package com.connect.oneboardserver.domain.quiz;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizStuRepository extends JpaRepository<QuizStu, Long> {
    List<QuizStu> findAllByQuizProId(Long quizProId);

    QuizStu findByStudentIdAndQuizProId(Long studentId, Long quizProId);
}
