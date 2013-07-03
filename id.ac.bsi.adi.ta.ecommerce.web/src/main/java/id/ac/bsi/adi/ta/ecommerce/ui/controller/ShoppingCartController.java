/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import id.ac.bsi.adi.ta.ecommerce.domain.security.User;
import id.ac.bsi.adi.ta.ecommerce.dto.ShoppingCartDTO;
import id.ac.bsi.adi.ta.ecommerce.service.MasterService;
import id.ac.bsi.adi.ta.ecommerce.service.SecurityService;
import id.ac.bsi.adi.ta.ecommerce.ui.helper.SpringSecurityHelper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author adi
 */

@Controller
@RequestMapping("/user/cart")
public class ShoppingCartController extends ExceptionHandlerController {
    
    
    @Autowired private MasterService masterService;
    @Autowired private SecurityService securityService;
    
    private final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);
    private final String SESSION_KEY_CART = "sessionShoppingCart";
    
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String addCart(
            @RequestParam(value="id", required=true) String idProduct, 
            @RequestParam(value="q", required=true) Integer qty, 
            HttpSession session) throws Exception{
        User user = securityService.findUserByUsername(SpringSecurityHelper.getCurrentUsername());
        if(user.getMember() == null){
            throw new Exception("invalid session or member is null");
        }
        
        Product p = masterService.findProductById(idProduct);
        if(p == null){
            throw new Exception("product is null");
        }
        
        List<ShoppingCartDTO> shoppingCart = new ArrayList<ShoppingCartDTO>();
        if(session.getAttribute(SESSION_KEY_CART)!=null){
            shoppingCart = (List<ShoppingCartDTO>) session.getAttribute(SESSION_KEY_CART);
        }
        
        ShoppingCartDTO dto = new ShoppingCartDTO();
        dto.setMember(user.getMember());
        dto.setProduct(p);
        dto.setQty(qty);
        dto.setTotal(new BigDecimal(dto.getQty()).multiply(p.getPrice()));
        
        shoppingCart.add(dto);
        
        session.setAttribute(SESSION_KEY_CART, shoppingCart);
        
        return "redirect:list";
    }
    
    @RequestMapping(value="/list", method= RequestMethod.GET)
    public ModelMap addCart(HttpSession session) throws Exception{
        
        List<ShoppingCartDTO> shoppingCart = new ArrayList<ShoppingCartDTO>();
        if(session.getAttribute(SESSION_KEY_CART)!=null){
            shoppingCart = (List<ShoppingCartDTO>) session.getAttribute(SESSION_KEY_CART);
        }
        
        return new ModelMap("shoppingCartDTO", shoppingCart);
        
    }
    
}
