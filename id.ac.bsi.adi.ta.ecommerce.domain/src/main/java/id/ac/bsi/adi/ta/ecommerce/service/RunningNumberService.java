/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.bsi.adi.ta.ecommerce.service;

import id.ac.bsi.adi.ta.ecommerce.constant.DesignationType;
import java.util.Date;

/**
 *
 * @author adi
 */
public interface RunningNumberService {
    public String generateYearlyRunningNumber(Date date, DesignationType designationType);
    public String generateMonthlyRunningNumber(Date date, DesignationType designationType);
    public String generateProductRunningNumber(DesignationType designationType);
}
