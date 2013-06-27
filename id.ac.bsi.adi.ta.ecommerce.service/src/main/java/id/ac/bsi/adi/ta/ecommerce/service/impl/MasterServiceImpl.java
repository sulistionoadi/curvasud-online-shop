/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service.impl;

import id.ac.bsi.adi.ta.ecommerce.dao.CategoryProductDao;
import id.ac.bsi.adi.ta.ecommerce.dao.MemberDao;
import id.ac.bsi.adi.ta.ecommerce.dao.ProductDao;
import id.ac.bsi.adi.ta.ecommerce.dao.UserDao;
import id.ac.bsi.adi.ta.ecommerce.domain.master.CategoryProduct;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Member;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import id.ac.bsi.adi.ta.ecommerce.domain.security.Role;
import id.ac.bsi.adi.ta.ecommerce.domain.security.User;
import id.ac.bsi.adi.ta.ecommerce.service.MasterService;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
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
    @Autowired private ProductDao productDao;
    @Autowired private MemberDao memberDao;
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
    public Long countAllProuducts() {
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
    
}
