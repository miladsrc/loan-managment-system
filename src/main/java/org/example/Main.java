package org.example;

import domain.BankCard;
import domain.Loan;
import domain.Refund;
import util.ApplicationContext;
import util.SessionFactorySingleton;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
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

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Refund> list = calculateMonthlyInstallments(100);
        int counter = 1;
        for (Refund li : list) {
            System.out.println(counter + " " + li.getPrice().intValue() + " " + li.getDate().format(dateTimeFormatter));
            counter++;
        }
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


    public static List<Refund> calculateMonthlyInstallments(double loanAmount) {
        final double ANNUAL_INTEREST_RATE = 0.04; // Annual interest rate (4%)
        final int LOAN_PERIOD_YEARS = 5; // Loan period (5 years)
        final int TOTAL_INSTALLMENTS = 60; // Total number of installments
        List<Refund> list = new ArrayList<>();
        double baseInstallment = loanAmount / TOTAL_INSTALLMENTS; // Base monthly installment
        LocalDate lcd = LocalDate.now();

        for (int year = 1; year <= LOAN_PERIOD_YEARS; year++) {
            double installmentMultiplier = Math.pow(2, year - 1); // Step-up multiplier for each year
            double installment = baseInstallment * installmentMultiplier; // Adjusted monthly installment
            double interest = installment * ANNUAL_INTEREST_RATE / 12; // Monthly interest
            double totalMonthlyInstallment = installment + interest;// Total monthly installment with interest
            for (int i = 0; i < 12; i++) {
                lcd=lcd.plusMonths(1);
                list.add(Refund.builder().
                        refundNum(i + 1)
                        .date(lcd)
//                        .loan(loan)
                        .price(totalMonthlyInstallment)
                        .checkout(false)
                        .build()
                );
            }
            lcd = lcd.plusYears(1);
        }
        return list;
    }


    public static boolean checkLoanRegistrationWindow() {
        // تاریخ فعلی
        LocalDate currentDate = LocalDate.now();

        // تاریخ‌های مشخص شده برای آبان
        LocalDate startAban = LocalDate.of(currentDate.getYear(), Month.NOVEMBER, 1);
        LocalDate endAban = startAban.plusWeeks(1);

        // تاریخ‌های مشخص شده برای بهمن
        LocalDate startBahman = LocalDate.of(currentDate.getYear(), Month.FEBRUARY, 25);
        LocalDate endBahman = startBahman.plusWeeks(1);

        // بررسی آیا در دوره‌ی ثبت نام قرار داریم
        if (!currentDate.isBefore(startAban) && !currentDate.isAfter(endAban)) {
            return true;
        } else return !currentDate.isBefore(startBahman) && !currentDate.isAfter(endBahman);
    }

    public static void checkGraduationStatus() {
        // تاریخ فعلی
        LocalDate currentDate = LocalDate.now();

        // تاریخ ورود به دانشگاه
        LocalDate entryDate = LocalDate.of(2020, Month.SEPTEMBER, 1); // تاریخ ورود به دانشگاه را تغییر دهید

        // محاسبه تعداد سال‌های گذشته از ورود به دانشگاه
        int yearsSinceEntry = currentDate.getYear() - entryDate.getYear();

        // محاسبه تعداد سال‌ها برای فارغ التحصیلی
        int yearsForGraduation = 4; // برای کارشناسی

        // بررسی آیا فارغ التحصیل شده است
        if (yearsSinceEntry >= yearsForGraduation) {
            System.out.println("شما فارغ التحصیل شده‌اید.");
            // اگر فارغ التحصیل شده باشد، گزینه درخواست وام غیرفعال شود
            // این قسمت را بر اساس نیاز خود تغییر دهید
        } else {
            System.out.println("شما هنوز فارغ التحصیل نشده‌اید.");
        }
    }


    public static void checkLoanEligibilityForEducation() {
        // تاریخ فعلی
        LocalDate currentDate = LocalDate.now();

        // تاریخ‌های مشخص شده برای نیم‌سال‌ها
        LocalDate firstSemesterStart = LocalDate.of(currentDate.getYear(), Month.MARCH, 1);
        LocalDate firstSemesterEnd = LocalDate.of(currentDate.getYear(), Month.AUGUST, 31);
        LocalDate secondSemesterStart = LocalDate.of(currentDate.getYear(), Month.SEPTEMBER, 1);
        LocalDate secondSemesterEnd = LocalDate.of(currentDate.getYear(), Month.FEBRUARY, 28);

        // بررسی آیا در طول نیم‌سال تحصیلی وام گرفته است
        boolean hasTakenLoan = false; // این قسمت را بر اساس وضعیت وام دانشجو تنظیم کنید

        // محاسبه مبلغ وام بر اساس مقطع تحصیلی
        double loanAmount;
        if (!hasTakenLoan) {
            if (currentDate.isAfter(firstSemesterStart) && currentDate.isBefore(firstSemesterEnd)) {
                // نیم‌سال اول
                loanAmount = 1; // مبلغ برای کاردانی و کارشناسی پیوسته
            } else if (currentDate.isAfter(secondSemesterStart) && currentDate.isBefore(secondSemesterEnd)) {
                // نیم‌سال دوم
                loanAmount = 2; // مبلغ برای دکترای حرفه و دکترای پیوسته
            } else {
                // خطا: در طول نیم‌سال تحصیلی وام گرفته است
                System.out.println("پیام خطا: در طول نیم‌سال تحصیلی وام گرفته‌اید.");
                return;
            }
        } else {
            // نیم‌سال تخصصی
            loanAmount = 3; // مبلغ برای دکترای تخصصی ناپیوسته
        }

        System.out.println("مبلغ وام: " + loanAmount);
    }

}