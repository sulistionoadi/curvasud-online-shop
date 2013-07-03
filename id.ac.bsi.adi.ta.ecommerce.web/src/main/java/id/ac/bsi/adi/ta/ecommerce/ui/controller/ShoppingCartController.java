/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import id.ac.bsi.adi.ta.ecommerce.constant.DesignationType;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import id.ac.bsi.adi.ta.ecommerce.domain.master.ShippingRate;
import id.ac.bsi.adi.ta.ecommerce.domain.security.User;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Booking;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.BookingDetail;
import id.ac.bsi.adi.ta.ecommerce.dto.ShoppingCartDTO;
import id.ac.bsi.adi.ta.ecommerce.service.MasterService;
import id.ac.bsi.adi.ta.ecommerce.service.RunningNumberService;
import id.ac.bsi.adi.ta.ecommerce.service.SecurityService;
import id.ac.bsi.adi.ta.ecommerce.service.TransaksiService;
import id.ac.bsi.adi.ta.ecommerce.ui.helper.SpringSecurityHelper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author adi
 */

@Controller
@RequestMapping("/user/cart")
public class ShoppingCartController extends ExceptionHandlerController {
    
    
    @Autowired private MasterService masterService;
    @Autowired private SecurityService securityService;
    @Autowired private RunningNumberService runningNumberService;
    @Autowired private TransaksiService transaksiService;
    
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
        
        ShippingRate sr = masterService.findShippingRateByCity(user.getMember().getCity());
        if(sr == null){
            throw new Exception("Shipping Cost is null");
        }
        
        List<ShoppingCartDTO> shoppingCart = new ArrayList<ShoppingCartDTO>();
        if(session.getAttribute(SESSION_KEY_CART)!=null){
            shoppingCart = (List<ShoppingCartDTO>) session.getAttribute(SESSION_KEY_CART);
        }
        
        ShoppingCartDTO dto = new ShoppingCartDTO();
        dto.setMember(user.getMember());
        dto.setProduct(p);
        dto.setQty(qty);
        dto.setOngkir(sr.getReguler());
        dto.setTotal(new BigDecimal(dto.getQty()).multiply(p.getPrice()));
        
        shoppingCart.add(dto);
        
        session.setAttribute(SESSION_KEY_CART, shoppingCart);
        
        return "redirect:list";
    }
    
    @RequestMapping(value="/checkout", method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> checkout(
            @RequestParam(value="s_name", required=true) String shippingName, 
            @RequestParam(value="s_address", required=true) String shippingAddress, 
            @RequestParam(value="s_phone", required=true) String shippingPhone, 
            @RequestParam(value="s_cost", required=true) BigDecimal shippingCost, 
            HttpSession session) throws Exception{
        
        User user = securityService.findUserByUsername(SpringSecurityHelper.getCurrentUsername());
        if(user.getMember() == null){
            throw new Exception("invalid session or member is null");
        }
        
        List<ShoppingCartDTO> shoppingCart = new ArrayList<ShoppingCartDTO>();
        if(session.getAttribute(SESSION_KEY_CART)!=null){
            shoppingCart = (List<ShoppingCartDTO>) session.getAttribute(SESSION_KEY_CART);
        }
        
        List<BookingDetail> details = new ArrayList<BookingDetail>();
        DateTimeFormatter blth = DateTimeFormat.forPattern("yyMM");
        DateTime sekarang = new DateTime();
        
        String bookCode = "PO" + blth.print(sekarang) + StringUtils.leftPad(runningNumberService.generateMonthlyRunningNumber(sekarang.toDate(), DesignationType.BOOKING), 4, "0");
        Booking booking = new Booking();
        booking.setBookingCode(bookCode);
        booking.setMember(user.getMember());
        booking.setTransactionDate(new Date());
        booking.setShippingAddress(shippingAddress);
        booking.setShippingCost(shippingCost);
        booking.setShippingName(shippingName);
        booking.setShippingPhone(shippingPhone);
        
        for (ShoppingCartDTO sd : shoppingCart) {
            BookingDetail bd = new BookingDetail();
            bd.setBooking(booking);
            bd.setAmount(sd.getProduct().getPrice());
            bd.setProduct(sd.getProduct());
            bd.setQty(sd.getQty());
            bd.setTotalAmount(sd.getTotal());
            details.add(bd);
        }
        booking.setBookingDetails(details);
        
        session.removeAttribute(SESSION_KEY_CART);
        Booking b = transaksiService.save(booking);
        
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("redirect", "sukses?id="+b.getId());
        return result;
    }
    
    @RequestMapping(value="/list", method= RequestMethod.GET)
    public ModelMap listCart(HttpSession session) throws Exception{
        
        List<ShoppingCartDTO> shoppingCart = new ArrayList<ShoppingCartDTO>();
        if(session.getAttribute(SESSION_KEY_CART)!=null){
            shoppingCart = (List<ShoppingCartDTO>) session.getAttribute(SESSION_KEY_CART);
        }
        
        return new ModelMap("shoppingCartDTO", shoppingCart);
        
    }
    
    @RequestMapping(value="/sukses", method= RequestMethod.GET)
    public ModelMap sukses(@RequestParam(value="id", required=true) String id) throws Exception{
        
        return new ModelMap("idBooking", id);
        
    }
    
}