package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import spittr.entity.Spitter;
import spittr.service.SpitterManager;

@Controller
@RequestMapping("/spitter")
public class SpitterController {

  private SpitterManager spitterManager;

  @Autowired
  public SpitterController(SpitterManager spitterManager) {
    this.spitterManager = spitterManager;
  }
  
  @RequestMapping(value="/register", method=GET)
  public String showRegistrationForm(Model model) {
    model.addAttribute(new Spitter());
    return "registerForm";
  }
  
  @RequestMapping(value="/register", method=POST)
  public String processRegistration(
      @Valid Spitter spitter, 
      Errors errors, Model model) {
    if (errors.hasErrors()) {
      return "registerForm";
    }
    try {
      spitterManager.save(spitter);
      model.addAttribute("username", spitter.getUsername());
      return "redirect:/spitter/{username}";
    }
    catch (ConstraintViolationException e){
      return "registerForm";
    }
  }
  
  @RequestMapping(value="/me", method=GET)
  public String me() {
    System.out.println("ME ME ME ME ME ME ME ME ME ME ME");
    return "home";
  }

  @RequestMapping(value="/{username}", method=GET)
  public String showSpitterProfile(@PathVariable String username, Model model) {
    Spitter spitter = spitterManager.findByUsername(username);
    model.addAttribute(spitter);
    return "profile";
  }

/*  @ExceptionHandler(ConstraintViolationException.class)
  public String duplicateSpittleHandler() {
    System.out.println("I AM HERE");
    return "redirect:/spitter/register";
  }*/
}
