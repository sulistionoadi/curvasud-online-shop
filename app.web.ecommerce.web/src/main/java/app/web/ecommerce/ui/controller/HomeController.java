/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.ui.controller;

import app.web.ecommerce.master.Product;
import app.web.ecommerce.transaction.Testimoni;
import app.web.ecommerce.service.MasterService;
import app.web.ecommerce.service.TransaksiService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriTemplate;

/**
 *
 * @author ilham-buru2@bsi
 */

@Controller
public class HomeController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired private MasterService masterService;
    @Autowired private TransaksiService transaksiService;
    
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
    
    @RequestMapping("/contact")
    public ModelMap getContact(){
        return new ModelMap();
    }
    
    @RequestMapping("/content")
    public ModelMap getContent(Pageable pageable){
        Long countProduct = masterService.countAllProducts();
        List<Product> list = masterService.findAllProducts(pageable).getContent();
        
        ModelMap mm = new ModelMap();
        mm.put("total", countProduct);
        mm.put("products", list);
        return mm;
    }
    
    @RequestMapping("/detail-content")
    public ModelMap getInfoProduct(@RequestParam(value="id", required=true) String idProduct){
        Product p = masterService.findProductByKode(idProduct);
        
        ModelMap mm = new ModelMap();
        mm.put("product", p);
        return mm;
    }
    
    @RequestMapping("/comment-list")
    public ModelMap listComment(@RequestParam(value="id", required=true) String idProduct, Pageable pageable) throws Exception {
        Product p = masterService.findProductByKode(idProduct);
        
        if(p==null){
            throw new Exception("Product not found !!");
        }
        
        Long countComment = transaksiService.countByProduct(p);
        List<Testimoni> list = transaksiService.findTestimoniByProduct(p, pageable).getContent();
        
        ModelMap mm = new ModelMap();
        mm.put("total", countComment);
        mm.put("comments", list);
        return mm;
    }
    
    @RequestMapping("/login/success")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin/page/home";
        }
        return "redirect:/user/page/home";
    }
    
}
