/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.service.impl;

import app.web.ecommerce.dao.PaymentDao;
import app.web.ecommerce.dao.BookingDao;
import app.web.ecommerce.dao.InvoiceDao;
import app.web.ecommerce.dao.TestimoniDao;
import app.web.ecommerce.domain.master.Product;
import app.web.ecommerce.domain.transaction.Booking;
import app.web.ecommerce.domain.transaction.Invoice;
import app.web.ecommerce.domain.transaction.Payment;
import app.web.ecommerce.domain.transaction.Testimoni;
import app.web.ecommerce.service.TransaksiService;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ilham-buru2@bsi
 */

@Service("transaksiService")
@Transactional
public class TransaksiServiceImpl implements TransaksiService {
    
    @Autowired
    private PaymentDao paymentDao;
    @Autowired
    private BookingDao bookingDao;
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private TestimoniDao testimoniDao;

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
        return p;
    }

    @Override
    public Page<Payment> findPaymentByApproved(Boolean approved, Pageable pageable) {
//        Page<Payment> payments = paymentDao.findByApproved(approved, pageable);
//        return payments;
        return null;
    }

    @Override
    public Long countPaymentByApproved(Boolean approved) {
//        return paymentDao.countPaymentByApproved(approved);
        return 0l;
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
//        List<Payment> payments = paymentDao.findByBooking(booking);
        return null;
    }

    @Override
    public void save(Invoice invoice) {
        invoiceDao.save(invoice);
    }

    @Override
    public Long countAllInvoice() {
        return invoiceDao.count();
    }

    @Override
    public Page<Invoice> findAllInvoice(Pageable pageable) {
        Page<Invoice> invoices = invoiceDao.findAll(pageable);
        return invoices;
    }

    @Override
    public Invoice findInvoiceByNumber(String number) {
        Invoice invoice = invoiceDao.findByInvoiceNumber(number);
        return invoice;
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
