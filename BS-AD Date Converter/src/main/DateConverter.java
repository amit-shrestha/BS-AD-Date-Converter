/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author AmitShrestha
 */
public class DateConverter implements DateConverterInterface {

    @Override
    public Date convertBsToAd(String bsDate) {
        if (matchFormat(bsDate)) {
            int bsYear = 0, bsMonth = 0, bsDayOfMonth = 0;
            String bsDates[] = bsDate.split("-");
            bsDayOfMonth = Integer.parseInt(bsDates[2]);
            bsMonth = Integer.parseInt(bsDates[1]);
            bsYear = Integer.parseInt(bsDates[0]);

            int index = getBsIndex(bsYear);
            if (validBsDate(bsDayOfMonth, bsMonth, bsYear)) {
                return convertBstoAd(bsDate, bsDayOfMonth, bsMonth, bsYear, index);
            } else {
                throw new CatchException("invalid BS date");
            }
        } else {
            throw new CatchException(bsDate);
        }
    }

    @Override
    public boolean matchFormat(String bsDate) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(bsDate);
        return matcher.matches();
    }

    @Override
    public Date convertBstoAd(String bsDate, int bsDayofMonth, int bsMonth, int bsYear, int index) {
        int numberOfDaysPassed = bsDayofMonth - 1;
        for (int i = 0; i <= bsMonth - 2; i++) {
            numberOfDaysPassed += NepaliDateStorage.nepaliMonthDays.get(index)[i];
        }
        String DATE_FORMAT = "dd-MM-yyyy";
        String indexedDate = NepaliDateStorage.equivalentEnglishDate.get(index);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(indexedDate));
        } catch (ParseException ex) {
            Logger.getLogger(DateConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        calendar.add(Calendar.DATE, numberOfDaysPassed);
        Date convertedDate = calendar.getTime();
        return convertedDate;
    }

    @Override
    public int getBsIndex(int bsYear) {
        return bsYear - NepaliDateStorage.nepaliYearStart;
    }

    @Override
    public boolean validBsDate(int bsDayOfMonth, int bsMonth, int bsYear) {
        if (NepaliDateStorage.nepaliYearStart <= bsYear && bsYear <= (NepaliDateStorage.nepaliYearStart + NepaliDateStorage.nepaliMonthDays.size() - 1)) {
            if (bsMonth >= 1 && bsMonth <= 12) {
                int dayOfMonth = NepaliDateStorage.nepaliMonthDays.get(getBsIndex(bsYear))[bsMonth - 1];
                if (bsDayOfMonth <= dayOfMonth) {
                    return true;
                } else {
                    throw new CatchException("invalid day of month " + bsDayOfMonth + " for year " + bsYear + " and month " + bsMonth);
                }
            }
        } else {
            if (bsYear < NepaliDateStorage.nepaliYearStart || bsYear > NepaliDateStorage.nepaliYearStart + NepaliDateStorage.nepaliMonthDays.size() - 1) {
                throw new CatchException("Year not in Range.Valid Year:1970 BS - 2100 BS.");
            }
        }
        return false;
    }

    @Override
    public String convertAdToBs(String adDate) {
        if (matchFormat(adDate)) {
            int adYear = 0, adMonth = 0, adDayOfMonth = 0;
            String adDates[] = adDate.split("-");
            adDayOfMonth = Integer.parseInt(adDates[2]);
            adMonth = Integer.parseInt(adDates[1]);
            adYear = Integer.parseInt(adDates[0]);

            int index = getAdIndex(adYear);
            if (validAdDate(adDayOfMonth, adMonth, adYear)) {
                return convertAdToBs(adDate, adDayOfMonth, adMonth, adYear, index);
            } else {
                throw new CatchException("invalid AD date");
            }
        } else {
            throw new CatchException("invalid format.");
        }
    }

    @Override
    public String convertAdToBs(String adDate, int adDayOfMonth, int adMonth, int adYear, int index) {
        int numberOfDaysPassed = adDayOfMonth - 1;
        for (int i = 0; i <= adMonth - 2; i++) {
            if (checkLeapYear(adYear)) {
                numberOfDaysPassed += EnglishDateStorage.englishMonthDaysInLeapYear[i];
            } else {
                numberOfDaysPassed += EnglishDateStorage.englishMonthDays[i];
            }
        }
        String indexedDate = EnglishDateStorage.equivalentNepaliDate.get(index);
        String correspondingBsDate[] = indexedDate.split("-");
        int correspondingBsDayOfMonth = Integer.parseInt(correspondingBsDate[0]);
        int correspondingBsMonth = Integer.parseInt(correspondingBsDate[1]);
        int correspondingBsYear = Integer.parseInt(correspondingBsDate[2]);
        int bsYearIndex = getBsIndex(correspondingBsYear);
        for (int i = 1; i <= numberOfDaysPassed; i++) {
            if (correspondingBsDayOfMonth < NepaliDateStorage.nepaliMonthDays.get(bsYearIndex)[correspondingBsMonth - 1]) {
                correspondingBsDayOfMonth += 1;
            } else if (correspondingBsDayOfMonth >= NepaliDateStorage.nepaliMonthDays.get(bsYearIndex)[correspondingBsMonth - 1]) {
                if (correspondingBsMonth < 12) {
                    correspondingBsMonth++;
                    correspondingBsDayOfMonth = 1;
                } else if (correspondingBsMonth == 12) {
                    correspondingBsYear++;
                    correspondingBsMonth = 1;
                    correspondingBsDayOfMonth = 1;
                    bsYearIndex = getBsIndex(correspondingBsYear);
                }
            }
        }
        return String.valueOf(correspondingBsYear).concat("-").concat(String.valueOf(correspondingBsMonth).concat("-").concat(String.valueOf(correspondingBsDayOfMonth)));
    }

    @Override
    public boolean validAdDate(int adDayOfMonth, int adMonth, int adYear) {
        int dayOfMonth = 100;
        if (EnglishDateStorage.englishYearStart <= adYear && EnglishDateStorage.englishYearEnd >= adYear) {
            if (adMonth >= 1 && adMonth <= 12) {
                if (checkLeapYear(adYear)) {
                    dayOfMonth = EnglishDateStorage.englishMonthDaysInLeapYear[adMonth - 1];
                } else {
                    dayOfMonth = EnglishDateStorage.englishMonthDays[adMonth - 1];
                }
                if (adDayOfMonth <= dayOfMonth) {
                    return true;
                } else {
                    throw new CatchException("invalid day of month " + adDayOfMonth + " for year " + adYear + " and month " + adMonth);
                }
            }
        } else {
            if (adYear < EnglishDateStorage.englishYearStart || adYear > EnglishDateStorage.englishYearEnd) {
                throw new CatchException("Year not in Range.Valid Year:1943 AD - 2042 AD");
            }
        }
        return false;
    }

    @Override
    public boolean checkLeapYear(int adYear) {
        if (adYear % 4 == 0) {
            if (adYear % 100 == 0) {
                if (adYear % 400 == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public int getAdIndex(int adYear) {
        return adYear - EnglishDateStorage.englishYearStart;
    }
}
