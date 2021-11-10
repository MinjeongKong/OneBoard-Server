package com.connect.oneboardserver.web.dto.assignment;

import com.connect.oneboardserver.domain.assignment.Submit;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmitFindResponseDto {

    private Submit submit;

    public SubmitFindResponseDto(Submit entity) {
        this.submit = entity;
    }
}
