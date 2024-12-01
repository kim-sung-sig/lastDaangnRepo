package com.demo.daangn.domain.file.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
// @EnableScheduling
public class FileCleanupJobScheduler {

    private final JobLauncher jobLauncher;

    @Qualifier("fileCleanupJob")
    private final Job fileCleanupJob;

    // @Scheduled(cron = "0 */5 * * * ?") // 테스트용 5분 마다 실행
    // @Scheduled(cron = "0 0 */2 * * ?") // 매시간 정각에 실행
    public void runFileCleanupJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addString("JobID", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();
            jobLauncher.run(fileCleanupJob, params);
        } catch (Exception e) {
            log.error("Failed to run file cleanup job.", e);
        }
    }
}

