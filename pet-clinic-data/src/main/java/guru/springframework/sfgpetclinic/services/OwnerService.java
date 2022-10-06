package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.model.Owner;
import java.util.List;

/**
 * @author igorg
 * Date 06.05.2022
 */
public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);
    
    List<Owner> findAllByLastNameLike(String lastName);
}
