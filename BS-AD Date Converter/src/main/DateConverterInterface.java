/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Date;

/**
 *
 * @author AmitShrestha
 */
public interface DateConverterInterface {

    public Date convertBsToAd(String bsDate);

    public Date convertBstoAd(String bsDate, int bsDayofMonth, int bsMonth, int bsYear, int index);

    public boolean matchFormat(String bsDate);

    public int getBsIndex(int bsYear);

    public boolean validBsDate(int bsDayOfMonth, int bsMonth, int bsYear);

    public String convertAdToBs(String adDate);

    public String convertAdToBs(String adDate, int adDayOfMonth, int adMonth, int adYear, int index);

    public boolean validAdDate(int adDayOfMonth, int adMonth, int adYear);

    public boolean checkLeapYear(int adYear);

    public int getAdIndex(int adYear);
}
