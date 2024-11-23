package com.demo.daangn.domain.file.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileStoreTempResponse {

    private String randomKey;

    private List<Long> fileIds;

    private List<String> fileUrls;

}
