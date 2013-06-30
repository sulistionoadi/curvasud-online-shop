/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.dao;

import id.ac.bsi.adi.ta.ecommerce.domain.master.City;
import id.ac.bsi.adi.ta.ecommerce.domain.master.ShippingRate;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author adi
 */
public interface ShippingRateDao extends PagingAndSortingRepository<ShippingRate, String> {
    
    ShippingRate findShippingRateByCity(City city);
    
}
