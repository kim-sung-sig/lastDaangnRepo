package com.demo.daangn.app.service.temp.file.response;

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

    private List<FileStoreRs> files;

}
