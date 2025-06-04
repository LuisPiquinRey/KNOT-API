package com.luispiquinrey.KnotCommerce.Entities.Product.Utils.Batch;

import java.text.SimpleDateFormat;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.lang.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingStepExecutionListener implements StepExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(LoggingStepExecutionListener.class);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    @Nullable
    public ExitStatus afterStep(StepExecution stepExecution) {
        String formattedStartTime = "N/A";

        if (stepExecution.getStartTime() != null) {
            formattedStartTime = formatter.format(stepExecution.getStartTime());
        }

        log.info("🟢 Step '{}' started at: {} ⏰",
                stepExecution.getStepName(),
                formattedStartTime);

        return stepExecution.getExitStatus();
    }
}
