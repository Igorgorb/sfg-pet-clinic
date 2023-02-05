package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * Created by igorg on 22 груд. 2022 р.
 *
 * @author igorg
 */
@Controller
public class VisitController {

  private final VisitService visitService;
  private final PetService petService;

  public VisitController(VisitService visitService, PetService petService) {
    this.visitService = visitService;
    this.petService = petService;
  }

  @InitBinder
  public void setAllowedFields(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");

    dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String text) throws IllegalArgumentException {
        setValue(LocalDate.parse(text));
      }
    });
  }

  /**
   * Called before each and every @RequestMapping annotated method.
   * 2 goals: 
   * - Make sure we always have fresh data 
   * - Since we do not use the session scope, make sure that Pet object always has an id 
   * (Even though id is not part of the form fields)
   *
   * @param petId
   * @param model
   * @return Pet
   */
  @ModelAttribute("visit")
  public Visit loadPetWithVisit(@PathVariable("petId") Long petId, Model model) {
    Pet pet = petService.findById(petId);
    model.addAttribute("pet", pet);

    Visit visit = new Visit();
    pet.addVisit(visit);
    return visit;
  }

  // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is
  // called
  @GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
  public String initNewVisitForm() {
    return "pets/createOrUpdateVisitForm";
  }

  // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is
  // called
  @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
  public String processNewVisitForm(@Valid Visit visit, BindingResult result) {
    if (result.hasErrors()) {
      return "pets/createOrUpdateVisitForm";
    }

//    owner.addVisit(petId, visit);
    visitService.save(visit);
    return "redirect:/owners/{ownerId}";
  }
}
