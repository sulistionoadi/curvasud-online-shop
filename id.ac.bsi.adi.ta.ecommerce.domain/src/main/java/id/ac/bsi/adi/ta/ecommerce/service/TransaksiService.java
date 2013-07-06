/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service;

import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Booking;
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
    public Long countPaymentByApproved(boolean approved);
    public Page<Payment> findPaymentByApproved(boolean approved, Pageable pageable);
    public List<Payment> findPaymentByBooking(Booking booking);
    
    public Booking save(Booking booking);
    public Booking findBookingById(String id);
    public Booking findBookingByBookingCode(String kode);
}
