package com.demo.daangn.domain.file.repository;

import java.time.LocalDateTime;
import java.util.List;

public interface FileTempRepositoryCustom {

    List<String> findDeleteRandomKeys(Integer isUsed, LocalDateTime createDate);
}
