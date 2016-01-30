/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.ui.controller;

import app.web.ecommerce.constant.StatusUser;
import app.web.ecommerce.master.City;
import app.web.ecommerce.master.ShippingRate;
import app.web.ecommerce.security.Role;
import app.web.ecommerce.security.User;
import app.web.ecommerce.service.MasterService;
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
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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
@RequestMapping("/admin/shipping")
public class ShippingController extends ExceptionHandlerController {
    
    @Autowired private MasterService masterService;
    public static final Logger logger = LoggerFactory.getLogger(ShippingController.class);
    
    @RequestMapping("/list")
    public ModelMap pageDataShipping(){
        return new ModelMap();
    }
    
    @RequestMapping(value="/cities", method= RequestMethod.POST)
    @ResponseBody
    public List<City> jsonCities(HttpServletResponse httpServletResponse) {
        return masterService.findAllCities();
    }
    
    @RequestMapping(value="/json", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> jsonDataShipping(Pageable pageable, HttpServletResponse httpServletResponse) {
        Long countShipping = masterService.countAllShippingRates();
        Object[] obj = {pageable.getOffset(), pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()};
        logger.info("DEBUG VALUE PAGEABLE -- Offset[{}] PageNumber[{}] PageSize[{}] Sort[{}]", obj);
        
        List<ShippingRate> datas = masterService.findAllShippingRates(pageable).getContent();
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", countShipping);
        result.put("rows", datas);
        return result;
    }
    
    @RequestMapping(value="/json", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> simpanShipping(@RequestBody @Valid ShippingRate shipping, BindingResult errors) {
        
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            responseEntity = new ResponseEntity<Object>(masterService.save(shipping), HttpStatus.CREATED);
        }
        return responseEntity;
    }
    
    @RequestMapping("/json/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ShippingRate shipping(@PathVariable String id) {
        ShippingRate r = masterService.findShippingRateById(id);
        if(r==null){
            logger.warn("ShippingRate with id [{}] not found !!", id);
            throw new IllegalStateException("ShippingRate with id [" + id + "] not found !!");
        }
        return r;
    }
    
    @RequestMapping(value="/json/{id}", method= RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid ShippingRate shipping, BindingResult errors) {
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            ShippingRate r = masterService.findShippingRateById(id);
            if(r==null){
                logger.warn("ShippingRate with id [{}] not found !!", id);
                throw new IllegalStateException("Shipping with id [" + id + "] not found !!");
            }
            shipping.setId(r.getId());
            responseEntity = new ResponseEntity<Object>(masterService.save(shipping), HttpStatus.OK);
        }
        return responseEntity;
    }
    
    @RequestMapping(value="/json/{id}", method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable String id) {
        ShippingRate r = masterService.findShippingRateById(id);
        if(r==null){
            logger.warn("ShippingRate with id [{}] not found !!", id);
            throw new IllegalStateException("ShippingRate with id [" + id + "] not found !!");
        }
        masterService.delete(r);
    }
    
}
