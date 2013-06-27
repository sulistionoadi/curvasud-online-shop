/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import id.ac.bsi.adi.ta.ecommerce.domain.master.CategoryProduct;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import id.ac.bsi.adi.ta.ecommerce.service.MasterService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author adi
 */

@Controller
@RequestMapping("/admin/product")
public class ProductController extends ExceptionHandlerController{
    
    @Autowired private MasterService masterService;
    public static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    
    @RequestMapping("/list")
    public ModelMap pageDataProduct(){
        return new ModelMap();
    }
    
    @RequestMapping(value="/json", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> jsonDataProduct(Pageable pageable, HttpServletResponse httpServletResponse) {
        Long countProduct = masterService.countAllProuducts();
        Object[] obj = {pageable.getOffset(), pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()};
        logger.info("DEBUG VALUE PAGEABLE -- Offset[{}] PageNumber[{}] PageSize[{}] Sort[{}]", obj);
        
        List<Product> datas = masterService.findAllProducts(pageable).getContent();
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", countProduct);
        result.put("rows", datas);
        return result;
    }
    
    @RequestMapping(value="/json", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> simpanProduct(@RequestBody @Valid Product product, BindingResult errors) {
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            responseEntity = new ResponseEntity<Object>(masterService.save(product), HttpStatus.CREATED);
        }
        return responseEntity;
    }
    
    @RequestMapping("/json/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Product product(@PathVariable String id) {
        Product p = masterService.findProductById(id);
        if(p==null){
            logger.warn("Product with id [{}] not found !!", id);
            throw new IllegalStateException("Product with id [" + id + "] not found !!");
        }
        return p;
    }
    
    @RequestMapping("/cat/{kode}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CategoryProduct category(@PathVariable String kode) {
        CategoryProduct cp = masterService.findCategoryProductByKode(kode);
        if(cp==null){
            logger.warn("CategoryProduct with id [{}] not found !!", cp);
            throw new IllegalStateException("CategoryProduct with id [" + cp + "] not found !!");
        }
        return cp;
    }
    
    @RequestMapping(value="/json/{id}", method= RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid Product product, BindingResult errors) {
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            Product p = masterService.findProductById(id);
            if(p==null){
                logger.warn("Product with id [{}] not found !!", id);
                throw new IllegalStateException("Product with id [" + id + "] not found !!");
            }
            product.setId(p.getId());
            responseEntity = new ResponseEntity<Object>(masterService.save(product), HttpStatus.OK);
        }
        return responseEntity;
    }
    
    @RequestMapping(value="/json/{id}", method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable String id) {
        Product p = masterService.findProductById(id);
        if(p==null){
            logger.warn("Product with id [{}] not found !!", id);
            throw new IllegalStateException("Product with id [" + id + "] not found !!");
        }
        masterService.delete(p);
    }
    
}
