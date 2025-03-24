package com.splashScore.waterpolo_app.web.exception_handler;

import com.splashScore.waterpolo_app.exception.EmailAlreadyExistException;
import com.splashScore.waterpolo_app.exception.UsernameAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ModelAndView exceptionHandler(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message", "This username is already in use");

        return new ModelAndView("redirect:/register");
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ModelAndView emailAlreadyExistHandler(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("email_in_use", "This email is already in use");
        return new ModelAndView("redirect:/register");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            AccessDeniedException.class, // не му е позволено/нямам достъп до даден endpoint
            NoResourceFoundException.class, //  несъществуващ endpoint
            MethodArgumentTypeMismatchException.class,
            MissingRequestValueException.class
    })
    public ModelAndView handleNotFoundExceptions(Exception exception) {

        return new ModelAndView("not-found");
    }
}
