package com.connect.oneboardserver.service.assignment;

import com.connect.oneboardserver.domain.assignment.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;


}
