/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.dao;

import app.web.ecommerce.master.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author ilham-buru2@bsi
 */
public interface CityDao extends PagingAndSortingRepository<City, String>, JpaRepository<City, String> {
    
    City findCityByCode(String code);
    
}
