/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import id.ac.bsi.adi.ta.ecommerce.domain.security.User;
import id.ac.bsi.adi.ta.ecommerce.service.SecurityService;
import id.ac.bsi.adi.ta.ecommerce.ui.helper.ChangePasswordHelper;
import id.ac.bsi.adi.ta.ecommerce.ui.helper.SpringSecurityHelper;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jimmy
 */
@Controller
@RequestMapping("/admin/setting")
public class ChangePasswordController {
    
    private final Logger logger = LoggerFactory.getLogger(PageUserController.class);
    @Autowired private SecurityService securityService;
    
    @RequestMapping("/changePassword")
    public ModelMap pageDataApproval() {
        return new ModelMap();
    }
    
    @RequestMapping(value="/json", method= RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> update(@RequestBody @Valid ChangePasswordHelper changePasswordHelper, BindingResult errors) {
        if(!changePasswordHelper.getConfirmNewPassword().isEmpty()){
            if(!changePasswordHelper.getNewPassword().equals(changePasswordHelper.getConfirmNewPassword())){
                errors.rejectValue("newPassword", "changePasswordHelper.newPassword.invalid", "Password not match !!");
            }
        } else {
            errors.rejectValue("confirmNewPassword", "changePasswordHelper.confirmNewPassword.required", "Please fill password confirmation !!");
        }
        
        String username = SpringSecurityHelper.getCurrentUsername();
        User user = securityService.findUserByUsername(username);
        
        ResponseEntity<Object> responseEntity = null;
        if(user != null) {
            String oldEncrypted = new Md5PasswordEncoder().encodePassword(changePasswordHelper.getOldPassword(), user.getUsername());
            if(!oldEncrypted.equals(user.getPassword())) {
                errors.rejectValue("oldPassword", "changePasswordHelper.oldPassword.invalid", "Old password doesn't match");
            }
            
            if(errors.hasErrors()){
                responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
            } else {
                String newEncrypted = new Md5PasswordEncoder().encodePassword(changePasswordHelper.getNewPassword(), user.getUsername());
                user.setPassword(newEncrypted);
                securityService.save(user);
                
                responseEntity = new ResponseEntity<Object>(securityService.save(user), HttpStatus.OK);
            }
        } else {
            logger.warn("User with name [{}] not found !!", username);
            throw new IllegalStateException("User with name [" + username + "] not found !!");
        }

        return responseEntity;
    }
    
}