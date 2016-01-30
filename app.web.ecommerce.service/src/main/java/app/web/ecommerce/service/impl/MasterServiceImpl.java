/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.web.ecommerce.service.impl;

import app.web.ecommerce.dao.CategoryProductDao;
import app.web.ecommerce.dao.MemberDao;
import app.web.ecommerce.dao.ProductDao;
import app.web.ecommerce.dao.UserDao;
import app.web.ecommerce.domain.master.CategoryProduct;
import app.web.ecommerce.domain.master.Member;
import app.web.ecommerce.domain.master.Product;
import app.web.ecommerce.domain.security.User;
import app.web.ecommerce.service.MasterService;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ilham-buru2@bsi
 */

@Service("masterService")
@Transactional
public class MasterServiceImpl implements MasterService {

    @Autowired private CategoryProductDao categoryProductDao;
    @Autowired private MemberDao memberDao;
    @Autowired private ProductDao productDao;
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
    public void save(Member member) {
        memberDao.save(member);
    }

    @Override
    public Member findMemberByKode(String kode) {
        return memberDao.findByMemberCode(kode);
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
    public Long countAllProductsByCategory(CategoryProduct cat) {
        return productDao.countByCategory(cat);
    }

    @Override
    public Page<Product> findAllProductsByCategory(Pageable pageable, CategoryProduct cat) {
        return productDao.findByCategory(cat, pageable);
    }

    @Override
    public Long countAllProductsByKeyword(String keyword) {
        return productDao.countByKeyword("%"+keyword+"%");
    }

    @Override
    public Page<Product> findAllProductsByKeyword(Pageable pageable, String keyword) {
        return productDao.findByKeyword("%"+keyword+"%", pageable);
    }
    
}
