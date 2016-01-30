/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.ui.controller;

import app.web.ecommerce.domain.security.User;
import app.web.ecommerce.service.SecurityService;
import app.web.ecommerce.ui.helper.SpringSecurityHelper;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ilham-buru2@bsi
 */

@Controller
@RequestMapping("/user/page")
public class PageUserController extends ExceptionHandlerController {
    
    private final Logger logger = LoggerFactory.getLogger(PageUserController.class);
    
    @Autowired private SecurityService securityService;
    
    
    @RequestMapping("/home")
    private ModelMap homeUser(HttpSession session) throws Exception{
        
        String username = SpringSecurityHelper.getCurrentUsername();
        
        User user = securityService.findUserByUsername(username);
        
        if(user.getMember()==null){
            throw new Exception("Data member untuk username " + user.getUsername() + " tidak ada !!!");
        }
        
        ModelMap mm = new ModelMap();
        mm.addAttribute("member", user.getMember());
        
        return mm;
    }
    
}
