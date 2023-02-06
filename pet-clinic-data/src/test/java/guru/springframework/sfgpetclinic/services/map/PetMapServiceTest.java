package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Pet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author <a href="mailto:k.czechowski83@gmail.com">Krzysztof Czechowski</a>
 * Created by igorg on 6 лют. 2023 р.
 * @author igorg 
 */
public class PetMapServiceTest {

  private PetMapService petMapService;

  private final Long petId = 1L;

  @BeforeEach
  public void setUp() {

    petMapService = new PetMapService();

    petMapService.save(Pet.builder().id(petId).build());
  }

  @Test
  public void findAll() {

    Set<Pet> petSet = petMapService.findAll();

    assertEquals(1, petSet.size());
  }

  @Test
  public void findByIdExistingId() {

    Pet pet = petMapService.findById(petId);

    assertEquals(petId, pet.getId());
  }

  @Test
  public void findByIdNotExistingId() {

    Pet pet = petMapService.findById(5L);

    assertNull(pet);
  }

  @Test
  public void findByIdNullId() {

    Pet pet = petMapService.findById(null);

    assertNull(pet);
  }

  @Test
  public void saveExistingId() {

    Long id = 2L;

    Pet pet2 = Pet.builder().id(id).build();

    Pet savedPet = petMapService.save(pet2);

    assertEquals(id, savedPet.getId());
  }

  @Test
  public void saveDuplicateId() {

    Long id = 1L;

    Pet pet2 = Pet.builder().id(id).build();

    Pet savedPet = petMapService.save(pet2);

    assertEquals(id, savedPet.getId());
    assertEquals(1, petMapService.findAll().size());
  }

  @Test
  public void saveNoId() {

    Pet savedPet = petMapService.save(Pet.builder().build());

    assertNotNull(savedPet);
    assertNotNull(savedPet.getId());
    assertEquals(2, petMapService.findAll().size());
  }

  @Test
  public void deletePet() {

    petMapService.delete(petMapService.findById(petId));

    assertEquals(0, petMapService.findAll().size());

  }

  @Test
  public void deleteWithWrongId() {

    Pet pet = Pet.builder().id(5L).build();

    petMapService.delete(pet);

    assertEquals(1, petMapService.findAll().size());
  }

  @Test
  public void deleteWithNullId() {

    Pet pet = Pet.builder().build();

    petMapService.delete(pet);

    assertEquals(1, petMapService.findAll().size());
  }

  @Test
  public void deleteNull() {

    petMapService.delete(null);

    assertEquals(1, petMapService.findAll().size());

  }

  @Test
  public void deleteByIdCorrectId() {

    petMapService.deleteById(petId);

    assertEquals(0, petMapService.findAll().size());
  }

  @Test
  public void deleteByIdWrongId() {

    petMapService.deleteById(5L);

    assertEquals(1, petMapService.findAll().size());
  }

  @Test
  public void deleteByIdNullId() {

    petMapService.deleteById(null);

    assertEquals(1, petMapService.findAll().size());
  }
}
