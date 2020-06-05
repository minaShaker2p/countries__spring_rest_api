package com.mina.countries.batch;

import com.mina.countries.model.CountryCSV;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchProcessingConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<CountryCSV> reader() {
        return new FlatFileItemReaderBuilder<CountryCSV>()
                .name("countryItemReader")
                .resource(new ClassPathResource("countries.csv"))
                .delimited()
                .names(new String[]{"id",
                        "nameEn",
                        "nameDe",
                        "altNameDe",
                        "isoNumeric",
                        "isoAlpha2",
                        "isoAlpha3",
                        "fibS10",
                        "stang",
                        "domain",
                        "countryCode",
                        "ioc",
                        "licensePlate",
                        "unlocode",
                        "longitude",
                        "latitude",
                        "active"
                })
                .fieldSetMapper(new BeanWrapperFieldSetMapper<CountryCSV>() {{
                    setTargetType(CountryCSV.class);
                }}).linesToSkip(1)
                .build();
    }


    @Bean
    public JdbcBatchItemWriter<CountryCSV> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<CountryCSV>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO country (country_id,name_en, name_de,country_code,license_plate) VALUES (:id,:nameEn, :nameDe,:countryCode,:licensePlate)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<CountryCSV> writer) {
        return stepBuilderFactory.get("step1")
                .<CountryCSV, CountryCSV>chunk(10)
                .reader(reader())
                .writer(writer)
                .build();
    }
}
