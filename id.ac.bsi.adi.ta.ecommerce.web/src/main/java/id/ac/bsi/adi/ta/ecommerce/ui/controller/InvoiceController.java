/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import id.ac.bsi.adi.ta.ecommerce.domain.transaction.BookingDetail;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Invoice;
import id.ac.bsi.adi.ta.ecommerce.dto.BookingStruk;
import id.ac.bsi.adi.ta.ecommerce.service.TransaksiService;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
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
        Long countInvoices = transaksiService.countAllInvoice();
        Object[] obj = {pageable.getOffset(), pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()};
        logger.info("DEBUG VALUE PAGEABLE -- Offset[{}] PageNumber[{}] PageSize[{}] Sort[{}]", obj);

        List<Invoice> datas = transaksiService.findAllInvoice(pageable).getContent();

        for (Invoice invoice : datas) {
            for (BookingDetail bookingDetail : invoice.getBooking().getBookingDetails()) {
                bookingDetail.setBooking(null);
            }
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", countInvoices);
        result.put("rows", datas);
        return result;
    }

    @RequestMapping("/cetak")
    public ModelMap cetakBuktiInvoice(
            @RequestParam(value = "id", required = true) String idInvoice) throws Exception {

        String format = "pdf";

        Invoice invoice = transaksiService.findInvoiceById(idInvoice);

        if (invoice == null) {
            throw new Exception("Invoice not found !");
        }

        for (BookingDetail bookingDetail : invoice.getBooking().getBookingDetails()) {
            bookingDetail.setBooking(null);
        }
        
        ModelMap mm = new ModelMap();
        mm.put("format", format);
        mm.put("noFaktur", invoice.getInvoiceNumber());
        mm.put("nama", invoice.getBooking().getShippingName());
        mm.put("alamat", invoice.getBooking().getShippingAddress());
        mm.put("tanggal", invoice.getTransactionDate());
        mm.put("nomorTelepon", invoice.getBooking().getShippingPhone());

        mm.put("ongkosKirim", invoice.getBooking().getShippingCost());

        int i = 0;
        List<BookingStruk> bookingInvoices = new ArrayList<BookingStruk>();
        for (BookingDetail bd : invoice.getBooking().getBookingDetails()) {
            i++;
            BookingStruk bs = new BookingStruk();
            bs.setNo(i);
            bs.setKode(bd.getProduct().getProductCode());
            bs.setProduk(bd.getProduct().getProductName().toUpperCase());
            bs.setQty(bd.getQty());
            bs.setHarga(bd.getAmount());
            bs.setTotal(bd.getTotalAmount());

            bookingInvoices.add(bs);
        }

        mm.put("bookingInvoices", bookingInvoices);

        return mm;
    }
}
