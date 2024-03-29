package guru.springframework.sfgpetclinic.repositories;

import guru.springframework.sfgpetclinic.model.Owner;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * @author igorg
 * Date 31.05.2022
 */
public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Owner findByLastName(String lastName);
    
    List<Owner> findAllByLastNameLike(String lastName);
}
