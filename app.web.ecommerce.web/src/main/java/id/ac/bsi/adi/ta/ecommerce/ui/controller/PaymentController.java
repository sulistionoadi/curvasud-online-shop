/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import id.ac.bsi.adi.ta.ecommerce.constant.DesignationType;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Booking;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Payment;
import id.ac.bsi.adi.ta.ecommerce.service.RunningNumberService;
import id.ac.bsi.adi.ta.ecommerce.service.TransaksiService;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author adi
 */

@Controller
@RequestMapping("/user/payment")
public class PaymentController extends ExceptionHandlerController {
    
    private final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    @Autowired private TransaksiService transaksiService;
    @Autowired private RunningNumberService runningNumberService;
    private DateTimeFormatter formatDate = DateTimeFormat.forPattern("dd-MM-yyyy");
    
    @RequestMapping(value="/form", method= RequestMethod.GET)
    private ModelMap formPayment(@RequestParam(value="kode", required=false) String kode) throws Exception{
        ModelMap mm = new ModelMap();
        
        if(StringUtils.hasText(kode)){
            Booking b = transaksiService.findBookingByBookingCode(kode);
            if(b==null) throw new Exception("Booking tidak ditemukan");
            
            List<Payment> payments = transaksiService.findPaymentByBooking(b);
            
            mm.addAttribute("booking", b);
            mm.addAttribute("payments", payments);
        }
        
        return mm;
    }
    
    @RequestMapping(value="/form", method= RequestMethod.POST)
    private String postPayment(
            @RequestParam(value="kodeBooking", required=true) String kode,
            @RequestParam(value="acname", required=true) String accountName,
            @RequestParam(value="amount", required=true) BigDecimal trfAmount,
            @RequestParam(value="tgl", required=true) String tglTransfer) throws Exception{
        
        logger.info("PARAM KODE : " + kode);
        logger.info("PARAM ACNAME : " + accountName);
        logger.info("PARAM AMOUNT : " + trfAmount);
        logger.info("PARAM TGL : " + tglTransfer);
        
        Booking b = transaksiService.findBookingByBookingCode(kode);
        if(b==null) throw new Exception("Booking tidak ditemukan");
            
        Payment payment = new Payment();
        payment.setApproveDate(null);
        payment.setApproved(Boolean.FALSE);
        payment.setBooking(b);
        
        payment.setAccountName(accountName);
        payment.setPaymentDate(formatDate.parseDateTime(tglTransfer).toDate());
        payment.setTransferAmount(trfAmount);
        
        DateTime sekarang = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyMM");
        
        String paymentCode = "PA" + formatter.print(sekarang) + org.apache.commons.lang.StringUtils.leftPad(runningNumberService.generateMonthlyRunningNumber(sekarang.toDate(), DesignationType.PAYMENT), 4, "0");
        payment.setPaymentCode(paymentCode);
        payment.setTransactionDate(new Date());
        
        transaksiService.save(payment);
        
        return "redirect:sukses?id=" + payment.getPaymentCode();
    }
    
    @RequestMapping(value="/sukses", method= RequestMethod.GET)
    public ModelMap sukses(
            @RequestParam(value="id", required=true) String id) throws Exception{
        ModelMap mm = new ModelMap();
        mm.addAttribute("id", id);
        return mm;
    }
    
    @RequestMapping("/cetak")
    public ModelMap cetakBuktiBooking(
            @RequestParam(value="id", required=true) String idPayment, HttpServletRequest request) throws Exception{
        
        String format = "pdf";
        String logoBca = request.getSession().getServletContext().getRealPath("img") + File.separator + "logo-bca.png";
        String logoCurvasud = request.getSession().getServletContext().getRealPath("img") + File.separator + "product" + File.separator + "curvasud_logo.png";
        
        Payment payment = transaksiService.findPaymentByCode(idPayment);
        
        if(payment==null) throw new Exception("Payment not found !");
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMMM yyyy HH:mm:ss");
        
        ModelMap mm = new ModelMap();
        mm.put("format", format);
        mm.put("noBooking", payment.getBooking().getBookingCode());
        mm.put("noPayment", payment.getPaymentCode());
        mm.put("tglPayment", payment.getTransactionDate());
        mm.put("kodeMember", payment.getBooking().getMember().getMemberCode());
        mm.put("namaMember", payment.getBooking().getMember().getFirstname().toUpperCase() + " " + payment.getBooking().getMember().getLastname().toUpperCase());
        mm.put("jumlahPayment", payment.getTransferAmount());
        mm.put("empties", new ArrayList<Object>());
        mm.put("logoBca", logoBca);
        mm.put("logoCurvasud", logoCurvasud);
        mm.put("remark", payment.getPaymentCode() + "/" + payment.getBooking().getMember().getMemberCode() + " " + formatter.print(new DateTime()));
        
        return mm;
    }
}
