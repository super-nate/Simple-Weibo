package spittr.web;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import spittr.entity.Spitter;
import spittr.entity.Spittle;
import spittr.entity.SpittleForm;
import spittr.service.SpitterManager;
import spittr.service.SpittleManager;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

    private static final String MAX_LONG_AS_STRING = "9223372036854775807";
    private static final String PAGE_SIZE = "5";

    private SpittleManager spittleManager;

    private SpitterManager spitterManager;

    @Autowired
    public SpittleController(SpittleManager spittleManager, SpitterManager spitterManager) {
        this.spittleManager = spittleManager;
        this.spitterManager = spitterManager;
    }


    //simple paging using PagedListHolder
/*  @RequestMapping(method=RequestMethod.GET)
  public List<Spittle> spittles() {
    return spittleManager.findAll();
  }*/

/*  @RequestMapping(method=RequestMethod.GET)
  public String spittles(
          @RequestParam(value="page",
                  defaultValue="1") int page, Model model) {

    PagedListHolder<Spittle> pagedListHolder = new PagedListHolder<>(spittleManager.findAll());
    pagedListHolder.setPageSize(2);

    if(page < 1 || page > pagedListHolder.getPageCount()) page=1;
    pagedListHolder.setPage(page-1);

    model.addAttribute("page", page);
    model.addAttribute("spittleList", pagedListHolder.getPageList());
    return "spittles";
  }*/

    //custom paging, more efficient
    @RequestMapping(method = RequestMethod.GET)
    public String spittles(
            @RequestParam(value = "page",
                    defaultValue = "1") int page, @RequestParam(value = "pageSize",
            defaultValue = PAGE_SIZE) int pageSize, Model model, HttpSession sessionObj) {

        List<Spittle> list = spittleManager.findByPage(pageSize, page);

        if (list.isEmpty()) {
            list = spittleManager.findByPage(pageSize, 1);
        }

        model.addAttribute("page", page);
        model.addAttribute("spittleList", list);
        return "spittles";
    }

    @RequestMapping(value = "/ownSpittles", method = RequestMethod.GET)
    public String ownSpittles(
            @RequestParam(value = "page",
                    defaultValue = "1") int page, @RequestParam(value = "pageSize",
            defaultValue = PAGE_SIZE) int pageSize, Model model, HttpSession sessionObj) {

        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();*/ //get logged in username

        //TODO how to keep the login user in session or how to get the login user
        Spitter spitter = (Spitter)sessionObj.getAttribute("spitter");

        PagedListHolder<Spittle> pagedListHolder = new PagedListHolder<>(spitter.getSpittles());
        pagedListHolder.setPageSize(pageSize);
        if (page < 1 || page > pagedListHolder.getPageCount()) page = 1;
        pagedListHolder.setPage(page - 1);

        model.addAttribute("page", page);
        model.addAttribute("spittleList", pagedListHolder.getPageList());
        return "ownSpittles";
    }


    @RequestMapping(value = "/{spittleId}", method = RequestMethod.GET)
    public String spittle(
            @PathVariable("spittleId") long spittleId,
            Model model) {
        model.addAttribute(spittleManager.findOne(spittleId));
        return "spittle";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveSpittle(SpittleForm form, Model model, HttpSession sessionObj) throws Exception {

       /* Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
*/
        //TODO how to keep the login user in session or how to get the login user
        Spitter spitter = (Spitter)sessionObj.getAttribute("spitter");


        Long id = spittleManager.save(new Spittle(null, form.getMessage(), new Date(),
                form.getLongitude(), form.getLatitude(), spitter)).getId();

        MultipartFile profilePicture = form.getProfilePicture();
        if (profilePicture != null) {
            profilePicture.transferTo(new File("/spittles/" + id + ".jpg"));
        }
        return "redirect:/spittles";
    }


    @RequestMapping(value = "/test", method = RequestMethod.POST, consumes="application/json", produces ="application/json")
    public ResponseEntity<Spittle> saveSpittle(@RequestBody Spittle spittle, UriComponentsBuilder ucb) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username

        Spitter spitter = spitterManager.findByUsername(name);

        spittle.setSpitter(spitter);

        Long id = spittleManager.save(spittle).getId();

        HttpHeaders headers = new HttpHeaders();
        URI locationUri =
                ucb.path("/api/spittles/")
                        .path(String.valueOf(id))
                        .build()
                        .toUri();
        headers.setLocation(locationUri);
        ResponseEntity<Spittle> responseEntity =
                new ResponseEntity<Spittle>(
                        spittle, headers, HttpStatus.CREATED);
        return responseEntity;
    }

/*    @ExceptionHandler(IllegalArgumentException.class)
    public String spittleNotFoundHandler() {
        return "redirect:/spittles";
    }*/
}
