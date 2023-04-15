package com.jcm.apiRest.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jcm.apiRest.entities.Person;
import com.jcm.apiRest.repositories.PersonRepository;

@Configuration
public class LoadDB {
	
	private static final Logger log = LoggerFactory.getLogger(LoadDB.class);
	
	@Bean
	CommandLineRunner applicationRunner(PersonRepository repository) {
		
		Person person1 = new Person("André Andrade", "998.665.998-99", "Rua das flores 698");
		Person person2 = new Person("Bruna Andrade", "998.665.998-99", "Rua das dores 698");
		Person person3 = new Person("Carla Farias", "998.665.998-99", "Rua das facas 698");
		Person person4 = new Person("Francisco Jose", "998.665.998-99", "Rua das camelias 698");
		Person person5 = new Person("Jocasta Figueiredo", "998.665.998-99", "Rua das putaneiras 698");
		Person person6 = new Person("Paulo Lima", "998.665.998-99", "Rua das palmeiras 698");
		return args ->{
			log.info("Log of event save Person 1: " + repository.save(person1));
			log.info("Log of event save Person 1: " + repository.save(person2));
			log.info("Log of event save Person 1: " + repository.save(person3));
			log.info("Log of event save Person 1: " + repository.save(person4));
			log.info("Log of event save Person 1: " + repository.save(person5));
			log.info("Log of event save Person 1: " + repository.save(person6));
		};
	}
}
