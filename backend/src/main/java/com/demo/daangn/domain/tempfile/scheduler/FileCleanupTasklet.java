package com.demo.daangn.domain.tempfile.scheduler;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.demo.daangn.domain.tempfile.entity.TempFile;
import com.demo.daangn.domain.tempfile.repository.TempFileRepository;
import com.demo.daangn.global.util.file.CustomFileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileCleanupTasklet implements Tasklet {

    private final Path tempRootLocation;
    private final TempFileRepository tempFileRepository;

    public FileCleanupTasklet(@Value("${custom.fileTempDirPath}") String tempDir, TempFileRepository tempFileRepository) {
        this.tempFileRepository = tempFileRepository;
        this.tempRootLocation = Paths.get(tempDir).toAbsolutePath().normalize();
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("************************************");
        log.info("** fileCleanupTasklet started");
        log.info("************************************");

        // 1. 1시간 이상 된 isUsed=0인 파일 목록 조회
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        List<TempFile> tempFiles = tempFileRepository.findByCreateDateBefore(oneHourAgo);
        List<UUID> uuids = tempFiles.stream().map(TempFile::getId).toList();

        log.debug("delete file size = " + uuids.size());

        // 2. 파일 삭제
        List<UUID> deletedUuids = new ArrayList<>();
        for (UUID uuid : uuids) {
            try {
                log.debug("Processing uuid: " + uuid);
                Path deleteFilePath = tempRootLocation.resolve(uuid.toString()).normalize();
                CustomFileUtil.deleteFiles(deleteFilePath);
                deletedUuids.add(uuid);
            } catch (Exception e) {
                log.error("Unexpected error occurred while processing uuid key: " + uuid, e);
            }
        }

        // 3. 삭제된 파일 목록 DB에서 삭제
        // using batch size 1000
        int batchSize = 1000;
        for (int i = 0; i < deletedUuids.size(); i += batchSize) {
            int toIndex = Math.min(i + batchSize, deletedUuids.size());
            List<UUID> subList = deletedUuids.subList(i, toIndex);
            tempFileRepository.deleteByIdIn(subList);
        }


        log.info("************************************");
        log.info("** File cleanup tasklet finished");
        log.info("************************************");
        return RepeatStatus.FINISHED;
    }
}
