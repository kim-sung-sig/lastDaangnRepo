package com.demo.daangn.domain.file.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class FileCleanupBatchConfig {

    private final JobRepository jobRepository;
    private final FileCleanupTasklet fileCleanupTasklet;
    private final PlatformTransactionManager transactionManager;

    private Long startTime;

    @Bean("fileCleanupTimeListener")
    JobExecutionListener fileCleanupTimeListener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) { // null 은 안뜸
                startTime = System.currentTimeMillis();
                log.info("-----------------------------------------------");
                log.info("-File cleanup job started");
                log.info("-----------------------------------------------");
                log.info("File cleanup job started at: " + startTime);
            }

            @Override
            public void afterJob(JobExecution jobExecution) { // null 은 안뜸
                long endTime = System.currentTimeMillis();
                log.info("File cleanup job finished at: " + endTime);
                log.info("File cleanup job took: " + (endTime - startTime) + "ms");
            }
        };
    }

    @Bean("fileCleanupJob")
    Job fileCleanupJob() {
        return new JobBuilder("fileCleanupJob", jobRepository)
                .start(fileCleanupStep())
                .listener(fileCleanupTimeListener())
                .build();
    }

    @Bean("fileCleanupStep")
    Step fileCleanupStep() {
        return new StepBuilder("fileCleanupStep", jobRepository)
                .tasklet(fileCleanupTasklet, transactionManager)
                .listener(fileCleanupTimeListener())
                .build();
    }
}
