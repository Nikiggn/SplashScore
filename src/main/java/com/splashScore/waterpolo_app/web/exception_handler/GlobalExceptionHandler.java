package com.splashScore.waterpolo_app.web.exception_handler;

import com.splashScore.waterpolo_app.exception.*;
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
import org.thymeleaf.exceptions.TemplateProcessingException;

import java.net.ConnectException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TemplateProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleTemplateProcessingException(Exception ex) {
        ModelAndView mav = new ModelAndView("not-found");
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return mav;
    }

    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ModelAndView exceptionHandler(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("usernameAlreadyExistMessage", "This username is already in use");

        return new ModelAndView("redirect:/register");
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ModelAndView emailAlreadyExistHandler(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("emailAlreadyExistMessage", "This email is already in use");
        return new ModelAndView("redirect:/register");
    }

    @ExceptionHandler(RefereeAlreadyExistException.class)
    public ModelAndView refereeAlreadyExistHandler(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("referee_exception", "Referee with that name already exists");
        return new ModelAndView("redirect:/add-referee");
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            AccessDeniedException.class, // не му е позволено/нямам достъп до даден endpoint
            NoResourceFoundException.class, //  несъществуващ endpoint
            MethodArgumentTypeMismatchException.class,
            MissingRequestValueException.class,
            ConnectException.class,
    })
    public ModelAndView handleNotFoundExceptions(Exception exception) {

        return new ModelAndView("not-found");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            MatchCreationException.class
    })
    public ModelAndView handleMatchExceptions(Exception exception) {

        return new ModelAndView("match-creation-exceptions");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleServerError() {
        ModelAndView mav = new ModelAndView("not-found");
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return mav;
    }
}
