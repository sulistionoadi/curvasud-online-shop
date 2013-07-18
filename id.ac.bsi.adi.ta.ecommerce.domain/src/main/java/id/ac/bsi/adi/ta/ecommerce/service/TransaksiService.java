/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service;

import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Booking;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.ChangeOfStock;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Invoice;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Payment;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Purchase;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author adi
 */
public interface TransaksiService {
    
    public Purchase save(Purchase purchase);
    public Purchase findPurchaseById(String id);
    public Long countPurchaseByDate(Date startDate, Date endDate);
    public Page<Purchase> findAllPurchaseByDate(Date startDate, Date endDate, Pageable pageable);
    public List<Purchase> findAllPurchaseByPurchaseNumber(String purchaseNumber);
 
    public Payment save(Payment payment);
    public Payment findPaymentById(String id);
    public Long countPaymentByApproved(Boolean approved);
    public Page<Payment> findPaymentByApproved(Boolean approved, Pageable pageable);
    public List<Payment> findPaymentByBooking(Booking booking);
    
    public Booking save(Booking booking);
    public Booking findBookingById(String id);
    public Booking findBookingByBookingCode(String kode);
    
    public void save(Invoice invoice);
    public Invoice findInvoiceById(String id);
    public Long countAllInvoice();
    public Page<Invoice> findAllInvoice(Pageable pageable);
    
    public ChangeOfStock getDataStok(Product product, Date periode);
    public ChangeOfStock save(ChangeOfStock cos);
    public void generateChangeOfStock();
}
