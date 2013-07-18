/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.ui.controller;

import id.ac.bsi.adi.ta.ecommerce.constant.DesignationType;
import id.ac.bsi.adi.ta.ecommerce.domain.master.CategoryProduct;
import id.ac.bsi.adi.ta.ecommerce.domain.master.Product;
import id.ac.bsi.adi.ta.ecommerce.service.MasterService;
import id.ac.bsi.adi.ta.ecommerce.service.RunningNumberService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author adi
 */

@Controller
@RequestMapping("/admin/product")
public class ProductController extends ExceptionHandlerController{
    
    @Autowired private MasterService masterService;
    @Autowired private RunningNumberService runningNumberService;
    public static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final String SESSION_KEY_UPLOAD_FILE = "sessionUploadFile";
    private final List<String> FILE_EXTENSION = Arrays.asList("png", "jpg", "jpeg");
    private final String IMAGE_PRODUCT_DIR = "img" + File.separator + "product";
    
    @RequestMapping("/list")
    public ModelMap pageDataProduct(){
        return new ModelMap();
    }
    
    @RequestMapping("/code")
    @ResponseBody
    public Map<String, String> generateKodeProduk(){
        String number = runningNumberService.generateProductRunningNumber(DesignationType.PRODUCT);
        String code = "CSOS" + StringUtils.leftPad(number, 4, "0");
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", code);
        return map;
    }
    
