package com.cedsif;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cedsif.model.Departament;
import com.cedsif.model.Profile;
import com.cedsif.repository.DepartamentRepository;
import com.cedsif.repository.ProfileRepository;

@SpringBootApplication
public class CedsifApplication {

	public static void main(String[] args) {
		SpringApplication.run(CedsifApplication.class, args);
	}

	@Bean
	CommandLineRunner depCommandLineRunner(DepartamentRepository repository) {
		
		return args -> {
			Departament departament = new Departament();
			departament.setName("SGA");
			departament.setCode("321");
			repository.save(departament);
		};
	}
	
	
	@Bean
	CommandLineRunner profileCommandaLineRunner(ProfileRepository repository) {
		
		return args -> {
			Profile profile1 = new Profile();
			profile1.setProfile("Testador");
			repository.save(profile1);
			
			Profile profile2 = new Profile();
			profile2.setProfile("Programador");
			repository.save(profile2);
			
			Profile profile3 = new Profile();
			profile3.setProfile("Analista");
			repository.save(profile3);
		};
	}
	
	
}
