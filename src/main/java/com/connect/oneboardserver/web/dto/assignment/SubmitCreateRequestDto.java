package com.connect.oneboardserver.web.dto.assignment;

import com.connect.oneboardserver.domain.assignment.Submit;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SubmitCreateRequestDto {

    private String content;
    private String fileUrl;

    @Builder
    public SubmitCreateRequestDto(String content, String fileUrl) {
        this.content = content;
        this.fileUrl = fileUrl;
    }

    public Submit toEntity() {
        return Submit.builder()
                .content(content)
                .fileUrl(fileUrl)
                .build();
    }
}
