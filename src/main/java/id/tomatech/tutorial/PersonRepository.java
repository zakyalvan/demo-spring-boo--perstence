package id.tomatech.tutorial;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Ini adalah antarmuka ke table database untuk entity {@link Person}.
 * Detailnya akan dijelaskan tutorial selanjutnya.
 * 
 * @author zakyalvan
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
	
}
