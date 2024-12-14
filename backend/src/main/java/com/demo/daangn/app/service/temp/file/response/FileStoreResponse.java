package com.demo.daangn.app.service.temp.file.response;

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
