package com.connect.oneboardserver.web.dto.assignment;

import com.connect.oneboardserver.domain.assignment.Assignment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AssignmentListFindResponseDto {

    private List<Assignment> assignmentList;

    public AssignmentListFindResponseDto(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }
}
