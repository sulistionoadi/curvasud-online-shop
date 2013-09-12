/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import id.ac.bsi.adi.ta.ecommerce.domain.master.CategoryProduct;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import id.ac.bsi.adi.ta.ecommerce.service.MasterService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author adi
 */

@Controller
@RequestMapping("/user/search")
public class SearchUserController extends ExceptionHandlerController {
    
    private final Logger logger = LoggerFactory.getLogger(PageUserController.class);
    @Autowired private MasterService masterService;
    
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
    public ModelMap getInfoProduct(@RequestParam(value="id", required=true) String idProduct){
        Product p = masterService.findProductByKode(idProduct);
        
        ModelMap mm = new ModelMap();
        mm.put("product", p);
        return mm;
    }
    
    @RequestMapping("/categories")
    @ResponseBody
    private List<CategoryProduct> categories(Pageable pageable) throws Exception{
        
        List<CategoryProduct> cat = masterService.findAllCategoryProducts(pageable).getContent();
        return cat;
    }
    
}
