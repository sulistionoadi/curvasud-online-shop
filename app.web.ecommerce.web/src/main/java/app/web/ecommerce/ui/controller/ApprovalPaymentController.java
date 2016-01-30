/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.ui.controller;

import app.web.ecommerce.constant.DesignationType;
import app.web.ecommerce.transaction.BookingDetail;
import app.web.ecommerce.transaction.Invoice;
import app.web.ecommerce.transaction.Payment;
import app.web.ecommerce.service.RunningNumberService;
import app.web.ecommerce.service.TransaksiService;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    @Autowired 
    private RunningNumberService runningNumberService;
    public static final Logger logger = LoggerFactory.getLogger(CategoryProductController.class);
    private DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd-MM-yyyy");

    @RequestMapping("/list")
    public ModelMap pageDataApproval() {
        return new ModelMap();
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> jsonDataApproval(Pageable pageable, HttpServletResponse httpServletResponse) {
        Long countPayments = transaksiService.countPaymentByApproved(Boolean.FALSE);
        Object[] obj = {pageable.getOffset(), pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()};
        logger.info("DEBUG VALUE PAGEABLE -- Offset[{}] PageNumber[{}] PageSize[{}] Sort[{}]", obj);

        List<Payment> datas = transaksiService.findPaymentByApproved(Boolean.FALSE, pageable).getContent();
        for (Payment p : datas) {
            for (BookingDetail d : p.getBooking().getBookingDetails()) {
                d.setBooking(null);
            }
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", countPayments);
        result.put("rows", datas);
        return result;
    }

    @RequestMapping(value = "/do-approve/{id}", method = RequestMethod.GET)
    public String jsonUpdateApproval(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        Payment payment = transaksiService.findPaymentByCode(id);
        if (payment == null) {
            logger.warn("Payment with id [{}] not found !!", id);
            throw new IllegalStateException("Payment with id [" + id + "] not found !!");
        }
        payment.setApproved(Boolean.TRUE);
        payment.setApproveDate(new Date());
        
        transaksiService.save(payment);
        
        DateTime sekarang = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyMM");
        String invoiceCode = "IN" + formatter.print(sekarang) + org.apache.commons.lang.StringUtils.leftPad(runningNumberService.generateMonthlyRunningNumber(sekarang.toDate(), DesignationType.PAYMENT), 4, "0");
        
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceCode);
        invoice.setTransactionDate(new Date());
        invoice.setBooking(payment.getBooking());
        
        transaksiService.save(invoice);
        
        String requestUrl = request.getRequestURL().toString();
        URI uri = new UriTemplate("{requestUrl}").expand(requestUrl);
        response.setHeader("Location", uri.toASCIIString());
        return "redirect:/admin/approval/list";
    }
}
