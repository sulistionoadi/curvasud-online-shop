/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service.impl;

import id.ac.bsi.adi.ta.ecommerce.domain.transaction.Purchase;
import id.ac.bsi.adi.ta.ecommerce.service.MasterService;
import id.ac.bsi.adi.ta.ecommerce.service.TransaksiService;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author adi
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:id/ac/bsi/adi/ta/**/applicationContext.xml")
public class PurchaseTestIT {
    @Autowired private TransaksiService transaksiService;
    @Autowired private MasterService masterService;
    
    @Test
    public void savePurchase() {
        Purchase p = new Purchase();
        p.setPurchaseDate(new Date());
        p.setPurchaseNumber("098289480332");
//        p.set

    }
}
