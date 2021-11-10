package com.connect.oneboardserver.web.dto.assignment;

import com.connect.oneboardserver.domain.assignment.Assignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AssignmentFindResponseDto {

    private Assignment assignment;

    public AssignmentFindResponseDto(Assignment entity) {
        this.assignment = entity;
    }

}