    @RequestMapping(value="/json", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> jsonDataProduct(Pageable pageable, HttpServletResponse httpServletResponse) {
        Long countProduct = masterService.countAllProducts();
        Object[] obj = {pageable.getOffset(), pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()};
        logger.info("DEBUG VALUE PAGEABLE -- Offset[{}] PageNumber[{}] PageSize[{}] Sort[{}]", obj);
        
        List<Product> datas = masterService.findAllProducts(pageable).getContent();
        for (Product p : datas) {
            List<String> newPicProduct = new ArrayList();
            for (String s : p.getPictures()) {
                s = tokenizer(s, "/");
                logger.info("STRING PIC PRODUCT : " + s);
                newPicProduct.add(s);
            }
            p.setPictures(newPicProduct);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", countProduct);
        result.put("rows", datas);
        return result;
    }
    
    @RequestMapping(value="/json", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> simpanProduct(@RequestBody @Valid Product product, BindingResult errors, HttpServletRequest request, HttpSession session) {
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            List<String> pics = new ArrayList<String>();
            List<String> newPic = new ArrayList<String>();
            if(session.getAttribute(SESSION_KEY_UPLOAD_FILE)!=null){
                pics = (List<String>) session.getAttribute(SESSION_KEY_UPLOAD_FILE);
            }
            logger.info("TOTAL PICS di SESSION : " + pics.size());
            
            for (String s : pics) {
                s = IMAGE_PRODUCT_DIR + File.separator + s;
                newPic.add(s);
            }
            
            product.setPictures(newPic);
            responseEntity = new ResponseEntity<Object>(masterService.save(product), HttpStatus.CREATED);
            for (String s : pics) {
                logger.info("MENCARI FILE DI TMP : " +"/tmp"+File.separator+s);
                File f = new File("/tmp"+File.separator+s);
                if(f.exists()){
                    logger.info("FILE DIPINDAH DI : " + request.getSession().getServletContext().getRealPath(IMAGE_PRODUCT_DIR) + File.separator + s);
                    f.renameTo(new File(request.getSession().getServletContext().getRealPath(IMAGE_PRODUCT_DIR),s));
                }
            }
            session.removeAttribute(SESSION_KEY_UPLOAD_FILE);
        }
        return responseEntity;
    }
    
    @RequestMapping("/json/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Product product(@PathVariable String id) {
        Product p = masterService.findProductById(id);
        if(p==null){
            logger.warn("Product with id [{}] not found !!", id);
            throw new IllegalStateException("Product with id [" + id + "] not found !!");
        }
        return p;
    }
    
    @RequestMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public ModelMap infoDetail(@RequestParam(value="id", required=false) String id) {
        Product p = masterService.findProductById(id);
        if(p==null){
            logger.warn("Product with id [{}] not found !!", id);
            throw new IllegalStateException("Product with id [" + id + "] not found !!");
        }
        return new ModelMap().addAttribute(p);
    }
    
    @RequestMapping("/cat/{kode}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CategoryProduct category(@PathVariable String kode) {
        CategoryProduct cp = masterService.findCategoryProductByKode(kode);
        if(cp==null){
            logger.warn("CategoryProduct with id [{}] not found !!", cp);
            throw new IllegalStateException("CategoryProduct with id [" + cp + "] not found !!");
        }
        return cp;
    }
    
    @RequestMapping(value="/json/{id}", method= RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid Product product, BindingResult errors, HttpServletRequest request, HttpSession session) {
        ResponseEntity<Object> responseEntity = null;
        if(errors.hasErrors()){
            responseEntity = new ResponseEntity<Object>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            Product p = masterService.findProductById(id);
            if(p==null){
                logger.warn("Product with id [{}] not found !!", id);
                throw new IllegalStateException("Product with id [" + id + "] not found !!");
            }
            product.setId(p.getId());
            List<String> pics = new ArrayList<String>();
            List<String> newPic = new ArrayList<String>();
            if(session.getAttribute(SESSION_KEY_UPLOAD_FILE)!=null){
                pics = (List<String>) session.getAttribute(SESSION_KEY_UPLOAD_FILE);
            }
            logger.info("TOTAL PICS di SESSION : " + pics.size());
            
            for (String s : pics) {
                s = IMAGE_PRODUCT_DIR + File.separator + s;
                newPic.add(s);
            }
            
            product.setPictures(newPic);
            responseEntity = new ResponseEntity<Object>(masterService.save(product), HttpStatus.OK);
            for (String s : pics) {
                logger.info("MENCARI FILE DI TMP : " +"/tmp"+File.separator+s);
                File f = new File("/tmp"+File.separator+s);
                if(f.exists()){
                    logger.info("FILE DIPINDAH DI : " + request.getSession().getServletContext().getRealPath(IMAGE_PRODUCT_DIR) + File.separator + s);
                    f.renameTo(new File(request.getSession().getServletContext().getRealPath(IMAGE_PRODUCT_DIR),s));
                }
            }
            session.removeAttribute(SESSION_KEY_UPLOAD_FILE);
        }
        return responseEntity;
    }
    
    @RequestMapping(value="/json/{id}", method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable String id) {
        Product p = masterService.findProductById(id);
        if(p==null){
            logger.warn("Product with id [{}] not found !!", id);
            throw new IllegalStateException("Product with id [" + id + "] not found !!");
        }
        masterService.delete(p);
    }
    
    @RequestMapping(value="/setPicSession/{id}", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void setPicSession(@PathVariable String id, HttpSession session) {
        Product p = masterService.findProductById(id);
        if(p!=null){
            List<String> pics = new ArrayList<String>();
            for(String s : p.getPictures()){
                s = tokenizer(s, "/");
                pics.add(s);
            }
            session.setAttribute(SESSION_KEY_UPLOAD_FILE, pics);
        }
    }
    
    @RequestMapping(value="/upload", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam(value="picture", required=true) MultipartFile multipartFile, 
        HttpServletRequest request, HttpSession session){
        
        Map<String, Object> result = new HashMap();
        
        if(multipartFile.isEmpty() || multipartFile == null){
            result.put("msg", "No file uploaded");
        } else {
            String extension = tokenizer(multipartFile.getOriginalFilename(), ".");
            if(FILE_EXTENSION.contains(extension.toLowerCase())){
                try {
                    List<String> listPics = new ArrayList();
                    if(session.getAttribute(SESSION_KEY_UPLOAD_FILE) != null){
                        listPics = (List<String>) session.getAttribute(SESSION_KEY_UPLOAD_FILE);
                    }
                    
                    String filename = multipartFile.getOriginalFilename();
                    if(listPics.contains(filename)){
                        result.put("msg", "File already exist !");
                    } else {
                        String targetFolder = "/tmp";
                        multipartFile.transferTo(new File(targetFolder + File.separator + filename));
                        listPics.add(filename);
                        session.setAttribute(SESSION_KEY_UPLOAD_FILE, listPics);
                    }
                } catch (IOException ex) {
                    result.put("msg", ex.getMessage());
                } catch (IllegalStateException ex) {
                    result.put("msg", ex.getMessage());
                }
            } else {
                result.put("msg", "File extensions are not permitted");
            }
        }
        
        return result;
    }
    
    @RequestMapping(value="/picdel", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> picdel(@RequestParam(value="name", required=true) String name, HttpServletRequest request, HttpSession session){
        Map<String, Object> result = new HashMap();
        String pathfile = request.getSession().getServletContext().getRealPath(IMAGE_PRODUCT_DIR) + File.separator + name;
        logger.info("Product Image Path : " + pathfile);
        List<String> listPics = new ArrayList();
        if(session.getAttribute(SESSION_KEY_UPLOAD_FILE) != null){
            listPics = (List<String>) session.getAttribute(SESSION_KEY_UPLOAD_FILE);
        }
        
        File file = new File(pathfile);
        if(file.exists()){
            file.delete();
        } else {
            pathfile = "/tmp" + File.separator + name;
            file = new File(pathfile);
            if(file.exists()){
                file.delete();
            }
        }
        listPics.remove(pathfile);
        session.setAttribute(SESSION_KEY_UPLOAD_FILE, listPics);
        return result;
    }
    
    private String tokenizer(String filename, String token){
        StringTokenizer st = new StringTokenizer(filename, token);
        String result = "";
        while(st.hasMoreTokens()){
            result = st.nextToken();
        }
        return result;
    }
    
}
