package org.example;

import domain.BankCard;
import domain.Loan;
import util.ApplicationContext;
import util.SessionFactorySingleton;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        LocalDate local = getDateFromUser ();
//        System.out.println(generatePassword());
//        System.out.println(generatePassword());
//        System.out.println(generatePassword());
//        System.out.println(generatePassword());
//        System.out.println(generatePassword());
//        System.out.println(generatePassword());
//        System.out.println(generatePassword());
//        SessionFactorySingleton.getInstance ();
//        System.out.println (local.getYear ());
//        System.out.println (local.getDayOfMonth ());
//        System.out.println (local.getMonth ());
//        System.out.println (local.getChronology ());
//        System.out.println (local.getDayOfWeek ());

//        Menu menu = new Menu();
//        menu.startMenu();

        calculateMonthlyInstallments(1);
//        BankCard bankCard = BankCard.builder()
//                .build();
//        ApplicationContext.getBankCardService ().saveOrUpdate ( bankCard );

    }

    public static LocalDate getDateFromUser() {
        Scanner scanner = new Scanner(System.in);
        LocalDate date = null;

        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (date == null) {
            System.out.println("enter date (yyyy-mm-dd):");
            String input = scanner.nextLine();

            try {
                date = LocalDate.parse(input, dt);
            } catch (DateTimeException dateTimeException) {
                dateTimeException.printStackTrace();
            }
        }

        return date;
    }


    private static String generatePassword() {
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialCharacters = "!@#$%^&*()_+";

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 2; i++) {
            password.append(lowercase.charAt(random.nextInt(lowercase.length())));
        }
        for (int i = 0; i < 2; i++) {
            password.append(lowercase.toUpperCase().charAt(random.nextInt(lowercase.length())));
        }
        for (int i = 0; i < 2; i++) {
            password.append(digits.charAt(random.nextInt(digits.length())));
        }
        for (int i = 0; i < 2; i++) {
            password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));
        }

        return password.toString();
    }


    public static void calculateMonthlyInstallments(double loanAmount) {
        final double ANNUAL_INTEREST_RATE = 0.04; // Annual interest rate (4%)
        final int LOAN_PERIOD_YEARS = 5; // Loan period (5 years)
        final int TOTAL_INSTALLMENTS = 60; // Total number of installments

        double baseInstallment = loanAmount / TOTAL_INSTALLMENTS; // Base monthly installment

        System.out.println("Monthly installments for a loan of " + loanAmount + " Tomans:");

        for (int year = 1; year <= LOAN_PERIOD_YEARS; year++) {
            double installmentMultiplier = Math.pow(2, year - 1); // Step-up multiplier for each year
            double installment = baseInstallment * installmentMultiplier; // Adjusted monthly installment
            double interest = installment * ANNUAL_INTEREST_RATE / 12; // Monthly interest
            double totalMonthlyInstallment = installment + interest; // Total monthly installment with interest

            System.out.println("Year " + year + ": Monthly installment " + totalMonthlyInstallment + " Tomans");
        }
    }
}