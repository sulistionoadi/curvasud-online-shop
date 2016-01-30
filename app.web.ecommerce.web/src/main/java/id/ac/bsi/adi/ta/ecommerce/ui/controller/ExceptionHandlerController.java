/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author adi
 */

public class ExceptionHandlerController {
    public static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);
    
    @ExceptionHandler({IllegalStateException.class})
    @ResponseBody
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex) {
        logger.error("Terjadi Exception [ " + ex.getMessage() + " ] ", ex);
        HttpHeaders header = new HttpHeaders();
        header.add("ErrorCause", ex.getMessage());
        header.add("ErrorClass", ex.getClass().getName());
        return new ResponseEntity<Object>(ex.getMessage(), header, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<Object> handleExeception(IllegalStateException ex) {
        logger.error("Terjadi Exception [ " + ex.getMessage() + " ] ", ex);
        HttpHeaders header = new HttpHeaders();
        header.add("ErrorCause", ex.getMessage());
        header.add("ErrorClass", ex.getClass().getName());
        return new ResponseEntity<Object>(ex.getMessage(), header, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
}
