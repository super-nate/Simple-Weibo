
package spittr.web;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * Created by Administrator on 2016/7/6.
 */


@ControllerAdvice
public class AppWideExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public String spittleNotFoundHandler() {
        return "redirect:/spittles";
    }


  /*@ExceptionHandler(ConstraintViolationException.class)
    public String duplicateSpitterHandler() {
        return "redirect:/spitter/register";
    }*/
}

