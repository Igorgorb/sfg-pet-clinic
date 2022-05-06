package guru.springframework.sfgpetclinic.services;

import java.util.Set;

/**
 * @author igorg
 * Date 06.05.2022
 */
public interface CrudService<T, ID> {

    Set<T> findAll();

    T findById(ID id);

    T save(T object);

    void delete(T object);

    void deleteById(ID id);
}
