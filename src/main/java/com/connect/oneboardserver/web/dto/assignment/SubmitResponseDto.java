package com.connect.oneboardserver.web.dto.assignment;

import com.connect.oneboardserver.domain.assignment.Submit;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmitResponseDto {

    private Long submitId;

    public SubmitResponseDto(Submit entity) {
        this.submitId = entity.getId();
    }
}
