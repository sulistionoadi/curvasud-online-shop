/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.dao;

import app.web.ecommerce.master.City;
import app.web.ecommerce.master.ShippingRate;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author ilham-buru2@bsi
 */
public interface ShippingRateDao extends PagingAndSortingRepository<ShippingRate, String> {
    
    ShippingRate findShippingRateByCity(City city);
    
}
