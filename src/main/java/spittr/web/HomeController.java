package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spittr.entity.Spitter;
import spittr.service.SpitterManager;
import spittr.service.SpittleManager;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {

  private SpitterManager spitterManager;

  @Autowired
  public HomeController(SpitterManager spitterManager) {
    this.spitterManager = spitterManager;
  }

  @RequestMapping(method = GET)
  public String home(Model model, HttpSession sessionObj) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String name = auth.getName(); //get logged in username

    //save in session
    Spitter spitter = spitterManager.findByUsername(name);
    sessionObj.setAttribute("spitter", spitter);

    model.addAttribute("username", name);
    return "home";
  }

}
