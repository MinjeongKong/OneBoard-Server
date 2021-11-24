package com.connect.oneboardserver.web.dto.assignment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SubmitUpdateRequestDto {

    private String content;
    private String fileUrl;

    @Builder
    public SubmitUpdateRequestDto(String content, String fileUrl) {
        this.content = content;
        this.fileUrl = fileUrl;
    }
}
