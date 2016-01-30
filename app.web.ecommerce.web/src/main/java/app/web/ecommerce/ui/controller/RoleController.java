/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.ui.controller;

import app.web.ecommerce.domain.security.Permission;
import app.web.ecommerce.domain.security.Role;
import app.web.ecommerce.service.SecurityService;
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
 * @author ilham-buru2@bsi
 */

@Controller
@RequestMapping("/admin/role")
public class RoleController extends ExceptionHandlerController {
    
    @Autowired private SecurityService securityService;
    public static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    
    @RequestMapping("/list")
    public ModelMap pageDataRole(){
        return new ModelMap();
    }
    
    @RequestMapping(value="/permissions", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> jsonPermissions(HttpServletResponse httpServletResponse) {
        List<Permission> datas = securityService.findAllPermissions();
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", datas.size());
        result.put("rows", datas);
        return result;
    }
    
    @RequestMapping(value="/json", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> jsonDataRole(Pageable pageable, HttpServletResponse httpServletResponse) {
        Long countRole = securityService.countAllRoles();
        Object[] obj = {pageable.getOffset(), pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()};
        logger.info("DEBUG VALUE PAGEABLE -- Offset[{}] PageNumber[{}] PageSize[{}] Sort[{}]", obj);
        
        List<Role> datas = securityService.findAllRoles(pageable).getContent();
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", countRole);
        result.put("rows", datas);
        return result;
    }
    
    @RequestMapping(value="/json", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> simpanRole(@RequestBody @Valid Role role, BindingResult errors) {
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            logger.debug(role.toString());
            for (Permission p : role.getPermissionSet()) {
                logger.debug(p.toString());
            }
            responseEntity = new ResponseEntity<Object>(securityService.save(role), HttpStatus.CREATED);
        }
        return responseEntity;
    }
    
    @RequestMapping("/json/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Role role(@PathVariable String id) {
        Role r = securityService.findRoleById(id);
        if(r==null){
            logger.warn("Role with id [{}] not found !!", id);
            throw new IllegalStateException("Role with id [" + id + "] not found !!");
        }
        return r;
    }
    
    @RequestMapping(value="/json/{id}", method= RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid Role role, BindingResult errors) {
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            Role r = securityService.findRoleById(id);
            if(r==null){
                logger.warn("Role with id [{}] not found !!", id);
                throw new IllegalStateException("Role with id [" + id + "] not found !!");
            }
            role.setId(r.getId());
            responseEntity = new ResponseEntity<Object>(securityService.save(role), HttpStatus.OK);
        }
        return responseEntity;
    }
    
    @RequestMapping(value="/json/{id}", method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable String id) {
        Role r = securityService.findRoleById(id);
        if(r==null){
            logger.warn("Role with id [{}] not found !!", id);
            throw new IllegalStateException("Role with id [" + id + "] not found !!");
        }
        securityService.delete(r);
    }
    
}
