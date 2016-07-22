package spittr.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import spittr.data.SpittleRepository;
import spittr.entity.Spitter;
import spittr.entity.Spittle;
import spittr.entity.SpittleForm;
import spittr.service.SpitterManager;
import spittr.service.SpittleManager;

import spittr.exception.Error;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
@RestController
@RequestMapping("/api/spittles")
public class SpittleApiController {
    private static final String MAX_LONG_AS_STRING = "9223372036854775807";

    private static final String PAGE_SIZE = "5";

    private SpittleManager spittleManager;
    private SpitterManager spitterManager;

    @Autowired
    public SpittleApiController(SpittleManager spittleManager, SpitterManager spitterManager){
        this.spittleManager = spittleManager;
        this.spitterManager = spitterManager;
    }

    @RequestMapping(method=RequestMethod.GET, produces="application/json")
    public List<Spittle> spittles(
            @RequestParam(value = "page",
                    defaultValue = "1") int page, @RequestParam(value = "pageSize",
            defaultValue = PAGE_SIZE) int pageSize) {

        List<Spittle> list = spittleManager.findByPage(pageSize, page);

        if (list.isEmpty()) {
            list = spittleManager.findByPage(pageSize, 1);
        }

        return list;
    }

    //Catch access deny exception, illegal arguement exception
    @RequestMapping(value = "/{spittleId}", method = RequestMethod.GET, produces="application/json")
    public Spittle spittle(
            @PathVariable("spittleId") long spittleId) {
        return spittleManager.findOne(spittleId);
    }


    @RequestMapping(method = RequestMethod.POST, consumes="application/json", produces ="application/json")
    public ResponseEntity<Spittle> saveSpittle(@RequestBody Spittle spittle,UriComponentsBuilder ucb) throws Exception {

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

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Error spittleNotAllowed(
            AccessDeniedException e) {
        return new Error(-1, "You have no access to this spittle");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error spittleNotFound(
            IllegalArgumentException e) {
        return  new Error(-1, "Spittle not found");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error spittleNotFound(
            ConstraintViolationException e) {
        return  new Error(-1, e.getMessage());
    }
}
