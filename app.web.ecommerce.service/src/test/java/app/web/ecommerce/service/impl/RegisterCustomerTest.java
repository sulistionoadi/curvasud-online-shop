/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.service.impl;

import app.web.ecommerce.constant.DesignationType;
import app.web.ecommerce.constant.StatusUser;
import app.web.ecommerce.master.City;
import app.web.ecommerce.master.Member;
import app.web.ecommerce.security.Role;
import app.web.ecommerce.security.User;
import app.web.ecommerce.service.MasterService;
import app.web.ecommerce.service.RunningNumberService;
import app.web.ecommerce.service.SecurityService;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 *
 * @author ilham-buru2@bsi
 */
public class RegisterCustomerTest {
    
    private static AbstractApplicationContext ctx;
    private static MasterService masterService;
    private static RunningNumberService runningNumberService;
    private static SecurityService securityService;
    
    @BeforeClass
    public static void setUpClass() {
        ctx = new ClassPathXmlApplicationContext("classpath*:id/ac/bsi/adi/ta/ecommerce/**/applicationContext.xml");
        masterService = (MasterService) ctx.getBean("masterService");
        runningNumberService = (RunningNumberService) ctx.getBean("runningNumberService");
        securityService = (SecurityService) ctx.getBean("securityService");
    }
    
    @AfterClass
    public static void tearDownClass() {
        ctx.registerShutdownHook();
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void register() {
         DateTimeFormatter formatter = DateTimeFormat.forPattern("yy");
         DateTime sekarang = new DateTime();
         String memberCode = "MB" + formatter.print(sekarang) + runningNumberService.generateYearlyRunningNumber(sekarang.toDate(), DesignationType.MEMBER);
         City c = masterService.findCityByKode("JAK");
                 
         Member member = new Member();
         member.setFirstname("AWEY");
         member.setLastname("BUSWAY");
         member.setEmail("awey@gmail.com");
         member.setCity(c);
         member.setAddress("Tanah Ireng 111");
         member.setMemberCode(memberCode);
         member.setMobile("08768747877");
         member.setPhone("01212434");
         member.setProvince("Jakarta Timur");
         member.setRegistrationDate(new Date());
         member.setZipCode("13620");

        Role role = securityService.findRoleByName("MEMBER");
        String encryptedPassword = new Md5PasswordEncoder().encodePassword("123456", "aweybb");
        
        User user = new User();
        user.setUsername("aweybb");
        user.setPassword(encryptedPassword);
        user.setActive(Boolean.TRUE);
        user.setStatus(StatusUser.ACTIVE);
        user.setRole(role);
        user.setMember(member);
        
        securityService.save(user);
        
//        masterService.register(member, user);
     }
}
