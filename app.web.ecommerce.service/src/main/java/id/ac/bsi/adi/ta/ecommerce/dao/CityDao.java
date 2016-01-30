/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.dao;

import id.ac.bsi.adi.ta.ecommerce.domain.master.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author adi
 */
public interface CityDao extends PagingAndSortingRepository<City, String>, JpaRepository<City, String> {
    
    City findCityByCode(String code);
    
}
