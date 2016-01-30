/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.ui.controller;

import app.web.ecommerce.master.Product;
import app.web.ecommerce.master.Supplier;
import app.web.ecommerce.security.Role;
import app.web.ecommerce.security.User;
import app.web.ecommerce.transaction.Purchase;
import app.web.ecommerce.transaction.PurchaseDetail;
import app.web.ecommerce.service.MasterService;
import app.web.ecommerce.service.TransaksiService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author adi
 */

@Controller
@RequestMapping("/admin/purchase")
public class PurchaseController extends ExceptionHandlerController {

    @Autowired private TransaksiService transaksiService;
    @Autowired private MasterService masterService;
    public static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);
    
    private final String SESSION_KEY_PURCHASE = "sessionPurchaseDetail";
    
    private DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd-MM-yyyy");
    
    @RequestMapping("/list")
    public ModelMap pageDataPurchase(){
        return new ModelMap();
    }
    
    @RequestMapping(value="/suppliers", method= RequestMethod.POST)
    @ResponseBody
    public List<Supplier> jsonSuppliers(HttpServletResponse httpServletResponse) {
        List<Supplier> suppliers = masterService.findAllSuppliers();
        return suppliers;
    }
    
    @RequestMapping(value="/products", method= RequestMethod.POST)
    @ResponseBody
    public List<Product> jsonProducts(HttpServletResponse httpServletResponse) {
        return masterService.findAllProducts();
    }
    
    @RequestMapping(value="/cancel", method= RequestMethod.GET)
    @ResponseBody
    public void cancelProducts(HttpSession session) {
        session.removeAttribute(SESSION_KEY_PURCHASE);
    }
    
    @RequestMapping(value="/json", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> jsonDataPurchase(Pageable pageable, 
            @RequestParam(value="startDate", required=true) String sdate,
            @RequestParam(value="endDate", required=true) String edate) {
        Date startDate = formatDate.parseDateTime(sdate).toDate();
        Date endDate = formatDate.parseDateTime(edate).toDate();
        
        Long countPurchase = transaksiService.countPurchaseByDate(startDate, endDate);
        
        Object[] obj = {pageable.getOffset(), pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()};
        logger.info("DEBUG VALUE PAGEABLE -- Offset[{}] PageNumber[{}] PageSize[{}] Sort[{}]", obj);
        
        List<Purchase> datas = transaksiService.findAllPurchaseByDate(startDate, endDate, pageable).getContent();
        for (Purchase p : datas) {
            for (PurchaseDetail d : p.getPurchaseDetails()) {
                d.setPurchase(null);
            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", countPurchase);
        result.put("rows", datas);
        return result;
    }
    
    @RequestMapping(value="/json", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> simpanDataPurchase(@RequestBody @Valid Purchase purchase, BindingResult errors, HttpSession session) {
        List<PurchaseDetail> details = new ArrayList<PurchaseDetail>();

        if(session.getAttribute(SESSION_KEY_PURCHASE) != null){
            details = (List<PurchaseDetail>) session.getAttribute(SESSION_KEY_PURCHASE);
        }
        
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            
            purchase.setPurchaseDetails(details);
            purchase.setPurchaseDate(new Date());
            
            for (PurchaseDetail pd : details) {
                pd.setPurchase(purchase);
            }
            
            session.removeAttribute(SESSION_KEY_PURCHASE);
            Purchase p = transaksiService.save(purchase);
            
            for (PurchaseDetail d : p.getPurchaseDetails()) {
                d.setPurchase(null);
            }
            
            responseEntity = new ResponseEntity<Object>(p, HttpStatus.CREATED);
        }
        return responseEntity;
    }
    
    @RequestMapping(value="/detail/json", method= RequestMethod.GET)
    @ResponseBody
    public List<PurchaseDetail> jsonDetailPurchase(HttpSession session) {
        List<PurchaseDetail> details = new ArrayList<PurchaseDetail>();
        
        if(session.getAttribute(SESSION_KEY_PURCHASE) != null){
            details = (List<PurchaseDetail>) session.getAttribute(SESSION_KEY_PURCHASE);
        }
        
        return details;
    }
    
    @RequestMapping(value="/detail/save", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> simpanDetailPurchase(@RequestBody @Valid PurchaseDetail purchaseDetail, BindingResult errors, HttpSession session) {
        
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            List<PurchaseDetail> details = new ArrayList<PurchaseDetail>();

            if(session.getAttribute(SESSION_KEY_PURCHASE) != null){
                details = (List<PurchaseDetail>) session.getAttribute(SESSION_KEY_PURCHASE);
            }
            
            details.add(purchaseDetail);
            
            session.setAttribute(SESSION_KEY_PURCHASE, details);
            
            responseEntity = new ResponseEntity<Object>(purchaseDetail, HttpStatus.CREATED);
        }
        return responseEntity;
    }
}
