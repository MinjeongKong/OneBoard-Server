package com.connect.oneboardserver.web.dto.understanding;

import com.connect.oneboardserver.domain.understanding.UnderstandPro;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnderstandProResponseDto {

    private Long understandId;

    public UnderstandProResponseDto(UnderstandPro entity) {
        this.understandId = entity.getId();
    }
}
