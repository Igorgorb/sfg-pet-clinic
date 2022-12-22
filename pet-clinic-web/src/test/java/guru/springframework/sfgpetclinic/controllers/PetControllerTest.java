package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Profile;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author igorg
 */
@WebMvcTest(PetController.class)
public class PetControllerTest {

  @MockBean
  PetService petService;

  @MockBean
  OwnerService ownerService;

  @MockBean
  PetTypeService petTypeService;
//  @InjectMocks
//  PetController petController;

  @Autowired
  MockMvc mockMvc;

  Owner owner;
  Set<PetType> petTypes;

  @BeforeEach
  public void setUp() {
    owner = Owner.builder().id(1L).build();

    petTypes = new HashSet<>();
    petTypes.add(PetType.builder().id(1L).name("Cat").build());
    petTypes.add(PetType.builder().id(2L).name("Dog").build());

//    mockMvc = MockMvcBuilders
//      .standaloneSetup(petController)
//      .build();
  }

  @Test
  public void initCreationForm() throws Exception {
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);

    mockMvc.perform(get("/owners/1/pets/new"))
      .andExpect(status().isOk())
      .andExpect(model().attributeExists("owner"))
      .andExpect(model().attributeExists("pet"))
      .andExpect(view().name("pets/createOrUpdatePetForm"));
  }

  @Test
  public void proccessCreationForm() throws Exception {
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);

    mockMvc.perform(post("/owners/1/pets/new"))
//      .andExpect(status().isOk())
      .andExpect(status().is3xxRedirection())
      .andExpect(view().name("redirect:/owners/1"));

    verify(petService).save(any());
  }

  @Test
  public void initUpdateForm() throws Exception {
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);
    when(petService.findById(anyLong())).thenReturn(Pet.builder().id(2L).build());

    mockMvc.perform(get("/owners/1/pets/2/edit"))
      .andExpect(status().isOk())
      .andExpect(model().attributeExists("owner"))
      .andExpect(model().attributeExists("pet"))
      .andExpect(view().name("pets/createOrUpdatePetForm"));
  }

  @Test
  public void processUpdateForm() throws Exception {
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);

    mockMvc.perform(post("/owners/1/pets/2/edit"))
      .andExpect(status().is3xxRedirection())
      .andExpect(view().name("redirect:/owners/1"));

    verify(petService).save(any());
  }

  @Test
  public void populatePetTypes() {
    //todo impl
  }

  @Test
  public void findOwner() {
    //todo impl
  }

  @Test
  public void initOwnerBinder() {
    //todo impl
  }
}
