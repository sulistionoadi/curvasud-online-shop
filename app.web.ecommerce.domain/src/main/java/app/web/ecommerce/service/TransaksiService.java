/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.service;

import app.web.ecommerce.domain.master.Product;
import app.web.ecommerce.domain.transaction.Booking;
import app.web.ecommerce.domain.transaction.Invoice;
import app.web.ecommerce.domain.transaction.Payment;
import app.web.ecommerce.domain.transaction.Testimoni;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author ilham-buru2@bsi
 */
public interface TransaksiService {
 
    public Payment save(Payment payment);
    public Payment findPaymentByCode(String code);
    public Long countPaymentByApproved(Boolean approved);
    public Page<Payment> findPaymentByApproved(Boolean approved, Pageable pageable);
    public List<Payment> findPaymentByBooking(Booking booking);
    
    public Booking save(Booking booking);
    public Booking findBookingByBookingCode(String kode);
    
    public void save(Invoice invoice);
    public Invoice findInvoiceByNumber(String number);
    public Long countAllInvoice();
    public Page<Invoice> findAllInvoice(Pageable pageable);
    
    public void comment(Testimoni testimoni);
    public void delete(Testimoni testimoni);
    public Testimoni findById(String id);
    public Long countByProduct(Product product);
    public Page<Testimoni> findTestimoniByProduct(Product product, Pageable pageable);
}
