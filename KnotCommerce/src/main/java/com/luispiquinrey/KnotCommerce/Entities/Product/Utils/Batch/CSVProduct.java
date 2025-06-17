package com.luispiquinrey.KnotCommerce.Entities.Product.Utils.Batch;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.luispiquinrey.KnotCommerce.Entities.Product.NoPerishableProduct;
import com.luispiquinrey.KnotCommerce.Entities.Product.PerishableProduct;

@Configuration
public class CSVProduct {

    private static final Logger logger = LoggerFactory.getLogger(CSVProduct.class);

    @Bean
    public FlatFileItemReader<NoPerishableProduct> noPerishableProductReader() {
        return new FlatFileItemReaderBuilder<NoPerishableProduct>()
            .name("noPerishableProductReader")
            .resource(new ClassPathResource("CSV/csvNoPerishableProduct/dataNoPerishable.csv"))
            .delimited()
            .delimiter(",")
            .names(
                "available",
                "id_Product",
                "name",
                "price",
                "description",
                "stock",
                "version",
                "categories",
                "warrantyPeriod"
            )
        .fieldSetMapper(new NoPerishableProductFieldMapper())
        .build();
}
    @Bean
    public FlatFileItemReader<PerishableProduct> perishableProductReader() {
        return new FlatFileItemReaderBuilder<PerishableProduct>()
            .name("perishableProductReader")
            .resource(new ClassPathResource("CSV/csvPerishableProduct/dataPerishable.csv"))
            .delimited()
            .delimiter(",")
            .names(
                "available",
                "id_Product",
                "name",
                "price",
                "description",
                "stock",
                "version",
                "categories",
                "expirationDate",
                "recommendedTemperature"
            )
            .fieldSetMapper(new PerishableProductFieldMapper())
            .build();
    }
    @Bean
    public JdbcBatchItemWriter<PerishableProduct> writerPerishable(DataSource dataSource) {
        JdbcBatchItemWriter<PerishableProduct> writer = new JdbcBatchItemWriter<>();
        writer.setSql("""
            INSERT INTO Product (
                product_type, id_product, available, description, name,
                price, stock, version, expiration_date, recommended_temperature
            ) VALUES (
                'PERISHABLE', :id_Product, :available, :description, :name,
                :price, :stock, :version, :expirationDate, :recommendedTemperature
            )
        """);
        writer.setDataSource(dataSource);
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }

    @Bean
    public JdbcBatchItemWriter<NoPerishableProduct> writer(DataSource dataSource) {
        JdbcBatchItemWriter<NoPerishableProduct> writerNoPerishable = new JdbcBatchItemWriter<>();
        writerNoPerishable.setSql("""
        INSERT INTO Product (
            product_type, id_product, available, description, name,
            price, stock, version, warranty_period
        ) VALUES (
            'NoPerishableProduct', :id_Product, :available, :description, :name,
            :price, :stock, :version, :warrantyPeriod
        )
    """);
        writerNoPerishable.setDataSource(dataSource);
        writerNoPerishable.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writerNoPerishable;
    }

    @Bean
    public Step sampleStepNoPerishable(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        FlatFileItemReader<NoPerishableProduct> noPerishableProductReader,
        JdbcBatchItemWriter<NoPerishableProduct> writer
    ) {
        return new StepBuilder("sampleStepNoPerishable", jobRepository)
            .<NoPerishableProduct, NoPerishableProduct>chunk(10, transactionManager)
            .reader(noPerishableProductReader)
            .writer(writer)
            .listener(new LoggingStepExecutionListener())
            .build();
    }
    @Bean
    public Step deleteCsvStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("deleteCsvStep", jobRepository)
            .tasklet(new DeleteCsvTasklet(), transactionManager)
            .allowStartIfComplete(true)
            .build();
    }
    @Bean
    public Step sampleStepPerishable(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        FlatFileItemReader<PerishableProduct> perishableProductReader,
        JdbcBatchItemWriter<PerishableProduct> writerPerishable
    ) {
        return new StepBuilder("sampleStepPerishable", jobRepository)
            .<PerishableProduct, PerishableProduct>chunk(10, transactionManager)
            .reader(perishableProductReader)
            .writer(writerPerishable)
            .build();
    }
    @Bean
    public Job sampleJob(JobRepository jobRepository, Step sampleStepNoPerishable,Step sampleStepPerishable,Step deleteCsvStep) {
        return new JobBuilder("sampleJob", jobRepository)
                .start(sampleStepNoPerishable)
                .next(sampleStepPerishable)
                .next(deleteCsvStep)
                .build();
    }
}
