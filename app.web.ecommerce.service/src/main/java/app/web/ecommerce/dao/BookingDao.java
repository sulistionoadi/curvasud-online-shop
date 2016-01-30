/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.dao;

import app.web.ecommerce.transaction.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author ilham-buru2@bsi
 */
public interface BookingDao extends PagingAndSortingRepository<Booking, String>, JpaRepository<Booking, String> {
    
    public Booking findByBookingCode(String bookingCode);
    
}
