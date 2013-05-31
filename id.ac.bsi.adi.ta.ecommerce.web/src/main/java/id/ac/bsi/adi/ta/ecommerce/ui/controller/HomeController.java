/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author adi
 */

@Controller
public class HomeController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @RequestMapping("/home")
    public void getHome(){
        logger.info("Controller home dipanggil");
    }
}
