package br.com.erudio.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.PersonVO;
import br.com.erudio.services.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonService service;
	
	@GetMapping (produces = {"application/json", "application/xml", "application/x-yaml"})
	//@RequestMapping(method=RequestMethod.GET) mesma coisa que getMapping
		//	produces=MediaType.APPLICATION_JSON_VALUE) ja esta no pacote do Spring Boot
	public List<PersonVO> findAll() {
		List<PersonVO> persons = service.findAll();
		persons
			.stream()
			.forEach(p -> p.add(
					linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel())
					);
	//	return service.findAll();
		return persons;
		
	}
	
	@GetMapping(value = "/{id}", produces = {"application/json","application/xml", "application/x-yaml"})
	//@RequestMapping(value="/{Id}", 
		//	method=RequestMethod.GET)
		//	produces=MediaType.APPLICATION_JSON_VALUE)
	public PersonVO findById(@PathVariable("id") Long id) {
		PersonVO personVO = service.findById(id);
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVO;
	//	return service.findById(Id);
		
	}
	
	@PostMapping (produces = {"application/json","application/xml", "application/x-yaml"},
				  consumes = {"application/json", "application/xml", "application/x-yaml"})
	//@RequestMapping(method=RequestMethod.POST)
		//	consumes=MediaType.APPLICATION_JSON_VALUE,
		//	produces=MediaType.APPLICATION_JSON_VALUE)
	public PersonVO create(@RequestBody PersonVO person) {
		

		return service.create(person);
		
	}
	
	@PutMapping
//	@RequestMapping(method=RequestMethod.PUT)
			//consumes=MediaType.APPLICATION_JSON_VALUE,
			//produces=MediaType.APPLICATION_JSON_VALUE)
	public PersonVO update(@RequestBody PersonVO person) {
		

		return service.update(person);
		
	}
	
	@DeleteMapping("/{Id}")
//	@RequestMapping(value="/{Id}", 
//			method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("Id") Long Id) {
		

		service.delete(Id);
		
		return ResponseEntity.ok().build();
		
	}
	


}
