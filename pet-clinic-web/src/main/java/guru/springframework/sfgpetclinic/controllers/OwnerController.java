package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author igorg 
 * @date 08.05.2022
 */
@RequestMapping("/owners")
@Controller
public class OwnerController {

  private final OwnerService ownerService;

  public OwnerController(OwnerService ownerService) {
    this.ownerService = ownerService;
  }

  @InitBinder
  public void setAllowedFields(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

//  @RequestMapping({"", "/", "/index", "/index.html"})
//  public String listOwners(Model model) {
//
//    model.addAttribute("owners", ownerService.findAll());
//
//    return "owners/index";
//  }
  @RequestMapping("/find")
  public String findOwners(Model model) {
    model.addAttribute("owner", Owner.builder().build());
    return "owners/findOwners";
  }

  @GetMapping
  public String processFindForm(Owner owner, BindingResult result, Model model) {
    // allow parameterless GET request for /owners to return all records
    if (owner.getLastName() == null) {
      owner.setLastName(""); // empty string signifies broadest possible search
    }

    // find owners by last name
    List<Owner> ownersResults = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");
    if (ownersResults.isEmpty()) {
      // no owners found
      result.rejectValue("lastName", "notFound", "not found");
      return "owners/findOwners";
    }

    if (ownersResults.size() == 1) {
      // 1 owner found
      owner = ownersResults.iterator().next();
      return "redirect:/owners/" + owner.getId();
    } else {
      // multiple owners found
      model.addAttribute("selections", ownersResults);
      return "owners/ownersList";
    }
  }

  @GetMapping("/{ownerId}")
  public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
    ModelAndView mav = new ModelAndView("owners/ownerDetails");
    Owner owner = this.ownerService.findById(ownerId);
    mav.addObject(owner);
    return mav;
  }
}
