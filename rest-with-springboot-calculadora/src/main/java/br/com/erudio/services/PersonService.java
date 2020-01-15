package br.com.erudio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.data.model.Person;
import br.com.erudio.data.vo.PersonVO;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.repository.PersonRepository;

@Service
public class PersonService {
	@Autowired
	PersonRepository repository;
	
	public PersonVO findById(Long id) {
		

	//	return repository.findById(id)
		//		.orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID"));
		
		Object entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID"));
				
		return DozerConverter.parseObject(entity, PersonVO.class);
		
	}
	
	public PersonVO create (PersonVO person) {
	//	PersonVO entity = DozerConverter.parseObject(person, Person.class);
		Person entity = DozerConverter.parseObject(person, Person.class);
		PersonVO vo	= DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		
		return vo;
		
		
		
		
	//	return repository.save(person);
	}
	
	public PersonVO update (PersonVO person) {
		
		Person entity = repository.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setGender(person.getGender());
		entity.setAddress(person.getAddress());
		
		PersonVO vo =  DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
	
	public void delete (Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID"));
		repository.delete(entity);
	}
	
	
	public List<PersonVO> findAll() {

	//	return repository.findAll() ;
		return DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);
	}



}
