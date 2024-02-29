package com.brewcompanion.brewcomp.utils;

import java.util.Date;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.addObject("timestamp", new Date());
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ModelAndView handleResourceNotFound(HttpServletRequest req, Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "Resource not found");
        mav.addObject("url", req.getRequestURL());
        mav.addObject("timestamp", new Date());
        mav.addObject("code", 404);
        mav.setStatus(HttpStatusCode.valueOf(404));
        mav.setViewName("error");
        return mav;
    }
}