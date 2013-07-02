/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service.impl;

import id.ac.bsi.adi.ta.ecommerce.dao.CategoryProductDao;
import id.ac.bsi.adi.ta.ecommerce.dao.CityDao;
import id.ac.bsi.adi.ta.ecommerce.dao.MemberDao;
import id.ac.bsi.adi.ta.ecommerce.dao.ProductDao;
import id.ac.bsi.adi.ta.ecommerce.dao.ShippingRateDao;
import id.ac.bsi.adi.ta.ecommerce.dao.SupplierDao;
import id.ac.bsi.adi.ta.ecommerce.dao.UserDao;
import id.ac.bsi.adi.ta.ecommerce.domain.master.CategoryProduct;
import id.ac.bsi.adi.ta.ecommerce.domain.master.City;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Member;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import id.ac.bsi.adi.ta.ecommerce.domain.master.ShippingRate;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Supplier;
import id.ac.bsi.adi.ta.ecommerce.domain.security.User;
import id.ac.bsi.adi.ta.ecommerce.service.MasterService;
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

@Service("masterService")
@Transactional
public class MasterServiceImpl implements MasterService {

    @Autowired private CategoryProductDao categoryProductDao;
    @Autowired private CityDao cityDao;
    @Autowired private MemberDao memberDao;
    @Autowired private ProductDao productDao;
    @Autowired private SupplierDao supplierDao;
    @Autowired private ShippingRateDao shippingRateDao;
    @Autowired private UserDao userDao;
    
    @Override
    public CategoryProduct save(CategoryProduct categoryProduct) {
        return categoryProductDao.save(categoryProduct);
    }

    @Override
    public void delete(CategoryProduct categoryProduct) {
        categoryProductDao.delete(categoryProduct);
    }

    @Override
    public CategoryProduct findCategoryProductById(String id) {
        return categoryProductDao.findOne(id);
    }

    @Override
    public CategoryProduct findCategoryProductByKode(String kode) {
        return categoryProductDao.findCategoryProductByCategoryCode(kode);
    }

    @Override
    public Long countAllCategoryProuducts() {
        return categoryProductDao.count();
    }

    @Override
    public Page<CategoryProduct> findAllCategoryProducts(Pageable pageable) {
        return categoryProductDao.findAll(pageable);
    }

    @Override
    public Product save(Product product) {
        return productDao.save(product);
    }

    @Override
    public void delete(Product product) {
        productDao.delete(product);
    }

    @Override
    public Product findProductById(String id) {
       Product p = productDao.findOne(id);
       
       if(p!=null) {
           Hibernate.initialize(p.getPictures());
       }
       
       return p;
    }

    @Override
    public Product findProductByKode(String kode) {
       Product p = productDao.findProductByProductCode(kode);
       
       if(p!=null) {
           Hibernate.initialize(p.getPictures());
       }
       
       return p;
    }

    @Override
    public Long countAllProducts() {
        return productDao.count();
    }

    @Override
    public Page<Product> findAllProducts(Pageable pageable) {
        Page<Product> products = productDao.findAll(pageable);
        
        for (Product p : products.getContent()) {
            Hibernate.initialize(p.getPictures());
        }
        
        return products;
    }
    
    @Override
    public List<Product> findAllProducts() {
        List<Product> products = productDao.findAll();
        
        for (Product p : products) {
            Hibernate.initialize(p.getPictures());
        }
        
        return products;
    }

    @Override
    public void delete(Member member) {
        memberDao.delete(member);
    }

    @Override
    public Member findMemberById(String id) {
        return memberDao.findOne(id);
    }

    @Override
    public Long countAllMembers() {
        return memberDao.count();
    }

    @Override
    public Page<Member> findAllMembers(Pageable pageable) {
        return memberDao.findAll(pageable);
    }

    @Override
    public void register(Member member, User user) {
        memberDao.save(member);
        userDao.save(user);
    }

    @Override
    public Supplier save(Supplier supplier) {
        return supplierDao.save(supplier);
    }

    @Override
    public void delete(Supplier supplier) {
        supplierDao.delete(supplier);
    }

    @Override
    public Supplier findSupplierById(String id) {
        return supplierDao.findOne(id);
    }

    @Override
    public Supplier findSupplierByKode(String kode) {
        return supplierDao.findSupplierByCode(kode);
    }

    @Override
    public Long countAllSupplier() {
        return supplierDao.count();
    }

    @Override
    public Page<Supplier> findAllSupplier(Pageable pageable) {
        return supplierDao.findAll(pageable);
    }

    @Override
    public City save(City city) {
        return cityDao.save(city);
    }

    @Override
    public void delete(City city) {
        cityDao.delete(city);
    }

    @Override
    public City findCityById(String id) {
        return cityDao.findOne(id);
    }

    @Override
    public City findCityByKode(String kode) {
        return cityDao.findCityByCode(kode);
    }

    @Override
    public Long countAllCities() {
        return cityDao.count();
    }

    @Override
    public Page<City> findAllCities(Pageable pageable) {
        return cityDao.findAll(pageable);
    }

    @Override
    public ShippingRate save(ShippingRate shippingRate) {
        return shippingRateDao.save(shippingRate);
    }

    @Override
    public void delete(ShippingRate shippingRate) {
        shippingRateDao.delete(shippingRate);
    }

    @Override
    public ShippingRate findShippingRateById(String id) {
        return shippingRateDao.findOne(id);
    }

    @Override
    public ShippingRate findShippingRateByCity(City city) {
        return shippingRateDao.findShippingRateByCity(city);
    }

    @Override
    public Long countAllShippingRates() {
        return shippingRateDao.count();
    }

    @Override
    public Page<ShippingRate> findAllShippingRates(Pageable pageable) {
        return shippingRateDao.findAll(pageable);
    }

    @Override
    public List<City> findAllCities() {
        return cityDao.findAll();
    }
    
    @Override
    public List<Supplier> findAllSuppliers() {
        return supplierDao.findAll();
    }
    
}
