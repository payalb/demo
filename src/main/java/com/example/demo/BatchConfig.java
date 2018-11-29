package com.example.demo;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.demo.dto.Employee;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired DataSource ds;
	@Autowired StepBuilderFactory stepBuilderFactory;
	
	@Autowired JobBuilderFactory jobBuilderFactory;
	@Autowired EmployeeProcessor processor;
	@Bean
	public ItemReader reader() {
		FlatFileItemReader<Employee> reader= new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("employees.csv"));
		reader.setLineMapper(new DefaultLineMapper<Employee>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setNames(new String[] {"name","salary","emailId"});
				
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {{
				setTargetType(Employee.class);
			}});
		}});
		return reader;
	}
	
	@Bean
	public ItemWriter writer() {
		JdbcBatchItemWriter<Employee> writer= new JdbcBatchItemWriter<>();
		writer.setDataSource(ds);
		writer.setSql("Insert into employee values (?,?,?,?)");
		writer.setItemPreparedStatementSetter(new EmployeePreparedStatementSetter());
		return writer;
	}
	
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<Employee, Employee>chunk(2).reader(reader()).processor(processor).writer(writer()).build();
	}
	
	/*@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2").<Employee, Employee>chunk(2).reader(reader()).processor(processor).writer(writer()).build();
	}*/
	@Bean
	public Job job() {
		return jobBuilderFactory.get("EmployeeProcessing").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
	}
}
