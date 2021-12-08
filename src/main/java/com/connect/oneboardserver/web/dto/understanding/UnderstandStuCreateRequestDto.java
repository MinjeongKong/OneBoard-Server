package com.connect.oneboardserver.web.dto.understanding;

import com.connect.oneboardserver.domain.understanding.UnderstandStu;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnderstandStuCreateRequestDto {

    private Integer response; //0:X, 1:O

    @Builder
    public UnderstandStuCreateRequestDto(Integer response) {
        this.response = response;
    }

    public UnderstandStu toEntity() {
        return UnderstandStu.builder()
                .response(response).build();
    }
}
