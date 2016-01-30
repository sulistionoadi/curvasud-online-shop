/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.ui.controller;

import app.web.ecommerce.domain.master.Member;
import app.web.ecommerce.domain.security.User;
import app.web.ecommerce.service.MasterService;
import app.web.ecommerce.service.SecurityService;
import app.web.ecommerce.ui.helper.ChangePasswordHelper;
import app.web.ecommerce.ui.helper.SpringSecurityHelper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author jimmy
 */
@Controller
@RequestMapping("/user/setting")
public class UserProfileController {
    
    private final Logger logger = LoggerFactory.getLogger(PageUserController.class);
    @Autowired private SecurityService securityService;
    @Autowired private MasterService masterService;
    
    @RequestMapping("/password")
    public ModelMap pageDataApproval() {
        return new ModelMap();
    }
    
    @RequestMapping("/profile")
    public ModelMap pageDataApproval(HttpSession session) throws Exception {
        
        String username = SpringSecurityHelper.getCurrentUsername();
        User user = securityService.findUserByUsername(username);
        if(user==null) throw new Exception("User dengan username ["+username+"] tidak ditemukan");
        if(user.getMember()==null) throw new Exception("Member dengan username ["+username+"] tidak ditemukan");
        
        return new ModelMap("member", user.getMember());
    }
    
    @RequestMapping(value="/json", method= RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> update(@RequestBody @Valid ChangePasswordHelper changePasswordHelper, BindingResult errors) {
        if(!changePasswordHelper.getConfirmNewPassword().isEmpty()){
            if(!changePasswordHelper.getNewPassword().equals(changePasswordHelper.getConfirmNewPassword())){
                errors.rejectValue("password", "user.password.invalid", "Password not match !!");
            }
        } else {
            errors.rejectValue("password", "user.password.required", "Please fill password confirmation !!");
        }
        
        String username = SpringSecurityHelper.getCurrentUsername();
        User user = securityService.findUserByUsername(username);
        
        ResponseEntity<Object> responseEntity = null;
        if(user != null) {
            String oldEncrypted = new Md5PasswordEncoder().encodePassword(changePasswordHelper.getOldPassword(), user.getUsername());
            if(!oldEncrypted.equals(user.getPassword())) {
                errors.rejectValue("password", "user.password.invalid", "Old password doesn't match");
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
    
    @RequestMapping(value="/member", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUpdateProfileMember(@RequestBody @Valid Member member, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws IOException{
        try{        
            
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yy");
            DateTime sekarang = new DateTime();

            Member memberdb = masterService.findMemberByKode(member.getMemberCode());
            member.setMemberCode(memberdb.getMemberCode());
            
            masterService.save(member);
            response.sendRedirect(request.getContextPath() + "/register/sukses");
        } catch (Exception ex){
            response.sendRedirect(request.getContextPath() + "/register/gagal");
        }
    }
    
}