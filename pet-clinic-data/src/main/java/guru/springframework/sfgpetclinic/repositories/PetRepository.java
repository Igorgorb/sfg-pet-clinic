package guru.springframework.sfgpetclinic.repositories;

import guru.springframework.sfgpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

/**
 * @author igorg
 * Date 31.05.2022
 */
public interface PetRepository extends CrudRepository<Pet, Long> {
}
