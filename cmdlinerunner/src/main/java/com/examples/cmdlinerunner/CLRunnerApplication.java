package com.examples.cmdlinerunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.scheduling.annotation.Scheduled;

import com.examples.cmdlinerunner.beans.ApplicationExternalComponent;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


@SpringBootApplication
public class CLRunnerApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CLRunnerApplication.class);
		application.run(args);
	}

	@Bean
	@Order(1)
	CommandLineRunner contextLoader(ApplicationExternalComponent external,
									ConfigurableApplicationContext configurableApplicationContext) {
		return args -> {
			MutablePropertySources propSources = configurableApplicationContext.getEnvironment().getPropertySources();
			propSources.addFirst(mapPropertySource(external.getComponentValue()));
			System.out.println("Properties configured successfully!.");
		};
	}

	private MapPropertySource mapPropertySource(String json) {
		return new MapPropertySource("service_A_app_state",
				new ObjectMapper().readValue(json, new TypeReference<>() {
				}));
	}


	@Order(2)
	@Bean
	CommandLineRunner prepareDatastore(){
		return (args) -> {
			Thread.sleep(1000);
			System.out.println("Data storage prepared successfully!");
		};
	}


	@Bean
	@Order(3)
	CommandLineRunner poller() {
		return (args) -> {
			Thread.sleep(1000);
			System.out.println("Sync Runner polled external service successfully!!");
		};
	}

	@Bean
	@Order(4)
	CommandLineRunner statePublisher() {

		return (args)->{

            CompletableFuture.supplyAsync(
					()-> {
                        try {
							Thread.sleep(3000);
							System.out.println("Application state information event published successfully!");
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        return Boolean.TRUE;
                    }
			);
		};
	}


	@Scheduled(fixedDelay = 3600, timeUnit = TimeUnit.SECONDS)
	CommandLineRunner scheduled(){
        return (args) -> System.out.println("Scheduled Job completed successfully. ");
	}

}