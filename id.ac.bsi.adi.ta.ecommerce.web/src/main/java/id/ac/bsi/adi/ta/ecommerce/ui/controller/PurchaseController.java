/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Purchase;
import id.ac.bsi.adi.ta.ecommerce.service.TransaksiService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/admin/purchase")
public class PurchaseController extends ExceptionHandlerController {

    @Autowired private TransaksiService transaksiService;
    public static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);
    
    private DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd-MM-yyyy");
    
    @RequestMapping("/list")
    public ModelMap pageDataPurchase(){
        return new ModelMap();
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
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", countPurchase);
        result.put("rows", datas);
        return result;
    }
}
