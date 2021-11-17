package com.connect.oneboardserver.domain.assignment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmitRepository extends JpaRepository<Submit, Long> {
    List<Submit> findAllByAssignmentId(Long assignmentId);
}
