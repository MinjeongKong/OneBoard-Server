package com.connect.oneboardserver.domain.quiz;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizProRepository extends JpaRepository<QuizPro, Long> {
    List<QuizPro> findAllByLessonId(Long lessonId);
}
