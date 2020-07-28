package com.logex.demo.config.exception;

import com.logex.demo.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ControllerAdvice
public class CustomExceptionHandler {

//    private static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(
            {AuthenticationException.class, AuthenticationServiceException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView exception(HttpServletRequest req, final Throwable throwable, final Model model) {
//        logger.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", errorMessage);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }


    @ExceptionHandler({DuplicateRecordException.class})
    protected ModelAndView handleException(HttpServletRequest req,DuplicateRecordException dre) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", dre.getMessage());
        mav.addObject("status",HttpStatus.CONFLICT);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler({RecordNotFoundException.class})
    protected ModelAndView handleException(HttpServletRequest req,RecordNotFoundException rnfe) {
//        return new ResponseEntity<>(new ResponseDto(rnfe.getMessage(), true), HttpStatus.NOT_FOUND);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", rnfe.getMessage());
        mav.addObject("status",HttpStatus.NOT_FOUND);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler({BadRequestException.class})
    protected ModelAndView handleException(HttpServletRequest req,BadRequestException bre) {
//        return new ResponseEntity<>(new ResponseDto(bre.getMessage(), true), HttpStatus.BAD_REQUEST);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", bre.getMessage());
        mav.addObject("status",HttpStatus.BAD_REQUEST);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler({BaseException.class})
    protected ModelAndView handleException(HttpServletRequest req,BaseException ae) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ae.getMessage());
        mav.addObject("status",HttpStatus.INTERNAL_SERVER_ERROR);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler({IOException.class})
    protected ModelAndView handleIOException(HttpServletRequest req,BaseException ioe) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ioe.getMessage());
        mav.addObject("status",HttpStatus.FORBIDDEN);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler({DbConnectionException.class})
    protected ModelAndView handleException(HttpServletRequest req,DbConnectionException dbce) {
//        return new ResponseEntity<>(new ResponseDto(dbce.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", dbce.getMessage());
        mav.addObject("status",HttpStatus.INTERNAL_SERVER_ERROR);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }


}