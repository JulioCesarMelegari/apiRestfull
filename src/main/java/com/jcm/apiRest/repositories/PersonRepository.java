package com.jcm.apiRest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcm.apiRest.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
