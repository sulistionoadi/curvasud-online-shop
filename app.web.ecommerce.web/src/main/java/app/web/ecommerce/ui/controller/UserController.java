/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.ui.controller;

import app.web.ecommerce.constant.StatusUser;
import app.web.ecommerce.security.Role;
import app.web.ecommerce.security.User;
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
 * @author ilham-buru2@bsi
 */

@Controller
@RequestMapping("/admin/user")
public class UserController extends ExceptionHandlerController {
    
    @Autowired private SecurityService securityService;
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @RequestMapping("/list")
    public ModelMap pageDataUser(){
        return new ModelMap();
    }
    
    @RequestMapping(value="/roles", method= RequestMethod.POST)
    @ResponseBody
    public List<Role> jsonRoles(HttpServletResponse httpServletResponse) {
        List<Role> roles = securityService.findAllRoles();
        for (Role r : roles) {
            r.setPermissionSet(null);
        }
        return roles;
    }
    
    @RequestMapping(value="/json", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> jsonDataUser(Pageable pageable, HttpServletResponse httpServletResponse) {
        Long countUser = securityService.countAllUsers();
        Object[] obj = {pageable.getOffset(), pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()};
        logger.info("DEBUG VALUE PAGEABLE -- Offset[{}] PageNumber[{}] PageSize[{}] Sort[{}]", obj);
        
        List<User> datas = securityService.findAllUsers(pageable).getContent();
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", countUser);
        result.put("rows", datas);
        return result;
    }
    
    @RequestMapping(value="/json", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> simpanUser(@RequestBody @Valid User user, BindingResult errors) {
        if(!user.getConfirm().isEmpty()){
            if(!user.getPassword().equals(user.getConfirm())){
                errors.rejectValue("password", "user.password.invalid", "Password not match !!");
            }
        } else {
            errors.rejectValue("password", "user.password.required", "Please fill password confirmation !!");
        }
        
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            String encrypted = new Md5PasswordEncoder().encodePassword(user.getPassword(), user.getUsername());
            user.setPassword(encrypted);
            if(user.getActive()){
                user.setStatus(StatusUser.ACTIVE);
            } else {
                user.setStatus(StatusUser.BLOCK);
            }
            responseEntity = new ResponseEntity<Object>(securityService.save(user), HttpStatus.CREATED);
        }
        return responseEntity;
    }
    
    @RequestMapping("/json/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public User user(@PathVariable String id) {
        User r = securityService.findUserById(id);
        if(r==null){
            logger.warn("User with id [{}] not found !!", id);
            throw new IllegalStateException("User with id [" + id + "] not found !!");
        }
        return r;
    }
    
    @RequestMapping(value="/json/{id}", method= RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid User user, BindingResult errors) {
        logger.info("IN METHOD UPDATE USER");
        User r = securityService.findUserById(id);
        
        if(!user.getConfirm().isEmpty()){
            if(!user.getPassword().equals(user.getConfirm())){
                errors.rejectValue("password", "user.password.invalid", "Password not match !!");
            } else {
                String encrypted = new Md5PasswordEncoder().encodePassword(user.getPassword(), user.getUsername());
                user.setPassword(encrypted);
            }
        } else {
            user.setPassword(r.getPassword());
        }
        
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            logger.info("SEARCH USER WITH ID " + id);
            if(r==null){
                logger.warn("User with id [{}] not found !!", id);
                throw new IllegalStateException("User with id [" + id + "] not found !!");
            }
            user.setId(r.getId());
            if(user.getActive()){
                user.setStatus(StatusUser.ACTIVE);
            } else {
                user.setStatus(StatusUser.BLOCK);
            }
            responseEntity = new ResponseEntity<Object>(securityService.save(user), HttpStatus.OK);
        }
        return responseEntity;
    }
    
    @RequestMapping(value="/json/{id}", method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable String id) {
        User r = securityService.findUserById(id);
        if(r==null){
            logger.warn("User with id [{}] not found !!", id);
            throw new IllegalStateException("User with id [" + id + "] not found !!");
        }
        securityService.delete(r);
    }
    
}
