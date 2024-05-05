package org.example;

import domain.*;
import logic.service.BankCardService;
import logic.service.LoanService;
import logic.service.RefundService;
import logic.service.StudentService;
import util.ApplicationContext;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class Menu {


    Scanner scanner = new Scanner(System.in);


    BankCardService bankCardService = ApplicationContext.getBankCardService();
    LoanService loanService = ApplicationContext.getLoanService();
    RefundService refundService = ApplicationContext.getRefundService();
    StudentService studentService = ApplicationContext.getStudentService();


    Optional<Student> student;


    public void startMenu() {

        while (true) {

            System.out.println("Welcome Studetn!");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Update Information");
            System.out.print("Please choose an option (1/2/3): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Call login method
                    break;
                case 2:
                    // Call register method
                    break;
                case 3:
                    // Call update information method
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }


    public void signUp() {

        City selectedCity = null;
        LocalDate date = null;
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        TypeUniversity selectedType = null;
        Grade selectedGrade = null;


        //Display and get the normal input from user
        String firstName = getInput(scanner, "Please enter first name:");
        String lastName = getInput(scanner, "Please enter last name:");
        String fatherName = getInput(scanner, "Please enter father name:");
        String motherName = getInput(scanner, "Please enter mother name:");
        String nationalCode = getInput(scanner, "Please enter national code:", "\\d{10}");
        String studentNumber = getInput(scanner, "Please enter student number:");
        String uniName = getInput(scanner, "Please enter university name:");
        boolean dorm = getYesNoInput(scanner, "Do you live in a dorm (Y/N)?");
        boolean isEducating = getYesNoInput(scanner, "Are you currently studying (Y/N)?");

        // Display the list of university types
        while (selectedType == null) {
            System.out.println("Please select one of the following university types by entering the corresponding number:");
            for (TypeUniversity type : TypeUniversity.values()) {
                System.out.println((type.ordinal() + 1) + ". " + type);
            }
            // Exception handling for user input
            try {
                int selectedOrdinal = scanner.nextInt() - 1; // Adjust for zero-based index

                // Check if the ordinal is within the valid range
                if (selectedOrdinal >= 0 && selectedOrdinal < TypeUniversity.values().length) {
                    selectedType = TypeUniversity.values()[selectedOrdinal];
                    System.out.println("You have selected: " + selectedType);
                } else {
                    System.out.println("Invalid selection. Please enter a number between 1 and " + TypeUniversity.values().length);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numerical value.");
                scanner.next(); // Clear the invalid input
            }
        }

        //Gettin entry date of student
        while (date == null) {
            System.out.println("Please enter your entry date (yyyy-mm-dd):");
            String input = scanner.nextLine();

            try {
                date = LocalDate.parse(input, DATE_FORMATTER);
                System.out.println("Entry date is: " + date);

            } catch (DateTimeException e) {
                System.out.println("The date entered is not valid. Please try again using the format yyyy-mm-dd.");
            }
        }


        //Getting grade
        while (selectedGrade == null) {
            // Display the list of grades
            System.out.println("Please select one of the following grades by entering the corresponding number:");
            for (Grade grade : Grade.values()) {
                System.out.println((grade.ordinal() + 1) + ". " + grade);
            }
            // Exception handling for user input
            try {
                int selectedOrdinal2 = scanner.nextInt() - 1; // Adjust for zero-based index

                // Check if the ordinal is within the valid range
                if (selectedOrdinal2 >= 0 && selectedOrdinal2 < Grade.values().length) {
                    selectedGrade = Grade.values()[selectedOrdinal2];
                    System.out.println("You have selected: " + selectedGrade);
                } else {
                    System.out.println("Invalid selection. Please enter a number between 1 and " + Grade.values().length);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numerical value.");
                scanner.next(); // Clear the invalid input
            }
        }


        //Getting city
        while (selectedCity == null) {
            System.out.println("Please enter your city:");
            for (City city : City.values()) {
                System.out.println(city.getCityName());
            }

            String cityNameInput = scanner.nextLine().trim();

            for (City city : City.values()) {
                if (city.getCityName().equalsIgnoreCase(cityNameInput)) {
                    selectedCity = city;
                    System.out.println("You have selected the city: " + selectedCity.getCityName());
                    break;
                }
            }

            if (selectedCity == null) {
                System.out.println("Invalid city name. Please try again.");
            }
        }



        //ask is marrid
        System.out.println("Are you married (Y/N)?");
        String marriedInput = scanner.next().trim();

        boolean isMarried = false;
        Optional<Student> partner = Optional.empty();

        try {
            if ("Y".equalsIgnoreCase(marriedInput)) {
                System.out.println("Enter your partner's national code:");
                String nationalCodePartner = scanner.next();
                System.out.println("Enter your partner's password:");
                String passwordOfPartner = scanner.next();

                partner = studentService.getStudentByNationalCodeAndPassword(nationalCodePartner, passwordOfPartner);
                isMarried = partner.isPresent();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while processing the information. Please try again.");
            e.printStackTrace();
        }


        // Getting bank card info
        BankCard bankCard = getBankCardInfoFromUser();

        // Generating Password
        String password = generatePassword();

        // Create Student object
        Student student = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .fatheName(fatherName)
                .motherName(motherName)
                .nationalCode(nationalCode)
                .studentNumber(studentNumber)
                .city(selectedCity)
                .isMarried(isMarried)
                .partner(partner.orElse(null))
                .enteryDate(date)
                .universityName(uniName)
                .typeUniversity(selectedType)
                .grade(selectedGrade)
                .dorm(dorm)
                .isEducate(isEducating)
                .password(password)
                .bankCard(bankCard)
                .build();

        // Print national code and password
        System.out.println("National Code: " + nationalCode);
        System.out.println("Password: " + password);

    }


    //-----------------------------METHOD-----------------------------------

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


    private static String getInput(Scanner scanner, String prompt) {
        System.out.println(prompt);
        String input = scanner.next();
        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty.");
        }
        return input;
    }

    private static String getInput(Scanner scanner, String prompt, String regex) {
        String input;
        do {
            System.out.println(prompt);
            input = scanner.next();
            if (!input.matches(regex)) {
                System.out.println("Invalid input. Please try again.");
            }
        } while (!input.matches(regex));
        return input;
    }

    private static boolean getYesNoInput(Scanner scanner, String question) {
        String input;
        while (true) {
            System.out.println(question);
            input = scanner.next().trim();
            if ("Y".equalsIgnoreCase(input)) {
                return true;
            } else if ("N".equalsIgnoreCase(input)) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'Y' for yes or 'N' for no.");
            }
        }
    }


    public BankCard getBankCardInfoFromUser() {
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Scanner scanner = new Scanner(System.in);
        BankCard bankCard = new BankCard();

        System.out.println("Please enter your bank card number (16 digits):");
        String cardNumber = scanner.nextLine();
        while (!cardNumber.matches("\\d{16}")) {
            System.out.println("Invalid card number. Please try again:");
            cardNumber = scanner.nextLine();
        }
        bankCard.setCardNumber(cardNumber);

        System.out.println("Please enter your CVV2 (4 digits):");
        int cvv2 = scanner.nextInt();
        while (cvv2 < 1000 || cvv2 > 9999) {
            System.out.println("Invalid CVV2. Please try again:");
            cvv2 = scanner.nextInt();
        }
        bankCard.setCvv2(cvv2);

        LocalDate expireDate = null;
        while (expireDate == null) {
            System.out.println("Please enter your card's expiration date (yyyy-MM-dd):");
            String input = scanner.next();
            try {
                expireDate = LocalDate.parse(input, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again using the format yyyy-MM-dd:");
            }
        }
        bankCard.setExpireDate(expireDate);

        System.out.println("Please enter your card balance:");
        long balance = scanner.nextLong();
        while (balance < 0) {
            System.out.println("Balance cannot be negative. Please enter a positive number:");
            balance = scanner.nextLong();
        }
        bankCard.setBalance(balance);

        return bankCard;
    }

}




