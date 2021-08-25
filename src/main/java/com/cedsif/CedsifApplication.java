package com.cedsif;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cedsif.model.Adminstrator;
import com.cedsif.model.Category;
import com.cedsif.model.Consultant;
import com.cedsif.model.Departament;
import com.cedsif.model.Employee;
import com.cedsif.model.Manager;
import com.cedsif.model.Profile;
import com.cedsif.model.Shift;
import com.cedsif.repository.DepartamentRepository;
import com.cedsif.repository.EmployeeRepository;
import com.cedsif.repository.ProfileRepository;

@SpringBootApplication
public class CedsifApplication {

	public static void main(String[] args) {
		SpringApplication.run(CedsifApplication.class, args);
	}

	@Bean
	CommandLineRunner depCommandLineRunner(DepartamentRepository repository) {
		
		return args -> {
			Departament sga = new Departament();
			sga.setName("SGA");
			sga.setCode("321");
			repository.save(sga);
			
			Departament departament = new Departament();
			departament.setName("SDA");
			departament.setCode("1568");
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
	
	@Bean
	CommandLineRunner employeeCommandaLineRunner(EmployeeRepository repository, ProfileRepository profileRepository) {
		
		return args -> {
			
			// -------------------------------- Consultant
			
			
			Employee employee = new Employee();
			employee.setName("Jobs Steve");
			employee.setNuit("1546468");
			Short sex = 1;
			employee.setSex(sex);
			employee.setCategory(Category.C);
			
			Employee saveEmployee = repository.save(employee);

			Consultant consultant = new Consultant();
			consultant.setId(employee.getId());
			consultant.setAddress("Grande Maputo");
			consultant.setProfile(profileRepository.findAll().get(0));
			consultant.setEmployee(saveEmployee);
			saveEmployee.setConsultant(consultant);
			saveEmployee = repository.save(saveEmployee);
			
			
			Employee employee1 = new Employee();
			employee1.setName("Bobby Esponja");
			employee1.setNuit("1546468");
			employee1.setSex(Short.parseShort("1"));
			employee1.setCategory(Category.C);
			
			Employee saveEmployee1 = repository.save(employee1);

			Consultant consultant1 = new Consultant();
			consultant1.setId(employee1.getId());
			consultant1.setAddress("Marracune");
			consultant1.setProfile(profileRepository.findAll().get(1));
			consultant1.setEmployee(saveEmployee1);
			saveEmployee1.setConsultant(consultant1);
			saveEmployee1 = repository.save(saveEmployee1);
			
			
			
			
			// -------------------------------- Manager
			
			Employee billy = new Employee();
			billy.setName("Gates Billy");
			billy.setNuit("1756616");
			billy.setSex(Short.parseShort("1"));
			billy.setCategory(Category.G);
			
			Employee savedBilly = repository.save(billy);

			Manager manager = new Manager();
			manager.setId(savedBilly.getId());
			manager.setInstitution("Standford University");
			manager.setCourse("Computer Science");
			manager.setEmployee(savedBilly);
			savedBilly.setManager(manager);
			repository.save(savedBilly);
			
			// -------------------------------- Administrator
			
			
			Employee musk = new Employee();
			musk.setName("Musk Elon");
			musk.setNuit("1756616");
			musk.setSex(Short.parseShort("1"));
			musk.setCategory(Category.A);

			Employee savedMusk = repository.save(musk);

			Adminstrator admin = new Adminstrator();
			admin.setId(savedMusk.getId());
			admin.setShift(Shift.M);
			admin.setEmployee(savedMusk);
			savedMusk.setAdminstrator(admin);
			savedMusk = repository.save(savedMusk);
			
		};
	}
	
	
	
}
