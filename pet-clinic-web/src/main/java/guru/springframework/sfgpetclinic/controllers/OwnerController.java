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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

/**
 * @author igorg
 * @date 08.05.2022
 */
@RequestMapping("/owners")
@Controller
public class OwnerController {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
  
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

  @GetMapping
  @RequestMapping("/new")
  public String initCreationForm(Model model) {
    model.addAttribute("owner", Owner.builder().build());

    return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
  }

  @PostMapping("/new")
  public String processCreationForm(@Valid Owner owner, BindingResult result) {
    if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
    
    Owner savedOwner = this.ownerService.save(owner);

//    model.addAttribute("owner", savedOwner);

    return "redirect:/owners/" + savedOwner.getId();
  }

  @GetMapping("/{ownerId}/edit")
  public String initUpdateOwnerForm(@PathVariable String ownerId, Model model) {
    Owner founedOwner = this.ownerService.findById(Long.valueOf(ownerId));

    model.addAttribute("owner", founedOwner);

    return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
  }

  @PostMapping("/{ownerId}/edit")
  public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable Long ownerId) {
    if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
    owner.setId(ownerId);
    Owner savedOwner = this.ownerService.save(owner);
    return "redirect:/owners/" + savedOwner.getId();
  }

}
