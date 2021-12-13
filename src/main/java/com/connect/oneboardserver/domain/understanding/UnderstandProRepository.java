package com.connect.oneboardserver.domain.understanding;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnderstandProRepository extends JpaRepository<UnderstandPro, Long> {
    List<UnderstandPro> findAllByLessonId(Long lessonId);
}
