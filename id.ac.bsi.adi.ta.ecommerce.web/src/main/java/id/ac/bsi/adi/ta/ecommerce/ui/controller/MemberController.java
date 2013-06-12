/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import id.ac.bsi.adi.ta.ecommerce.constant.StatusUser;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Member;
import id.ac.bsi.adi.ta.ecommerce.domain.security.Role;
import id.ac.bsi.adi.ta.ecommerce.domain.security.User;
import id.ac.bsi.adi.ta.ecommerce.service.MasterService;
import id.ac.bsi.adi.ta.ecommerce.service.SecurityService;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author adi
 */

@Controller
public class MemberController {
    
    @Autowired
    private MasterService masterService;
    @Autowired
    private SecurityService securityService;
    
    @RequestMapping("/registrasi/member")
    public ModelMap tampilFormRegistrasiMember(){
        return new ModelMap().addAttribute("member", new Member());
    }
    
    @RequestMapping(value="/registrasi/member", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String saveRegistrasiMember(@ModelAttribute @Valid Member member, BindingResult bindingResult){
        
        if(!member.getConfirmPassword().equals(member.getPassword())){
            bindingResult.rejectValue("confirmPassword", "confirmPassword.notMatch", "Konfirmasi password invalid");
        }
        
        if(bindingResult.hasErrors()){
            return "/registrasi/member";
        }
        
        Role role = securityService.findRoleByName("MEMBER");
        String encryptedPassword = new Md5PasswordEncoder().encodePassword(member.getPassword(), member.getUsername());
        
        User user = new User();
        user.setUsername(member.getUsername());
        user.setPassword(encryptedPassword);
        user.setActive(Boolean.TRUE);
        user.setStatus(StatusUser.ACTIVE);
        user.setRole(role);
        user.setMember(member);
        
        member.setRegistrationDate(new Date());
        masterService.register(member, user);
        
        return "/registrasi/sukses";
    }
    
}
