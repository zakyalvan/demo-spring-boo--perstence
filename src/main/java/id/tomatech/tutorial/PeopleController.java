package id.tomatech.tutorial;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handle request dari client, browser atau mobile app.
 * 
 * @author zakyalvan
 */
@RestController
@RequestMapping(value="/people")
public class PeopleController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PeopleController.class);
	
	@Autowired
	private PersonRepository personRepository;
	
	/**
	 * Handle create user request.
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public HttpEntity<Person> registerPerson(@RequestBody Person person) {
		Person newPerson = personRepository.save(person);
		return new ResponseEntity<>(newPerson, HttpStatus.OK);
	}
	
	/**
	 * List all people.
	 * 
	 * @return
	 */
	@RequestMapping(value= "/list", method=RequestMethod.GET)
	public List<Person> listPeople() {
		LOGGER.info("List people");
		return personRepository.findAll();
	}
	
	/**
	 * Retrieve person by id.
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/detail/{personId}", method=RequestMethod.GET)
	public HttpEntity<Person> detailPerson(@PathVariable(value="personId") Long id) {
		LOGGER.info("Retrieve person with id {}", id);
		if(!personRepository.exists(id)) {
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Person>(personRepository.findOne(id), HttpStatus.OK);
	}
}
