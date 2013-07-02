/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import id.ac.bsi.adi.ta.ecommerce.service.MasterService;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriTemplate;

/**
 *
 * @author adi
 */

@Controller
public class HomeController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired private MasterService masterService;
    
    @RequestMapping("/awal")
    public String getAwal(HttpServletRequest request, HttpServletResponse response){
        logger.info("Controller awal dipanggil");
        String requestUrl = request.getHeader("Referer");
        logger.info("Request URL nya adalah " + requestUrl);
        
        URI uri = new UriTemplate("{requestUrl}{home}").expand(requestUrl, "home");
        logger.info("Redirect URI " + uri.toASCIIString());
        response.setHeader("Location", uri.toASCIIString());
        
        
        return "redirect:/home";
    }
    
    @RequestMapping("/home")
    public ModelMap getHome(){
        logger.info("Controller home dipanggil");
        return new ModelMap();
    }
    
    @RequestMapping("/content")
    public ModelMap getContent(Pageable pageable){
        logger.info("Controller home dipanggil");
        Long countProduct = masterService.countAllProducts();
        List<Product> list = masterService.findAllProducts(pageable).getContent();
        
        ModelMap mm = new ModelMap();
        mm.put("total", countProduct);
        mm.put("products", list);
        return mm;
    }
    
    @RequestMapping("/login/success")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin/category/list";
        }
        return "redirect:/registrasi/member";
    }
    
}
