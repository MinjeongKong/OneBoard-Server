package com.connect.oneboardserver.web.dto.assignment;

import com.connect.oneboardserver.domain.assignment.Assignment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssignmentResponseDto {

    private Long assignmentId;

    public AssignmentResponseDto(Assignment entity) {
        this.assignmentId = entity.getId();
    }
}
