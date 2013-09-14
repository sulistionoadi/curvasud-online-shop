/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service;

import id.ac.bsi.adi.ta.ecommerce.domain.master.CategoryProduct;
import id.ac.bsi.adi.ta.ecommerce.domain.master.City;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Member;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import id.ac.bsi.adi.ta.ecommerce.domain.master.ShippingRate;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Supplier;
import id.ac.bsi.adi.ta.ecommerce.domain.security.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author adi
 */
public interface MasterService {
    
    public CategoryProduct save(CategoryProduct categoryProduct);
    public void delete(CategoryProduct categoryProduct);
    public CategoryProduct findCategoryProductByKode(String kode);
    public Long countAllCategoryProuducts();
    public Page<CategoryProduct> findAllCategoryProducts(Pageable pageable);
    
    public Product save(Product product);
    public void delete(Product product);
    public Product findProductByKode(String kode);
    public Long countAllProducts();
    public Long countAllProductsByCategory(CategoryProduct cat);
    public Long countAllProductsByKeyword(String keyword);
    public Page<Product> findAllProducts(Pageable pageable);
    public Page<Product> findAllProductsByCategory(Pageable pageable, CategoryProduct cat);
    public Page<Product> findAllProductsByKeyword(Pageable pageable, String keyword);
    public List<Product> findAllProducts();
    
    public Supplier save(Supplier supplier);
    public void delete(Supplier supplier);
    public Supplier findSupplierByKode(String kode);
    public Long countAllSupplier();
    public Page<Supplier> findAllSupplier(Pageable pageable);
    public List<Supplier> findAllSuppliers();
    
    public void register(Member member, User user);
    public void delete(Member member);
    public Member findMemberByKode(String kode);
    public Long countAllMembers();
    public Page<Member> findAllMembers(Pageable pageable);
    
    public City save(City city);
    public void delete(City city);
    public City findCityByKode(String kode);
    public Long countAllCities();
    public Page<City> findAllCities(Pageable pageable);
    public List<City> findAllCities();
    
    public ShippingRate save(ShippingRate shippingRate);
    public void delete(ShippingRate shippingRate);
    public ShippingRate findShippingRateById(String id);
    public ShippingRate findShippingRateByCity(City city);
    public Long countAllShippingRates();
    public Page<ShippingRate> findAllShippingRates(Pageable pageable);
    
}
