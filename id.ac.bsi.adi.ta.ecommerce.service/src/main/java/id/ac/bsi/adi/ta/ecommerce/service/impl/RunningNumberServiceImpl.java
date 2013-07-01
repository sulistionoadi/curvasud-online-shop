/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service.impl;

import id.ac.bsi.adi.ta.ecommerce.constant.DesignationType;
import id.ac.bsi.adi.ta.ecommerce.dao.RunningNumberDao;
import id.ac.bsi.adi.ta.ecommerce.domain.RunningNumber;
import id.ac.bsi.adi.ta.ecommerce.service.RunningNumberService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author adi
 */
@Service("runningNumberService") 
@Transactional(propagation= Propagation.REQUIRES_NEW)
public class RunningNumberServiceImpl implements RunningNumberService {
    
    @Autowired private RunningNumberDao runningNumberDao;

    @Override
    public String generateYearlyRunningNumber(Date date, DesignationType designationType) {
        RunningNumber rn = generateYearlyNumber(date, designationType);
        rn = setOrGenerateNumber(rn, date, designationType);
        return rn.getLastNumber().toString();
    }

    @Override
    public String generateMonthlyRunningNumber(Date date, DesignationType designationType) {
        RunningNumber rn = generateMonthlyNumber(date, designationType);
        rn = setOrGenerateNumber(rn, date, designationType);
        return rn.getLastNumber().toString();
    }

    @Override
    public String generateProductRunningNumber(DesignationType designationType) {
        RunningNumber rn = generateProductNumber(designationType);
        rn = setOrGenerateNumber(rn, new Date(), designationType);
        return rn.getLastNumber().toString();
    }
    
    private RunningNumber generateYearlyNumber(Date date, DesignationType designationType){
        return runningNumberDao.getNumberYearly(date, designationType);
    }
    
    private RunningNumber generateMonthlyNumber(Date date, DesignationType designationType){
        return runningNumberDao.getNumberMonthly(date, designationType);
    }
    
    private RunningNumber generateProductNumber(DesignationType designationType){
        return runningNumberDao.getNumberProduct(designationType);
    }
    
    private RunningNumber setOrGenerateNumber(RunningNumber stan, Date date, DesignationType designation) {
        if (stan == null) {
            stan = new RunningNumber();
            stan.setBussinessDate(date);
            stan.setDesignationType(designation);
            stan.setLastNumber(1l);
            runningNumberDao.save(stan);
        } else {
            stan.setLastNumber(stan.getLastNumber() + 1);
            runningNumberDao.save(stan);
        }
        return stan;
    }
    
}
