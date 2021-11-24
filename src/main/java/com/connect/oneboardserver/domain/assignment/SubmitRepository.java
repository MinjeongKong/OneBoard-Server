package com.connect.oneboardserver.domain.assignment;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface SubmitRepository extends JpaRepository<Submit, Long> {
    List<Submit> findAllByAssignmentId(Long assignmentId);

    Submit findByStudentIdAndAssignmentId(Long studentId, Long assignmentId);

    @Transactional
    void deleteAllByAssignmentId(Long assignmentId);
}
