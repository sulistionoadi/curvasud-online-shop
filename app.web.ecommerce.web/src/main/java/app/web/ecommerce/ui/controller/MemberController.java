/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.ui.controller;

import app.web.ecommerce.constant.DesignationType;
import app.web.ecommerce.constant.StatusUser;
import app.web.ecommerce.domain.master.Member;
import app.web.ecommerce.domain.security.Role;
import app.web.ecommerce.domain.security.User;
import app.web.ecommerce.service.RunningNumberService;
import app.web.ecommerce.service.SecurityService;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
public class MemberController extends ExceptionHandlerController{
    
    @Autowired
    private SecurityService securityService;
    @Autowired
    private RunningNumberService runningNumberService;
    
    private final Logger logger = LoggerFactory.getLogger(MemberController.class);
    
    @RequestMapping("/registrasi/member")
    public ModelMap tampilFormRegistrasiMember(){
        return new ModelMap().addAttribute("member", new Member());
    }
    
    @RequestMapping("/registrasi/sukses")
    public ModelMap tampilFormRegistrasiSukses(){
        return new ModelMap();
    }
    
    @RequestMapping("/registrasi/gagal")
    public ModelMap tampilFormRegistrasiGagal(){
        return new ModelMap();
    }
    
    @ResponseBody
    @RequestMapping(value="/registrasi/member", method= RequestMethod.POST)
    public String saveRegistrasiMember(@RequestBody @Valid Member member, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws IOException{
        if(!member.getConfirmPassword().equals(member.getPassword())){
            bindingResult.rejectValue("confirmPassword", "confirmPassword.notMatch", "Konfirmasi password invalid");
        }
        
        try{
            if(bindingResult.hasErrors()){
                logger.debug("BINDING RESULT HAS ERROR");
                for (ObjectError o : bindingResult.getAllErrors()) {
                    logger.error("Errors Save member [{}]", o.getObjectName() + " | " + o.getDefaultMessage());    
                }
                return "gagal";
            }
        
            Role role = securityService.findRoleByName("MEMBER");
            String encryptedPassword = new Md5PasswordEncoder().encodePassword(member.getPassword(), member.getUsername());

            DateTimeFormatter formatter = DateTimeFormat.forPattern("yy");
            DateTime sekarang = new DateTime();

            String memberCode = "MB" + formatter.print(sekarang) + StringUtils.leftPad(
                    runningNumberService.generateYearlyRunningNumber(sekarang.toDate(), DesignationType.MEMBER), 6, "0");
            member.setMemberCode(memberCode);
            member.setRegistrationDate(new Date());

            User user = new User();
            user.setUsername(member.getUsername());
            user.setPassword(encryptedPassword);
            user.setActive(Boolean.TRUE);
            user.setStatus(StatusUser.ACTIVE);
            user.setRole(role);
            user.setMember(member);

            securityService.save(user);
            return "sukses";
        } catch (Exception ex){
            logger.error(ex.getMessage(), ex);
            return "gagal";
        }
    }
    
}
