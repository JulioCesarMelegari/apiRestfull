package com.jcm.apiRest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jcm.apiRest.entities.Person;
import com.jcm.apiRest.exception.PersonNotFoundException;
import com.jcm.apiRest.repositories.PersonRepository;

@RestController
public class PersonController {
	
	@Autowired
	private PersonRepository repository;
	
	@GetMapping("/persons")
	public List<Person> listOfPersonAll(){
		return repository.findAll();	
	}
	@GetMapping("/person/{id}")
	public Person getByPersonId(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(()-> new PersonNotFoundException(id));
	}
	
	@PostMapping("/persons")
	public Person newPerson(@RequestBody Person newPerson) {
		return repository.save(newPerson);
	}
	
	public Person replacePerson(@RequestBody Person newPerson, Long id) {
		return repository.findById(id).map(person ->{
			person.setName(newPerson.getName());
			person.setCpf(newPerson.getCpf());
			person.setAddress(newPerson.getAddress());
			return repository.save(newPerson);
		}).orElseGet(()->{
			newPerson.setId(id);
			return repository.save(newPerson);
		});
	}
	
	@DeleteMapping("/person/{id}")
	public void deletePerson(@PathVariable Long id) {
		repository.deleteById(id);
	}

}
