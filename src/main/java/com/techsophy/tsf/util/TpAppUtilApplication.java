package com.techsophy.tsf.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import static com.techsophy.tsf.util.constants.QRCodeConstants.*;

@EnableMongoRepositories
@EnableMongoAuditing
@SpringBootApplication
@ComponentScan({PACKAGE_NAME,MULTI_TENANCY_PROJECT})
public class TpAppUtilApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(TpAppUtilApplication.class, args);
	}
}
