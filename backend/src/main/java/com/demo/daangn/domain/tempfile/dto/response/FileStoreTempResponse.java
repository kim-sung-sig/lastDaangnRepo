package com.demo.daangn.domain.tempfile.dto.response;

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
