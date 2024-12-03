package com.demo.daangn.domain.tempfile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class FileStoreResponse {
    private String fileName;
    private String saveFileName;
}
