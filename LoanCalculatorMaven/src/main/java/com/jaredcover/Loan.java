 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaredcover;

/**
 *
 * @author jared
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Loan implements Serializable {

    private String name;
    private String lender;
    private boolean compoundsDaily;
    private boolean compoundsMonthly;
    private boolean compoundsYearly;
    private BigDecimal principal;
    private BigDecimal amountOwed;
    private BigDecimal InterestRate;
    final static double DAYS_IN_YEAR = 365;
    final static BigDecimal BD_DAYS_IN_YEAR = new BigDecimal(DAYS_IN_YEAR);
    private Calendar dateCreated;
    private Calendar dayLastAccrued;
    private Calendar today;
    private long daysPassedSinceCreated;
    private long daysSinceLastAccrued;
    private int numOfTimesAccrued;

    public int getNumOfTimesAccrued() {
        return numOfTimesAccrued;
    }

    public void setNumOfTimesAccrued(int numOfTimesAccrued) {
        this.numOfTimesAccrued = numOfTimesAccrued;
    }

    public Loan() {

        dateCreated = Calendar.getInstance(Locale.US);
        compoundsDaily = false;
        compoundsMonthly = false;
        compoundsYearly = false;
        daysPassedSinceCreated = 0;
        dayLastAccrued = Calendar.getInstance(Locale.US);
        today = dateCreated;
    }

    

    public void accrueInterestDaily() {
        
        calculateDaysSinceLastAccrual();
        BigDecimal dailyRate = InterestRate.divide(BD_DAYS_IN_YEAR, MathContext.DECIMAL128);

        for (long i = 0; i < daysSinceLastAccrued; i++) {
            BigDecimal interest = amountOwed.multiply(dailyRate, MathContext.DECIMAL128);
            amountOwed = amountOwed.add(interest, MathContext.DECIMAL128);
            numOfTimesAccrued++;
            
        }

        dayLastAccrued = Calendar.getInstance(Locale.US);
        

    }

    public void calculateDaysSinceLastAccrual() {
        long end = today.getTimeInMillis();
        long start = dayLastAccrued.getTimeInMillis();
        daysSinceLastAccrued = TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
                

    }

    public void accrueInterestMonthly() {

    }

    public void accrueInterestYearly() {

    }

    public void updateLoan() {
        today = Calendar.getInstance(Locale.US);
        calculateDaysSinceLastAccrual();
        
        if (compoundsDaily = true) {
            if (daysSinceLastAccrued != 0) {
                accrueInterestDaily();
            }
        } else if (compoundsMonthly = true) {
            accrueInterestMonthly();
        } else if (compoundsYearly = true) {
            accrueInterestYearly();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal.setScale(2, RoundingMode.UP);
    }

    public void setAmountOwed(BigDecimal amountOwed) {
        this.amountOwed = amountOwed.setScale(2, RoundingMode.UP);
    }

    public void setInterestRate(BigDecimal InterestRate) {
        BigDecimal oneHundered = new BigDecimal(100);
        this.InterestRate = InterestRate.divide(oneHundered);
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDayLastAccrued(Calendar dayLastAccrued) {
        this.dayLastAccrued = dayLastAccrued;
    }

    public String getName() {
        return name;
    }

    public String getLender() {
        return lender;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public BigDecimal getAmountOwed() {
        return amountOwed;
    }

    public BigDecimal getInterestRate() {
        return InterestRate;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public Calendar getDayLastAccrued() {
        return dayLastAccrued;
    }

    public void setCompoundsDaily(boolean compoundsDaily) {
        this.compoundsDaily = compoundsDaily;
    }

    public void setCompoundsMonthly(boolean compoundsMonthly) {
        this.compoundsMonthly = compoundsMonthly;
    }

    public void setCompoundsYearly(boolean compoundsYearly) {
        this.compoundsYearly = compoundsYearly;
    }

    public boolean isCompoundsDaily() {
        return compoundsDaily;
    }

    public boolean isCompoundsMonthly() {
        return compoundsMonthly;
    }

    public boolean isCompoundsYearly() {
        return compoundsYearly;
    }

    public void setDaysPassedSinceCreated(long daysPassedSinceCreated) {
        this.daysPassedSinceCreated = daysPassedSinceCreated;
    }

    public long getDaysPassedSinceCreated() {
        return daysPassedSinceCreated;
    }
}
