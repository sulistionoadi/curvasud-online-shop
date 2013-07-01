/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.test;

import id.ac.bsi.adi.ta.ecommerce.constant.DesignationType;
import id.ac.bsi.adi.ta.ecommerce.service.RunningNumberService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.CannotAcquireLockException;

/**
 *
 * @author adi
 */
public class RunningNumberTestIT {

    private static final Logger logger = LoggerFactory.getLogger(RunningNumberTestIT.class);
    private static RunningNumberService runningNumberService;
    
    public static void main(String[] args) {
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:id/ac/bsi/adi/ta/**/applicationContext.xml");
        runningNumberService = (RunningNumberService) ctx.getBean("runningNumberService");
        ctx.registerShutdownHook();
        
        List<Thread> threads = new ArrayList<Thread>();
        
        for (int i = 0; i < 10; i++) {
            logger.info("WOWOWOWOOWOWOWOWOOWOWOW");
            Thread t = new Thread(new Runnable() {

                @Override
                public void run() {
                    logger.info("IN RUNNABLE THREAD");
                    for(int x = 0; x<2; x++){
                        try{
                            String stan = runningNumberService.generateProductRunningNumber(DesignationType.PRODUCT);
                            logger.info("Stan PRODUCT " + stan);
                        } catch(Exception ex){
                            logger.error(ex.getMessage());
                        }
                    }
                }
            }, "Thread " + i);
            
            Thread t2 = new Thread(new Runnable() {

                @Override
                public void run() {
                    logger.info("IN RUNNABLE THREAD2");
                    for(int x = 0; x<2; x++){
                        try{
                            String stan = runningNumberService.generateYearlyRunningNumber(new Date(), DesignationType.PAYMENT);
                            logger.info("Stan PAYMENT " + stan);
                        } catch(Exception ex){
                            logger.error(ex.getMessage());
                        }
                    }
                }
            }, "Thread2 " + i);
            
            threads.add(t2);
        }
        
        for (Thread t : threads) {
            logger.info("JALANIN THREAD " + t.getName());
            t.start();
        }

    }
}
