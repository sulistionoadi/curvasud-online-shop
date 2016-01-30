/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.service.impl;

import app.web.ecommerce.dao.PaymentDao;
import app.web.ecommerce.dao.BookingDao;
import app.web.ecommerce.dao.ChangeOfStockDao;
import app.web.ecommerce.dao.InvoiceDao;
import app.web.ecommerce.dao.PurchaseDao;
import app.web.ecommerce.dao.TestimoniDao;
import app.web.ecommerce.master.Product;
import app.web.ecommerce.transaction.Booking;
import app.web.ecommerce.transaction.BookingDetail;
import app.web.ecommerce.transaction.ChangeOfStock;
import app.web.ecommerce.transaction.Invoice;
import app.web.ecommerce.transaction.Payment;
import app.web.ecommerce.transaction.Purchase;
import app.web.ecommerce.transaction.PurchaseDetail;
import app.web.ecommerce.transaction.Testimoni;
import app.web.ecommerce.service.TransaksiService;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private TestimoniDao testimoniDao;

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
    public Payment findPaymentByCode(String code) {
        Payment p = paymentDao.findByPaymentCode(code);
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
    public Invoice findInvoiceByNumber(String number) {
        Invoice invoice = invoiceDao.findByInvoiceNumber(number);
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

    @Override
    public void comment(Testimoni testimoni) {
        testimoniDao.save(testimoni);
    }

    @Override
    public void delete(Testimoni testimoni) {
        testimoniDao.delete(testimoni);
    }

    @Override
    public Testimoni findById(String id) {
        return testimoniDao.findOne(id);
    }

    @Override
    public Page<Testimoni> findTestimoniByProduct(Product product, Pageable pageable) {
        PageRequest pr= new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "date");
        return testimoniDao.findByProduct(product, pr);
    }

    @Override
    public Long countByProduct(Product product) {
        return testimoniDao.countByProduct(product);
    }
    
}
