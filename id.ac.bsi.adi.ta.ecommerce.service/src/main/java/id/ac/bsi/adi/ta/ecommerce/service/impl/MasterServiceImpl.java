/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service.impl;

import id.ac.bsi.adi.ta.ecommerce.domain.master.CategoryProduct;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import id.ac.bsi.adi.ta.ecommerce.domain.security.Role;
import id.ac.bsi.adi.ta.ecommerce.service.MasterService;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author adi
 */

@Service("masterService")
@Transactional
public class MasterServiceImpl implements MasterService {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void save(CategoryProduct categoryProduct) {
        sessionFactory.getCurrentSession().saveOrUpdate(categoryProduct);
    }

    @Override
    public void delete(CategoryProduct categoryProduct) {
        sessionFactory.getCurrentSession().delete(categoryProduct);
    }

    @Override
    public CategoryProduct findCategoryProductById(String id) {
        return (CategoryProduct) sessionFactory.getCurrentSession().get(CategoryProduct.class, id);
    }

    @Override
    public CategoryProduct findCategoryProductByKode(String kode) {
        return (CategoryProduct) sessionFactory.getCurrentSession()
                .createQuery("from CategoryProduct cp where cp.categoryCode = :code")
                .setParameter("code", kode)
                .uniqueResult();
    }

    @Override
    public Long countAllCategoryProuducts() {
        return (Long) sessionFactory.getCurrentSession().createQuery("select count(cp) from CategoryProduct cp").uniqueResult();
    }

    @Override
    public List<CategoryProduct> findAllCategoryProducts(Integer start, Integer rows) {
        if(start==null) start=0;
        if(rows==null) rows=20;
        
        return sessionFactory.getCurrentSession()
                .createQuery("from CategoryProduct cp order by cp.description")
                .setFirstResult(start)
                .setMaxResults(rows)
                .list();
    }

    @Override
    public void save(Product product) {
        sessionFactory.getCurrentSession().saveOrUpdate(product);
    }

    @Override
    public void delete(Product product) {
        sessionFactory.getCurrentSession().delete(product);
    }

    @Override
    public Product findProductById(String id) {
       Product p = (Product) sessionFactory.getCurrentSession().get(Product.class, id);
       
       if(p!=null) {
           Hibernate.initialize(p.getPictures());
       }
       
       return p;
    }

    @Override
    public Product findProductByKode(String kode) {
       Product p = (Product) sessionFactory.getCurrentSession()
               .createQuery("from Product p where p.productCode = :kode")
               .setParameter("kode", kode)
               .uniqueResult();
       
       if(p!=null) {
           Hibernate.initialize(p.getPictures());
       }
       
       return p;
    }

    @Override
    public Long countAllProuducts() {
        return (Long) sessionFactory.getCurrentSession().createQuery("select count(p) from Product p").uniqueResult();
    }

    @Override
    public List<Product> findAllProducts(Integer start, Integer rows) {
        if(start==null) start=0;
        if(rows==null) rows=20;
        
        List<Product> products = sessionFactory.getCurrentSession()
                .createQuery("from Product p order by p.productName")
                .setFirstResult(start)
                .setMaxResults(rows)
                .list();
        
        for (Product p : products) {
            Hibernate.initialize(p.getPictures());
        }
        
        return products;
    }
    
}
