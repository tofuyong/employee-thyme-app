package ibf2022.day21thyme.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf2022.day21thyme.model.Dependant;
import ibf2022.day21thyme.service.DependantService;

@Controller
@RequestMapping("/dependants")
public class DependantController {

    private static final Logger logger = LoggerFactory.getLogger(DependantController.class);

    @Autowired
    DependantService depSvc;

    @Autowired
    EmployeeController empCon;
    
    // #1 Show all dependants in listing page
    @GetMapping
    public String listDep(Model model) {
        List<Dependant> dependants = depSvc.findAll();
        model.addAttribute("dependants", dependants);
        return "deplist";
    }

    // #2 Add new dependant
    @GetMapping("/addnew")
    public String createDep(Model model) {
        Dependant depForm = new Dependant();
        model.addAttribute("depForm", depForm);
        return "newdep";
    }

    // #3 Post new Dependant  
    @PostMapping("/save")
    public String saveDep(@ModelAttribute("depForm") Dependant depForm, BindingResult result) {
        if (result.hasErrors()) {
            return "addnew";
        }

        Dependant newDep = new Dependant();
        newDep.setEmployeeId(depForm.getEmployeeId());
        newDep.setFullName(depForm.getFullName());
        newDep.setRelationship(depForm.getRelationship());
        newDep.setBirthdate(depForm.getBirthdate());
        depSvc.save(newDep);
        return "redirect:/dependants"; 
    }

    // #4 Delete Dependant by Id 
    @GetMapping("/delete/{id}")
    public String deleteDeo(@PathVariable("id") Integer id) {
        depSvc.delete(id);
        return "redirect:/dependants";
    }

    // #5 Update Dependant by Id
    @GetMapping("/update/{id}")
    public String editDep(@PathVariable("id") Integer id, Model model) {
        // First retrieve the dependant
        Dependant retrievedDep = depSvc.findById(id);
        logger.info(">> retrievedDep " + retrievedDep.getFullName()); // logging
        model.addAttribute("depForm", retrievedDep);
        // This will show the retrieved dependant. create update page, similar to newdep, but different end point
        return "updatedep";
    }

    // #6 Save updated Dependant in #5
    @PostMapping("/saveUpdate")
    public String saveUpdateDep(@ModelAttribute("depForm") Dependant dependant, BindingResult result) {
        if (result.hasErrors()) {
            return "updatedep";
        }
        depSvc.update(dependant);
        return "redirect:/dependants";
    }

}
