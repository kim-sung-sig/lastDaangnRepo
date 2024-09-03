package com.demo.daangn.domain.file.scheduler;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.demo.daangn.domain.file.entity.FileTempEntity;
import com.demo.daangn.domain.file.repository.FileTempRepository;
import com.demo.daangn.global.util.file.CustomFileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileCleanupTasklet implements Tasklet {

    private final Path tempRootLocation;
    private final FileTempRepository fileTempRepository;

    public FileCleanupTasklet(@Value("${custom.fileTempDirPath}") String tempDir, FileTempRepository fileTempRepository) {

        this.fileTempRepository = fileTempRepository;
        this.tempRootLocation = Paths.get(tempDir).toAbsolutePath().normalize();
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("===============================================");
        log.info("fileCleanupTasklet started");
        log.info("===============================================");
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        // 1. 1시간 이상 된 isUsed=0인 파일 목록 조회
        List<FileTempEntity> filesToDelete = fileTempRepository.findByIsUsedAndCreateDateBefore(0, oneHourAgo);
        log.debug("Files to delete: " + filesToDelete.size());
        List<String> randomKeys = filesToDelete.stream()
                .map(FileTempEntity::getRandomKey)
                .distinct()
                .toList();

        // 2. 파일 삭제 및 DB 삭제
        for (String randomKey : randomKeys) {
            try {
                Path randomKeyPath = tempRootLocation.resolve(randomKey).normalize();
                CustomFileUtil.deleteFiles(randomKeyPath);
                fileTempRepository.deleteByRandomKey(randomKey);
            } catch (IOException e) {
                log.error("Failed to delete files for random key: " + randomKey, e);
            } catch (DataAccessException e) {
                log.error("Failed to delete database entry for random key: " + randomKey, e);
            } catch (Exception e) {
                log.error("Unexpected error occurred while processing random key: " + randomKey, e);
            }
        }

        log.info("File cleanup tasklet finished");
        return RepeatStatus.FINISHED;
    }
}
