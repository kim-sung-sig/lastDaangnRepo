package com.demo.daangn.global.util.file.dto.response;

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
