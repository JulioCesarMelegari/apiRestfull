package com.jcm.apiRest.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcm.apiRest.entities.Person;
import com.jcm.apiRest.exception.PersonNotFoundException;
import com.jcm.apiRest.repositories.PersonRepository;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonRepository repository;
	
	@GetMapping
	public String Welcome() {
		return("Welcome API Persons");
	}
	
	@GetMapping("/persons")
	 ResponseEntity<List<Person>> listOfPersonAll(){
		Long idPerson;
		Link linkUri;
		List<Person> personList = repository.findAll();
		if(personList.isEmpty()) return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
		for (Person person: personList) {
			idPerson = person.getId();
			linkUri = linkTo(methodOn(PersonController.class).getByPersonId(idPerson)).withSelfRel();
			person.add(linkUri);
			linkUri = linkTo(methodOn(PersonController.class).listOfPersonAll()).withRel("Cusumer Person List");
			person.add(linkUri);
		}
		return new ResponseEntity<List<Person>>(personList, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Person> getByPersonId(@PathVariable Long id) {
		Optional<Person> personPointer = repository.findById(id);
		if(personPointer.isPresent()) {
			Person person = personPointer.get();
			person.add(linkTo(methodOn(PersonController.class).listOfPersonAll()).withRel("All persons"));
			return new ResponseEntity<>(person, HttpStatus.OK);
		}else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public Person newPerson(@RequestBody Person newPerson) {
		return repository.save(newPerson);
	}
	
	@PutMapping("/id")
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
	
	@DeleteMapping("/delete/{id}")
	public void deletePerson(@PathVariable Long id) {
		repository.deleteById(id);
	}

}
