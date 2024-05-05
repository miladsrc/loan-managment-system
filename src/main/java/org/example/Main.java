package org.example;

import domain.BankCard;
import domain.Loan;
import util.ApplicationContext;
import util.SessionFactorySingleton;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        LocalDate local = getDateFromUser ();

        SessionFactorySingleton.getInstance ();
//        System.out.println (local.getYear ());
//        System.out.println (local.getDayOfMonth ());
//        System.out.println (local.getMonth ());
//        System.out.println (local.getChronology ());
//        System.out.println (local.getDayOfWeek ());


        BankCard bankCard = BankCard.builder()
                .build();
        ApplicationContext.getBankCardService ().saveOrUpdate ( bankCard );

    }

    public static LocalDate getDateFromUser() {
       Scanner scanner = new Scanner(System.in);
       LocalDate date = null;

       DateTimeFormatter dt = DateTimeFormatter.ofPattern ( "yyyy-MM-dd" );

       while(date == null)
       {
           System.out.println ("enter date (yyyy-mm-dd):");
           String input = scanner.nextLine ();

           try{
                date = LocalDate.parse (input, dt );
           }catch (DateTimeException dateTimeException){
               dateTimeException.printStackTrace ();
           }
       }

        return date;
    }
}