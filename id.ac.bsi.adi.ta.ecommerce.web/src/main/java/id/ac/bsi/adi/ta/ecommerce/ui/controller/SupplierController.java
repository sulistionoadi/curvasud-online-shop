/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import id.ac.bsi.adi.ta.ecommerce.domain.master.CategoryProduct;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Supplier;
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
@RequestMapping("/admin/supplier")
public class SupplierController extends ExceptionHandlerController{
    
    @Autowired private MasterService masterService;
    public static final Logger logger = LoggerFactory.getLogger(SupplierController.class);
    
    @RequestMapping("/list")
    public ModelMap pageDataSupplier(){
        return new ModelMap();
    }
    
    @RequestMapping(value="/json", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> jsonDataSupplier(Pageable pageable, HttpServletResponse httpServletResponse) {
        Long countSupplier = masterService.countAllSupplier();
        Object[] obj = {pageable.getOffset(), pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()};
        logger.info("DEBUG VALUE PAGEABLE -- Offset[{}] PageNumber[{}] PageSize[{}] Sort[{}]", obj);
        
        List<Supplier> datas = masterService.findAllSupplier(pageable).getContent();
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", countSupplier);
        result.put("rows", datas);
        return result;
    }
    
    @RequestMapping(value="/json", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> simpan(@RequestBody @Valid Supplier supplier, BindingResult errors) {
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            responseEntity = new ResponseEntity<Object>(masterService.save(supplier), HttpStatus.CREATED);
        }
        return responseEntity;
    }
    
    @RequestMapping("/json/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Supplier supplier(@PathVariable String id) {
        Supplier sp = masterService.findSupplierById(id);
        if(sp==null){
            logger.warn("Supplier with id [{}] not found !!", id);
            throw new IllegalStateException("Supplier with id [" + id + "] not found !!");
        }
        return sp;
    }
    
    @RequestMapping(value="/json/{id}", method= RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid Supplier supplier, BindingResult errors) {
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            Supplier sp = masterService.findSupplierById(id);
            if(sp==null){
                logger.warn("Supplier with id [{}] not found !!", id);
                throw new IllegalStateException("Supplier with id [" + id + "] not found !!");
            }
            supplier.setId(sp.getId());
            responseEntity = new ResponseEntity<Object>(masterService.save(supplier), HttpStatus.OK);
        }
        return responseEntity;
    }
    
    @RequestMapping(value="/json/{id}", method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable String id) {
        Supplier sp = masterService.findSupplierById(id);
        if(sp==null){
            logger.warn("Supplier with id [{}] not found !!", id);
            throw new IllegalStateException("Supplier with id [" + id + "] not found !!");
        }
        masterService.delete(sp);
    }
}
