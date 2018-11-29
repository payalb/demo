package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class MyJobScheduler {
	@Autowired
	private JobLauncher launcher;
	@Autowired Job job;
	
	@Scheduled(cron="*/10 * * * * *")
	public void scheduler() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParameters jobParameters= new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
		JobExecution ex=launcher.run(job, jobParameters);
		System.out.println(ex.getEndTime());
		System.out.println(ex.getExitStatus());
	}
}
