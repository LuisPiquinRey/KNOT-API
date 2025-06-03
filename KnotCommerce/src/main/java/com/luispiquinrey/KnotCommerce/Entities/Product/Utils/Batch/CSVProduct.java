package com.luispiquinrey.KnotCommerce.Entities.Product.Utils.Batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.luispiquinrey.KnotCommerce.Entities.Product.NoPerishableProduct;
import com.luispiquinrey.KnotCommerce.Entities.Product.PerishableProduct;

@Configuration
public class CSVProduct {

    @Bean
    public FlatFileItemReader<NoPerishableProduct> noPerishableProductReader() {
        return new FlatFileItemReaderBuilder<NoPerishableProduct>()
            .name("noPerishableProductReader")
            .resource(new ClassPathResource("CSV/csvNoPerishableProduct/dataNoPerishable.csv"))
            .fixedLength()
            .columns(
                new Range(1, 5),
                new Range(6, 15),
                new Range(16, 35),
                new Range(36, 45),
                new Range(46, 95),
                new Range(96, 100),
                new Range(101, 105),
                new Range(106, 125),
                new Range(126, 145)
            )
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
            .fixedLength()
            .columns(
                new Range(1, 5),
                new Range(6, 15),
                new Range(16, 35),
                new Range(36, 45),
                new Range(46, 95),
                new Range(96, 100),
                new Range(101, 105),
                new Range(106, 125),
                new Range(126, 145),
                new Range(146, 155)
            )
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
    public JdbcBatchItemWriter<NoPerishableProduct> writer(DataSource dataSource) {
        JdbcBatchItemWriter<NoPerishableProduct> writer = new JdbcBatchItemWriter<>();
        writer.setSql("""
            INSERT INTO Product (
                available, id_Product, name, price, description, stock, version, warrantyPeriod
            ) VALUES (
                :available, :id_Product, :name, :price, :description, :stock, :version, :warrantyPeriod
            )
        """);
        writer.setDataSource(dataSource);
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }

    @Bean
    public Step sampleStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        FlatFileItemReader<NoPerishableProduct> noPerishableProductReader,
        JdbcBatchItemWriter<NoPerishableProduct> writer
    ) {
        return new StepBuilder("sampleStep", jobRepository)
            .<NoPerishableProduct, NoPerishableProduct>chunk(10, transactionManager)
            .reader(noPerishableProductReader)
            .writer(writer)
            .build();
    }
    @Bean
    public Job sampleJob(JobRepository jobRepository, Step sampleStep) {
        return new JobBuilder("sampleJob", jobRepository)
                .start(sampleStep)
                .build();
    }
}
