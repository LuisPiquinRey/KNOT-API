package com.luispiquinrey.KnotCommerce.Entities.Product.Utils.Batch;

import java.nio.file.Path;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.lang.Nullable;

public class DeleteCsvTasklet implements Tasklet{
    @Override
    @Nullable
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        boolean deleted1 = Path.of("CSV/csvNoPerishableProduct/dataNoPerishable.csv").toFile().delete();
        boolean deleted2 = Path.of("CSV/csvPerishableProduct/dataPerishable.csv").toFile().delete();
        if (deleted1) System.out.println("NoPerishable file deleted");
        if (deleted2) System.out.println("Perishable file deleted");
        return RepeatStatus.FINISHED;
    }
}
