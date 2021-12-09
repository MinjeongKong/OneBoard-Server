package com.connect.oneboardserver.web.dto.understanding;

import com.connect.oneboardserver.domain.understanding.UnderstandStu;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnderstandStuResponseDto {

    private Long understandResponseId;

    public UnderstandStuResponseDto(UnderstandStu entity) {
        this.understandResponseId = entity.getId();
    }
}
