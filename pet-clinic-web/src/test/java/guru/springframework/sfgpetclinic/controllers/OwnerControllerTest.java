package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;
import static org.hamcrest.Matchers.hasProperty;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.mockito.ArgumentMatchers;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author igorg
 * @date 13.06.2022
 */
@ExtendWith(MockitoExtension.class)
public class OwnerControllerTest {

  @Mock
  OwnerService ownerService;

  @InjectMocks
  OwnerController controller;

  Set<Owner> owners;

  MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    owners = new HashSet<>();
    owners.add(Owner.builder().id(1L).build());
    owners.add(Owner.builder().id(2L).build());

    mockMvc = MockMvcBuilders
      .standaloneSetup(controller)
      .build();
  }

//  @Test
//  void listOwners() throws Exception {
//    when(ownerService.findAll()).thenReturn(owners);
//
//    mockMvc.perform(get("/owners"))
//      .andExpect(status().isOk())
//      .andExpect(view().name("owners/index"))
//      .andExpect(model().attribute("owners", hasSize(2)));
//  }
//
//  @Test
//  void listOwnersByIndex() throws Exception {
//    when(ownerService.findAll()).thenReturn(owners);
//
//    mockMvc.perform(get("/owners/index"))
//      .andExpect(status().isOk())
//      .andExpect(view().name("owners/index"))
//      .andExpect(model().attribute("owners", hasSize(2)));
//  }
  @Test
  public void findOwners() throws Exception {
    mockMvc.perform(get("/owners/find"))
      .andExpect(status().isOk())
      .andExpect(view().name("owners/findOwners"))
      .andExpect(model().attributeExists("owner"));

    verifyNoInteractions(ownerService);
  }

  @Test
  public void processFindFromReturnMany() throws Exception {
    when(ownerService.findAllByLastNameLike(anyString()))
      .thenReturn(Arrays.asList(Owner.builder().id(1L).build(),
        Owner.builder().id(2L).build()));

    mockMvc.perform(get("/owners"))
      .andExpect(status().isOk())
      .andExpect(view().name("owners/ownersList"))
      .andExpect(model().attribute("selections", hasSize(2)));
  }

  @Test
  public void processFindFromReturnOne() throws Exception {
    when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList(Owner.builder().id(1L).build()));

    mockMvc.perform(get("/owners"))
      .andExpect(status().is3xxRedirection())
      .andExpect(view().name("redirect:/owners/1"));
  }

  @Test
  public void processFindFromEmptyReturnMany() throws Exception {
    when(ownerService.findAllByLastNameLike(anyString()))
      .thenReturn(
        Arrays.asList(Owner.builder().id(1L).build(),
          Owner.builder().id(2L).build()));

    mockMvc.perform(get("/owners")
      .param("lastName", ""))
      .andExpect(status().isOk())
      .andExpect(view().name("owners/ownersList"))
      .andExpect(model().attribute("selections", hasSize(2)));
  }

  @Test
  public void displayOwner() throws Exception {
    when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

    mockMvc.perform(get("/owners/123"))
      .andExpect(status().isOk())
      .andExpect(view().name("owners/ownerDetails"))
      .andExpect(model().attribute("owner", hasProperty("id", is(1L))));
  }

  @Test
  public void initCreationForm() throws Exception {
    mockMvc.perform(get("/owners/new"))
      .andExpect(status().isOk())
      .andExpect(view().name("owners/createOrUpdateOwnerForm"))
      .andExpect(model().attributeExists("owner"));

    verifyNoInteractions(ownerService);
  }

  @Test
  public void processCreationForm() throws Exception {
    when(ownerService.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(1L).build());

    mockMvc.perform(post("/owners/new"))
      .andExpect(status().is3xxRedirection())
      .andExpect(view().name("redirect:/owners/1"))
      .andExpect(model().attributeExists("owner"));

    verify(ownerService).save(ArgumentMatchers.any());
  }

  @Test
  public void initUpdateOwnerForm() throws Exception {
    when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

    mockMvc.perform(get("/owners/1/edit"))
      .andExpect(status().isOk())
      .andExpect(view().name("owners/createOrUpdateOwnerForm"))
      .andExpect(model().attributeExists("owner"));

    //verifyNoInteractions(ownerService);    
  }

  @Test
  public void processUpdateOwnerForm() throws Exception {
    when(ownerService.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(1L).build());

    mockMvc.perform(post("/owners/1/edit"))
      .andExpect(status().is3xxRedirection())
      .andExpect(view().name("redirect:/owners/1"))
      .andExpect(model().attributeExists("owner"));

    verify(ownerService).save(ArgumentMatchers.any());
  }
}
