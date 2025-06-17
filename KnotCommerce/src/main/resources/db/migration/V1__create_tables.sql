CREATE TABLE `batch_job_instance` (
    `JOB_INSTANCE_ID` bigint NOT NULL,
    `VERSION` bigint DEFAULT NULL,
    `JOB_NAME` varchar(100) NOT NULL,
    `JOB_KEY` varchar(32) NOT NULL,
    PRIMARY KEY (`JOB_INSTANCE_ID`),
    UNIQUE KEY `JOB_INST_UN` (`JOB_NAME`,`JOB_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `batch_job_execution` (
    `JOB_EXECUTION_ID` bigint NOT NULL,
    `VERSION` bigint DEFAULT NULL,
    `JOB_INSTANCE_ID` bigint NOT NULL,
    `CREATE_TIME` datetime(6) NOT NULL,
    `START_TIME` datetime(6) DEFAULT NULL,
    `END_TIME` datetime(6) DEFAULT NULL,
    `STATUS` varchar(10) DEFAULT NULL,
    `EXIT_CODE` varchar(2500) DEFAULT NULL,
    `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
    `LAST_UPDATED` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`JOB_EXECUTION_ID`),
    KEY `JOB_INST_EXEC_FK` (`JOB_INSTANCE_ID`),
    CONSTRAINT `JOB_INST_EXEC_FK` FOREIGN KEY (`JOB_INSTANCE_ID`) REFERENCES `batch_job_instance` (`JOB_INSTANCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `batch_job_execution_context` (
    `JOB_EXECUTION_ID` bigint NOT NULL,
    `SHORT_CONTEXT` varchar(2500) NOT NULL,
    `SERIALIZED_CONTEXT` text,
    PRIMARY KEY (`JOB_EXECUTION_ID`),
    CONSTRAINT `JOB_EXEC_CTX_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `batch_job_execution` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `batch_job_execution_params` (
    `JOB_EXECUTION_ID` bigint NOT NULL,
    `PARAMETER_NAME` varchar(100) NOT NULL,
    `PARAMETER_TYPE` varchar(100) NOT NULL,
    `PARAMETER_VALUE` varchar(2500) DEFAULT NULL,
    `IDENTIFYING` char(1) NOT NULL,
    KEY `JOB_EXEC_PARAMS_FK` (`JOB_EXECUTION_ID`),
    CONSTRAINT `JOB_EXEC_PARAMS_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `batch_job_execution` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `batch_job_execution_seq` (
    `ID` bigint NOT NULL,
    `UNIQUE_KEY` char(1) NOT NULL,
    UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `batch_job_seq` (
    `ID` bigint NOT NULL,
    `UNIQUE_KEY` char(1) NOT NULL,
    UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `batch_step_execution` (
    `STEP_EXECUTION_ID` bigint NOT NULL,
    `VERSION` bigint NOT NULL,
    `STEP_NAME` varchar(100) NOT NULL,
    `JOB_EXECUTION_ID` bigint NOT NULL,
    `CREATE_TIME` datetime(6) NOT NULL,
    `START_TIME` datetime(6) DEFAULT NULL,
    `END_TIME` datetime(6) DEFAULT NULL,
    `STATUS` varchar(10) DEFAULT NULL,
    `COMMIT_COUNT` bigint DEFAULT NULL,
    `READ_COUNT` bigint DEFAULT NULL,
    `FILTER_COUNT` bigint DEFAULT NULL,
    `WRITE_COUNT` bigint DEFAULT NULL,
    `READ_SKIP_COUNT` bigint DEFAULT NULL,
    `WRITE_SKIP_COUNT` bigint DEFAULT NULL,
    `PROCESS_SKIP_COUNT` bigint DEFAULT NULL,
    `ROLLBACK_COUNT` bigint DEFAULT NULL,
    `EXIT_CODE` varchar(2500) DEFAULT NULL,
    `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
    `LAST_UPDATED` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`STEP_EXECUTION_ID`),
    KEY `JOB_EXEC_STEP_FK` (`JOB_EXECUTION_ID`),
    CONSTRAINT `JOB_EXEC_STEP_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `batch_job_execution` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `batch_step_execution_context` (
    `STEP_EXECUTION_ID` bigint NOT NULL,
    `SHORT_CONTEXT` varchar(2500) NOT NULL,
    `SERIALIZED_CONTEXT` text,
    PRIMARY KEY (`STEP_EXECUTION_ID`),
    CONSTRAINT `STEP_EXEC_CTX_FK` FOREIGN KEY (`STEP_EXECUTION_ID`) REFERENCES `batch_step_execution` (`STEP_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `batch_step_execution_seq` (
    `ID` bigint NOT NULL,
    `UNIQUE_KEY` char(1) NOT NULL,
    UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `category` (
    `id_category` int NOT NULL AUTO_INCREMENT,
    `description` varchar(100) NOT NULL,
    `name` varchar(50) NOT NULL,
    PRIMARY KEY (`id_category`),
    UNIQUE KEY `UK3x7l6yk1oxdxmdh4am3yq2y38` (`description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product` (
    `product_type` varchar(31) NOT NULL,
    `id_product` bigint NOT NULL AUTO_INCREMENT,
    `available` bit(1) DEFAULT NULL,
    `description` varchar(100) DEFAULT NULL,
    `name` varchar(20) DEFAULT NULL,
    `price` double DEFAULT NULL,
    `stock` int DEFAULT NULL,
    `version` int DEFAULT NULL,
    `warranty_period` varchar(20) DEFAULT NULL,
    `expiration_date` date DEFAULT NULL,
    `recommended_temperature` float DEFAULT NULL,
    PRIMARY KEY (`id_product`),
    UNIQUE KEY `UKq2n3melweyrl5d4rqkg7pq6ra` (`description`),
    CONSTRAINT `product_chk_1` CHECK ((`stock` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=10067000002 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product_category` (
    `id_product` bigint NOT NULL,
    `id_category` int NOT NULL,
    KEY `FK4y2ucymplqxycf58myn6abcv2` (`id_category`),
    KEY `FKt4sn9fs5ju7d8mcoporlyhfun` (`id_product`),
    CONSTRAINT `FK4y2ucymplqxycf58myn6abcv2` FOREIGN KEY (`id_category`) REFERENCES `category` (`id_category`),
    CONSTRAINT `FKt4sn9fs5ju7d8mcoporlyhfun` FOREIGN KEY (`id_product`) REFERENCES `product` (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;