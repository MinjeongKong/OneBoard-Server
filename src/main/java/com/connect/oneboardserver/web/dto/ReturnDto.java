package com.connect.oneboardserver.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReturnDto {

    private String result;
    private Object data;

    public ReturnDto(String result) {
        this.result = result;
    }

    public ReturnDto(String result, Object data) {
        this.result = result;
        this.data = data;
    }

}
