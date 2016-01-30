 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.ui.controller;

import app.web.ecommerce.master.CategoryProduct;
import app.web.ecommerce.master.City;
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
@RequestMapping("/admin/city")
public class CityController extends ExceptionHandlerController{
    
    @Autowired private MasterService masterService;
    public static final Logger logger = LoggerFactory.getLogger(CityController.class);
    
    @RequestMapping("/list")
    public ModelMap pageDataCity(){
        return new ModelMap();
    }
    
    @RequestMapping(value="/json", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> jsonDataCity(Pageable pageable, HttpServletResponse httpServletResponse) {
        Long countCity = masterService.countAllCities();
        Object[] obj = {pageable.getOffset(), pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()};
        logger.info("DEBUG VALUE PAGEABLE -- Offset[{}] PageNumber[{}] PageSize[{}] Sort[{}]", obj);
        
        List<City> datas = masterService.findAllCities(pageable).getContent();
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", countCity);
        result.put("rows", datas);
        return result;
    }
    
    @RequestMapping(value="/json", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> simpanCity(@RequestBody @Valid City city, BindingResult errors) {
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            responseEntity = new ResponseEntity<Object>(masterService.save(city), HttpStatus.CREATED);
        }
        return responseEntity;
    }
    
    @RequestMapping("/json/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public City kategori(@PathVariable String id) {
        City cp = masterService.findCityByKode(id);
        if(cp==null){
            logger.warn("City with kode [{}] not found !!", id);
            throw new IllegalStateException("City with kode [" + id + "] not found !!");
        }
        return cp;
    }
    
    @RequestMapping(value="/json/{id}", method= RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid City city, BindingResult errors) {
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            City cp = masterService.findCityByKode(id);
            if(cp==null){
                logger.warn("City with Kode [{}] not found !!", id);
                throw new IllegalStateException("City with id [" + id + "] not found !!");
            }
            city.setCode(cp.getCode());
            responseEntity = new ResponseEntity<Object>(masterService.save(city), HttpStatus.OK);
        }
        return responseEntity;
    }
    
    @RequestMapping(value="/json/{id}", method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable String id) {
        City cp = masterService.findCityByKode(id);
        if(cp==null){
            logger.warn("City with id [{}] not found !!", id);
            throw new IllegalStateException("City with id [" + id + "] not found !!");
        }
        masterService.delete(cp);
    }
}
