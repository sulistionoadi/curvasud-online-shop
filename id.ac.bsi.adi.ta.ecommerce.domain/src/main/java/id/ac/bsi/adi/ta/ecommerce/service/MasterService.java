/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service;

import id.ac.bsi.adi.ta.ecommerce.domain.master.CategoryProduct;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import java.util.List;

/**
 *
 * @author adi
 */
public interface MasterService {
    
    public void save(CategoryProduct categoryProduct);
    public void delete(CategoryProduct categoryProduct);
    public CategoryProduct findCategoryProductById(String id);
    public CategoryProduct findCategoryProductByKode(String kode);
    public Long countAllCategoryProuducts();
    public List<CategoryProduct> findAllCategoryProducts(Integer start, Integer rows);
    
    public void save(Product product);
    public void delete(Product product);
    public Product findProductById(String id);
    public Product findProductByKode(String kode);
    public Long countAllProuducts();
    public List<Product> findAllProducts(Integer start, Integer rows);
    
    
}
