/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import id.ac.bsi.adi.ta.ecommerce.domain.master.CategoryProduct;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Payment;
import id.ac.bsi.adi.ta.ecommerce.service.TransaksiService;
import static id.ac.bsi.adi.ta.ecommerce.ui.controller.CategoryProductController.logger;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriTemplate;

/**
 *
 * @author jimmy
 */
@Controller
@RequestMapping("/admin/approval")
public class ApprovalPaymentController {

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
        Long countPayments = transaksiService.countPaymentByApproved(false);
        Object[] obj = {pageable.getOffset(), pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()};
        logger.info("DEBUG VALUE PAGEABLE -- Offset[{}] PageNumber[{}] PageSize[{}] Sort[{}]", obj);

        List<Payment> datas = transaksiService.findPaymentByApproved(false, pageable).getContent();

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", countPayments);
        result.put("rows", datas);
        return result;
    }

    @RequestMapping(value = "/do-approve/{id}", method = RequestMethod.GET)
    public String jsonUpdateApproval(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        Payment payment = transaksiService.findPaymentById(id);
        if (payment == null) {
            logger.warn("Payment with id [{}] not found !!", id);
            throw new IllegalStateException("Payment with id [" + id + "] not found !!");
        }
        payment.setApproved(Boolean.TRUE);
        payment.setApproveDate(new Date());
        
        transaksiService.save(payment);
        String requestUrl = request.getRequestURL().toString();
        URI uri = new UriTemplate("{requestUrl}").expand(requestUrl);
        response.setHeader("Location", uri.toASCIIString());
        return "redirect:/admin/approval/list";
    }
}
