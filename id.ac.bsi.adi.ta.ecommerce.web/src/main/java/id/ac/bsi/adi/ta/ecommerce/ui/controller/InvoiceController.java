/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Payment;
import id.ac.bsi.adi.ta.ecommerce.service.TransaksiService;
import static id.ac.bsi.adi.ta.ecommerce.ui.controller.ApprovalPaymentController.logger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jimmy
 */
@Controller
@RequestMapping("/admin/invoice")
public class InvoiceController {
    
    @Autowired
    private TransaksiService transaksiService;
    public static final Logger logger = LoggerFactory.getLogger(CategoryProductController.class);

    @RequestMapping("/list")
    public ModelMap pageDataApproval() {
        return new ModelMap();
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> jsonDataApproval(Pageable pageable, HttpServletResponse httpServletResponse) {
        Long countPayments = transaksiService.countPaymentByApproved(true);
        Object[] obj = {pageable.getOffset(), pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()};
        logger.info("DEBUG VALUE PAGEABLE -- Offset[{}] PageNumber[{}] PageSize[{}] Sort[{}]", obj);

        List<Payment> datas = transaksiService.findPaymentByApproved(true, pageable).getContent();

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", countPayments);
        result.put("rows", datas);
        return result;
    }
    
}
