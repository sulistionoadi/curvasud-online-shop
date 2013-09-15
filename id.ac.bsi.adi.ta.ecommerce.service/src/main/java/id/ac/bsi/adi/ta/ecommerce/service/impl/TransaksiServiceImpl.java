/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service.impl;

import id.ac.bsi.adi.ta.ecommerce.dao.PaymentDao;
import id.ac.bsi.adi.ta.ecommerce.dao.BookingDao;
import id.ac.bsi.adi.ta.ecommerce.dao.ChangeOfStockDao;
import id.ac.bsi.adi.ta.ecommerce.dao.InvoiceDao;
import id.ac.bsi.adi.ta.ecommerce.dao.PurchaseDao;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Booking;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.BookingDetail;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.ChangeOfStock;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Invoice;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Payment;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Purchase;
import id.ac.bsi.adi.ta.ecommerce.domain.transaction.PurchaseDetail;
import id.ac.bsi.adi.ta.ecommerce.service.TransaksiService;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;
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
    @Autowired
    private BookingDao bookingDao;
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private ChangeOfStockDao changeOfStockDao;

    @Override
    public Purchase save(Purchase purchase) {
        Purchase p = purchaseDao.save(purchase);
        for(PurchaseDetail pd : p.getPurchaseDetails()){
            ChangeOfStock cos = getDataStok(pd.getProduct(), p.getPurchaseDate());
            cos.setStockDebet(cos.getStockDebet() + pd.getQuantity());
            cos.calculateFinalStock();
            save(cos);
        }
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
    public Booking save(Booking booking) {
        return bookingDao.save(booking);
    }
    
    @Override
	public Payment save(Payment payment) {
        Payment p = paymentDao.save(payment);
        
        return p;
    }

    @Override
    public Payment findPaymentById(String id) {
        Payment p = paymentDao.findOne(id);
        if(p.getBooking()!=null){
            Hibernate.initialize(p.getBooking().getBookingDetails());
        }
        return p;
    }

    @Override
    public Page<Payment> findPaymentByApproved(Boolean approved, Pageable pageable) {
        Page<Payment> payments = paymentDao.findByApproved(approved, pageable);
        
        if(payments.getSize() > 0){
            for (Payment payment : payments) {
                if(payment.getBooking() != null) {
                    Hibernate.initialize(payment.getBooking().getBookingDetails());
                }
            }
        }
        
        return payments;
    }

    @Override
    public Long countPaymentByApproved(Boolean approved) {
        return paymentDao.countPaymentByApproved(approved);
    }

    @Override
    public Booking findBookingByBookingCode(String kode) {
        Booking b = bookingDao.findByBookingCode(kode);
        if(b!=null){
            Hibernate.initialize(b.getBookingDetails());
        }
        
        return b;
    }

    @Override
    public List<Payment> findPaymentByBooking(Booking booking) {
        List<Payment> payments = paymentDao.findByBooking(booking);
        
        for (Payment p : payments) {
            if(p.getBooking()!=null){
                Hibernate.initialize(p.getBooking().getBookingDetails());
            }
        }
        
        return payments;
    }

    @Override
    public void save(Invoice invoice) {
        invoiceDao.save(invoice);
        
        for(BookingDetail bd : invoice.getBooking().getBookingDetails()){
            ChangeOfStock cos = getDataStok(bd.getProduct(), invoice.getTransactionDate());
            cos.setStockCredit(bd.getQty());
            cos.calculateFinalStock();
            save(cos);
        }
    }

    @Override
    public Long countAllInvoice() {
        return invoiceDao.count();
    }

    @Override
    public Page<Invoice> findAllInvoice(Pageable pageable) {
        Page<Invoice> invoices = invoiceDao.findAll(pageable);
        for (Invoice invoice : invoices) {
            if(invoice.getBooking()!=null){
                Hibernate.initialize(invoice.getBooking().getBookingDetails());
            }
        }
        
        return invoices;
    }

    @Override
    public Invoice findInvoiceById(String id) {
        Invoice invoice = invoiceDao.findOne(id);
        if(invoice.getBooking() != null) {
            Hibernate.initialize(invoice.getBooking().getBookingDetails());
        }
        
        return invoice;
    }

    @Override
    public ChangeOfStock getDataStok(Product product, Date periode) {
        ChangeOfStock cos = changeOfStockDao.getByProductAndPeriode(product.getProductCode(), periode);
        if(cos==null){
            cos = new ChangeOfStock();
            cos.setDateOfMutation(new DateTime().withDayOfMonth(1).toDate());
            cos.setFinalStock(0);
            cos.setInitialStock(0);
            cos.setProduct(product);
            cos.setStockCredit(0);
            cos.setStockDebet(0);
        }
        return cos;
    }

    @Override
    public ChangeOfStock save(ChangeOfStock cos) {
        return changeOfStockDao.save(cos);
    }

    @Override
    public void generateChangeOfStock() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Long countStokByPeriode(Date tanggal) {
        return changeOfStockDao.countByPeriode(tanggal);
    }

    @Override
    public Page<ChangeOfStock> findStokByPeriode(Date tanggal, Pageable pageable) {
        return changeOfStockDao.findByPeriode(tanggal, pageable);
    }
    
}
