/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.ui.controller;

import app.web.ecommerce.domain.master.CategoryProduct;
import app.web.ecommerce.domain.master.Product;
import app.web.ecommerce.domain.security.User;
import app.web.ecommerce.domain.transaction.Testimoni;
import app.web.ecommerce.service.MasterService;
import app.web.ecommerce.service.SecurityService;
import app.web.ecommerce.service.TransaksiService;
import app.web.ecommerce.ui.helper.SpringSecurityHelper;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ilham-buru2@bsi
 */

@Controller
@RequestMapping("/user/search")
public class SearchUserController extends ExceptionHandlerController {
    
    private final Logger logger = LoggerFactory.getLogger(PageUserController.class);
    @Autowired private MasterService masterService;
    @Autowired private SecurityService securityService;
    @Autowired private TransaksiService transaksiService;
    
    @RequestMapping("/panel")
    private ModelMap searchPanel(
            @RequestParam(value="cat", required=false) String cat,
            @RequestParam(value="key", required=false) String key) {
        ModelMap mm = new ModelMap();
        mm.addAttribute("cat", cat);
        mm.addAttribute("key", key);
        return mm;
    }
    
    @RequestMapping("/content")
    private ModelMap searchContent(Pageable pageable, 
        @RequestParam(value="cat", required=false) String cat,
        @RequestParam(value="key", required=false) String key) throws Exception{
        
        Long countProduct = masterService.countAllProducts();
        List<Product> list = masterService.findAllProducts(pageable).getContent();
        
        if(StringUtils.hasText(cat)){
            CategoryProduct cp = masterService.findCategoryProductByKode(cat);
            if(cp==null) throw new Exception("CategoryProduct with code " + cat + " not fount !!!");
            
            countProduct = masterService.countAllProductsByCategory(cp);
            list = masterService.findAllProductsByCategory(pageable, cp).getContent();
        }
        
        if(StringUtils.hasText(key)){
            countProduct = masterService.countAllProductsByKeyword(key);
            list = masterService.findAllProductsByKeyword(pageable, key).getContent();
        }
        
        ModelMap mm = new ModelMap();
        mm.put("total", countProduct);
        mm.put("products", list);
        return mm;
    }
    
    @RequestMapping("/detail-content")
    public ModelMap getInfoProduct(@RequestParam(value="id", required=true) String idProduct) throws Exception {
        Product p = masterService.findProductByKode(idProduct);
        User user = securityService.findUserByUsername(SpringSecurityHelper.getCurrentUsername());
        
        if(user==null){
            throw new Exception("Session Invalid");
        }
        
        ModelMap mm = new ModelMap();
        mm.put("product", p);
        mm.put("member", user.getMember());
        return mm;
    }
    
    @RequestMapping(value="/comment", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> comment(@RequestBody @Valid Testimoni testimoni, BindingResult errors) throws Exception{
        ResponseEntity<Object>  responseEntity= null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.OK);
        } else {
            transaksiService.comment(testimoni);
            responseEntity = new ResponseEntity<Object>("Sukses", HttpStatus.OK);
        }
        return responseEntity;
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
    
    @RequestMapping("/categories")
    @ResponseBody
    private List<CategoryProduct> categories(Pageable pageable) throws Exception{
        
        List<CategoryProduct> cat = masterService.findAllCategoryProducts(pageable).getContent();
        return cat;
    }
    
}
