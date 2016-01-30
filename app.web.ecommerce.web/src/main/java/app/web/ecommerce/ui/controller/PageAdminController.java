/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.ui.controller;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ilham-buru2@bsi
 */

@Controller
@RequestMapping("/admin/page")
public class PageAdminController extends ExceptionHandlerController {
    
    private final Logger logger = LoggerFactory.getLogger(PageAdminController.class);
    
    @RequestMapping("/home")
    private ModelMap homeUser(HttpSession session) throws Exception{
        
        return new ModelMap();
    }
    
}
