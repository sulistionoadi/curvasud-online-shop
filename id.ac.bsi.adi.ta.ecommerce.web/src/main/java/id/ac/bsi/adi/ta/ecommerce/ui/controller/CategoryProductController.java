/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import id.ac.bsi.adi.ta.ecommerce.domain.master.CategoryProduct;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author adi
 */

@Controller
public class CategoryProductController extends ExceptionHandlerController{
    
    @Autowired private MasterService masterService;
    public static final Logger logger = LoggerFactory.getLogger(CategoryProductController.class);
    
    @RequestMapping("/admin/category/list")
    public ModelMap pageDataKategori(){
        return new ModelMap();
    }
    
    @RequestMapping(value="/admin/category/json", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> jsonDataKategori(Pageable pageable, HttpServletResponse httpServletResponse) {
        Long countCategory = masterService.countAllCategoryProuducts();
        Object[] obj = {pageable.getOffset(), pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()};
        logger.info("DEBUG VALUE PAGEABLE -- Offset[{}] PageNumber[{}] PageSize[{}] Sort[{}]", obj);
        
        List<CategoryProduct> datas = masterService.findAllCategoryProducts(pageable).getContent();
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", countCategory);
        result.put("rows", datas);
        return result;
    }
    
    @RequestMapping(value="/admin/category/json", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> simpanKategori(@RequestBody @Valid CategoryProduct categoryProduct, BindingResult errors) {
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            responseEntity = new ResponseEntity<Object>(masterService.save(categoryProduct), HttpStatus.CREATED);
        }
        return responseEntity;
    }
    
    @RequestMapping("/admin/category/json/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CategoryProduct kategori(@PathVariable String id) {
        CategoryProduct cp = masterService.findCategoryProductById(id);
        if(cp==null){
            logger.warn("CategoryProduct with id [{}] not found !!", id);
            throw new IllegalStateException("CategoryProduct with id [" + id + "] not found !!");
        }
        return cp;
    }
    
    @RequestMapping(value="/admin/category/json/{id}", method= RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid CategoryProduct categoryProduct, BindingResult errors) {
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            CategoryProduct cp = masterService.findCategoryProductById(id);
            if(cp==null){
                logger.warn("CategoryProduct with id [{}] not found !!", id);
                throw new IllegalStateException("CategoryProduct with id [" + id + "] not found !!");
            }
            categoryProduct.setId(cp.getId());
            responseEntity = new ResponseEntity<Object>(masterService.save(categoryProduct), HttpStatus.OK);
        }
        return responseEntity;
    }
    
    @RequestMapping(value="/admin/category/json/{id}", method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable String id) {
        CategoryProduct cp = masterService.findCategoryProductById(id);
        if(cp==null){
            logger.warn("CategoryProduct with id [{}] not found !!", id);
            throw new IllegalStateException("CategoryProduct with id [" + id + "] not found !!");
        }
        masterService.delete(cp);
    }
}
