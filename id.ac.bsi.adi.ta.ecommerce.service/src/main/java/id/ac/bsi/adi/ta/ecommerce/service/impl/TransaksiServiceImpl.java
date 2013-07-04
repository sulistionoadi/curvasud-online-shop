/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service.impl;

import id.ac.bsi.adi.ta.ecommerce.dao.PaymentDao;
import id.ac.bsi.adi.ta.ecommerce.dao.PurchaseDao;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Payment;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Purchase;
import id.ac.bsi.adi.ta.ecommerce.service.TransaksiService;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author adi
 */

@Service("transaksiService")
@Transactional
public class TransaksiServiceImpl implements TransaksiService {
    
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private PaymentDao paymentDao;

    @Override
    public Purchase save(Purchase purchase) {
        Purchase p = purchaseDao.save(purchase);
        if(p!=null){
            Hibernate.initialize(p.getPurchaseDetails());
        }
        return p;
    }

    @Override
    public Purchase findPurchaseById(String id) {
        Purchase p = purchaseDao.findOne(id);
        if(p!=null){
            Hibernate.initialize(p.getPurchaseDetails());
        }
        return p;
    }

    @Override
    public Page<Purchase> findAllPurchaseByDate(Date startDate, Date endDate, Pageable pageable) {
        Page<Purchase> pages = purchaseDao.findByPurchaseDateBetween(startDate, endDate, pageable);
        for (Purchase p : pages.getContent()) {
            Hibernate.initialize(p.getPurchaseDetails());
        }
        return pages;
    }

    @Override
    public List<Purchase> findAllPurchaseByPurchaseNumber(String purchaseNumber) {
        List<Purchase> listp = purchaseDao.findByPurchaseNumber(purchaseNumber);
        for (Purchase p : listp) {
            Hibernate.initialize(p.getPurchaseDetails());
        }
        return listp;
    }

    @Override
    public Long countPurchaseByDate(Date startDate, Date endDate) {
        return purchaseDao.countByPurchaseDateBetween(startDate, endDate);
    }

    @Override
    public Payment save(Payment payment) {
        Payment p = paymentDao.save(payment);
        
        return p;
    }

    @Override
    public Payment findPaymentById(String id) {
        Payment p = paymentDao.findOne(id);
        
        return p;
    }

    @Override
    public Page<Payment> findPaymentByApproved(boolean approved, Pageable pageable) {
        Page<Payment> payments = paymentDao.findByApproved(approved, pageable);
        
        if(payments.getSize() > 0){
            for (Payment payment : payments) {
                if(payment.getBooking() != null) {
                    Hibernate.initialize(payment.getBooking());
                    Hibernate.initialize(payment.getBooking().getBookingDetails());
                }
            }
        }
        
        return payments;
    }

    @Override
    public Long countPaymentByApproved(boolean approved) {
        return paymentDao.countPaymentByApproved(approved);
    }
    
}
