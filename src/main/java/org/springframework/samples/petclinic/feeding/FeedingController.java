package org.springframework.samples.petclinic.feeding;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.pet.PetType;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feeding")
public class FeedingController {
	
	private static final String VIEWS_CREATE_OR_UPDATE_FORM = "feedings/createOrUpdateFeedingForm";
	
	private PetService ps;
	private FeedingService fs;
	
	@Autowired
	public FeedingController(PetService ps, FeedingService fs) 
	{
		this.ps = ps;
		this.fs = fs;
	}
	
	@ModelAttribute("feedingTypes")
	public Collection<FeedingType> populateTypes() {
		return this.fs.getAllFeedingTypes();
	}

	@ModelAttribute("pets")
	public Collection<Pet> populatePets() {
		return this.ps.getAllPets();
	}
	
	@GetMapping(value = "/create")
	public String initCreationForm(ModelMap model) {
		Feeding f = new Feeding();
		model.put("feeding", f);
		return VIEWS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/create")
	public String processCreationForm(@Valid Feeding feeding, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("feeding", feeding);
			return VIEWS_CREATE_OR_UPDATE_FORM;
		}
		else {
                    try{
                    	this.fs.save(feeding);
                    }catch(UnfeasibleFeedingException ex){
                        result.rejectValue("name", "tipo incorrecto", "La mascota seleccionada no se le puede asignar el plan de"
                        		+ "alimentación especificado.");
                        return VIEWS_CREATE_OR_UPDATE_FORM;
                    }
                    return "redirect:/welcome";
		}
	}

    
}
