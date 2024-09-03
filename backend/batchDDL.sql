-- Drop existing tables if they exist
DROP TABLE IF EXISTS batch_step_execution_seq;
DROP TABLE IF EXISTS batch_job_execution_seq;
DROP TABLE IF EXISTS batch_job_seq;
DROP TABLE IF EXISTS batch_job_instance;
DROP TABLE IF EXISTS batch_job_execution;
DROP TABLE IF EXISTS batch_job_execution_context;
DROP TABLE IF EXISTS batch_step_execution;
DROP TABLE IF EXISTS batch_step_execution_context;
DROP TABLE IF EXISTS batch_job_execution_params;

-- Create sequence tables
CREATE TABLE batch_step_execution_seq (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
) ENGINE=InnoDB;
INSERT INTO batch_step_execution_seq (id) VALUES (0);

CREATE TABLE batch_job_execution_seq (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
) ENGINE=InnoDB;
INSERT INTO batch_job_execution_seq (id) VALUES (0);

CREATE TABLE batch_job_seq (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
) ENGINE=InnoDB;
INSERT INTO batch_job_seq (id) VALUES (0);

-- Create batch job instance table
CREATE TABLE batch_job_instance (
    job_instance_id BIGINT NOT NULL AUTO_INCREMENT,
    job_name VARCHAR(100) NOT NULL,
    job_key VARCHAR(32) NOT NULL,
    version BIGINT DEFAULT 0 NOT NULL,
    PRIMARY KEY (job_instance_id)
) ENGINE=InnoDB;

-- Create batch job execution table
CREATE TABLE batch_job_execution (
    job_execution_id BIGINT NOT NULL AUTO_INCREMENT,
    job_instance_id BIGINT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    status VARCHAR(10),
    exit_code VARCHAR(250),
    exit_message VARCHAR(4000),
    version BIGINT DEFAULT 0 NOT NULL,
    last_updated TIMESTAMP,
    PRIMARY KEY (job_execution_id)
) ENGINE=InnoDB;

-- Create batch job execution context table
CREATE TABLE batch_job_execution_context (
    job_execution_id BIGINT NOT NULL,
    short_context VARCHAR(250),
    serialized_context LONGTEXT,
    PRIMARY KEY (job_execution_id)
) ENGINE=InnoDB;

-- Create batch step execution table
CREATE TABLE batch_step_execution (
    step_execution_id BIGINT NOT NULL AUTO_INCREMENT,
    job_execution_id BIGINT NOT NULL,
    step_name VARCHAR(100) NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    status VARCHAR(10),
    commit_count BIGINT,
    read_count BIGINT,
    filter_count BIGINT,
    write_count BIGINT,
    read_skip_count BIGINT,
    write_skip_count BIGINT,
    process_skip_count BIGINT,
    rollback_count BIGINT,
    exit_code VARCHAR(250),
    exit_message VARCHAR(4000),
    version BIGINT DEFAULT 0 NOT NULL,
    last_updated TIMESTAMP,
    PRIMARY KEY (step_execution_id)
) ENGINE=InnoDB;

-- Create batch step execution context table
CREATE TABLE batch_step_execution_context (
    step_execution_id BIGINT NOT NULL,
    short_context VARCHAR(4000),
    serialized_context LONGTEXT,
    PRIMARY KEY (step_execution_id)
) ENGINE=InnoDB;

-- Create batch job execution params table
CREATE TABLE batch_job_execution_params (
    job_execution_id BIGINT NOT NULL,
    parameter_name VARCHAR(100) NOT NULL,
    parameter_type VARCHAR(100) NOT NULL,
    parameter_value VARCHAR(250),
    identifying CHAR(1) DEFAULT 'N',
    PRIMARY KEY (job_execution_id, parameter_name)
) ENGINE=InnoDB;

-- Add indexes
CREATE INDEX idx_job_name ON batch_job_instance (job_name);
CREATE INDEX idx_job_key ON batch_job_instance (job_key);
CREATE INDEX idx_job_instance_id ON batch_job_execution (job_instance_id);
CREATE INDEX idx_job_execution_id ON batch_job_execution_context (job_execution_id);
CREATE INDEX idx_step_execution_id ON batch_step_execution_context (step_execution_id);
CREATE INDEX idx_job_execution_id_param ON batch_job_execution_params (job_execution_id);
