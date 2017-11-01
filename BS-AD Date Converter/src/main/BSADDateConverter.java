/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author AmitShrestha
 */
public class BSADDateConverter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("This program converts BS date into AD date(input range: 1970 BS - 2100 BS) \n and vice versa(input range:1943 AD - 2042 AD). ");
        while (true) {
            DateConverterInterface dateConverterInterface = new DateConverter();
            String bsDate;
            Date adDate;
            String adDateString;
            Scanner sc;
            Scanner sc_menu;
            String menu;
            System.out.println("1. Convert BS to AD.");
            System.out.println("2. Convert AD to BS.");
            System.out.println("Please enter your choice(1/2): ");
            sc_menu = new Scanner(System.in);
            menu = sc_menu.next();
            switch (menu) {
                case "1":
                    System.out.println("BS Date in yyyy-mm-dd format: ");
                    sc = new Scanner(System.in);
                    bsDate = sc.next();
                    adDate = dateConverterInterface.convertBsToAd(bsDate);
                    System.out.println("AD: " + adDate);
                    break;
                case "2":
                    System.out.println("AD Date in yyyy-mm-dd format: ");
                    sc = new Scanner(System.in);
                    adDateString = sc.next();
                    bsDate = dateConverterInterface.convertAdToBs(adDateString);
                    System.out.println("BS: " + bsDate);
                    break;
                default:
                    System.out.println("Enter Valid Choice!!!");
            }
        }
    }
}
