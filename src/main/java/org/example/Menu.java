package org.example;

import base.exception.NotFoundException;
import domain.*;
import logic.service.BankCardService;
import logic.service.LoanService;
import logic.service.RefundService;
import logic.service.StudentService;
import org.hibernate.exception.DataException;
import org.hibernate.service.spi.ServiceException;
import util.ApplicationContext;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


public class Menu {


    Scanner scanner = new Scanner(System.in);


    BankCardService bankCardService = ApplicationContext.getBankCardService();
    LoanService loanService = ApplicationContext.getLoanService();
    RefundService refundService = ApplicationContext.getRefundService();
    StudentService studentService = ApplicationContext.getStudentService();


    private Student student = null;

    private BankCard bankCard = null;


    public void startMenu() {
        boolean flag = true;
        while (flag) {
            try {
                System.out.println("Welcome Student!");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Exit");
                System.out.print("Please choose an option (1/2/3): ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> studentLogin();
                    case 2 -> studentSignUp();
                    case 3 -> flag = false;
                    default -> System.out.println("Invalid choice. Please select 1 or 2 or 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //-----------------------------LOGINGIN METHOD------------------------------------

    public void studentLogin() {
        while (student == null) {
            try {
                System.out.print("Enter your  national code: ");
                String nationalCodePartner = scanner.next();
                System.out.print("Enter your  password: ");
                String passwordOfPartner = scanner.next();

                student = studentService.getStudentByNationalCodeAndPassword(nationalCodePartner, passwordOfPartner);
                Thread.sleep(1000);
                bankCard = student.getBankCard();


            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter valid values.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again later.");
                e.printStackTrace();
            }
        }
        choinceMenu();
    }


    public void choinceMenu() {
        System.out.println("\nChoose please :");
        System.out.println("1: Take Loan ");
        System.out.println("2: Refund Loan");
        int input = scanner.nextInt();

        switch (input) {
            case 1 -> loginMenu();
            case 2 -> refundMenu();
            default -> {
                System.out.println("Invalid input , please try again !");
                choinceMenu();
            }
        }
    }


    //-----------------------------LOGIN MENU ------------------------------------

    private void loginMenu() {


        System.out.printf("Welcome %s %s ", student.getFirstName(), student.getLastName());
        System.out.println("\nPlease select the type of loan you want to register for:");
        System.out.println("1. Tuition Loan");
        System.out.println("2. Educational Loan");
        System.out.println("3. Housing Deposit Loan");
        System.out.print("Your choice (1-3): ");
        int loanType = scanner.nextInt();

        switch (loanType) {
            case 1 -> registerTuitionLoan();
            case 2 -> registerEducationalLoan();
            case 3 -> registerHousingDepositLoan();
            default -> System.out.println("Invalid choice.");
        }

    }

    //REGESTER LOAN LOGIC
    public void registerTuitionLoan() {

        if (student.getTypeUniversity().equals(TypeUniversity.DOLATI)) {
            System.out.println("This tuition can't be applyied by you because of Dolati uni !");
            choinceMenu();
        }

        boolean flag = false;
        while (!flag) {
            try {
                flag = getCardInfo();
                if (flag) {
                    if (registerEducation()) {
                        Loan tuitionLoan = Loan.builder()
                                .loanType(LoanType.TUITION)
                                .student(student)
                                .amount(tuituinLoanAmount())
                                .grade(student.getGrade())
                                .date(LocalDate.now())
                                .checkOut(false)
                                .build();

                        //Set balance
                        List<Refund> tuitionRefund = calculateMonthlyInstallments(tuituinLoanAmount(), tuitionLoan);
                        tuitionLoan.setRefundList(tuitionRefund);
                        bankCard.setBalance(tuituinLoanAmount());

                        // Persist in the database

                        Loan savedLoan = loanService.saveOrUpdate(tuitionLoan);
                        bankCardService.saveOrUpdate(bankCard);
                        for (Refund refund : tuitionLoan.getRefundList()) {
                            refundService.saveOrUpdate(refund);
                        }
                        if (savedLoan.getId() != null) {
                            System.out.println("\n" + savedLoan.getLoanType() + " is now registered for " +
                                    student.getFirstName() + " " + student.getLastName());
                        }
                        // Back to the menu
                        System.out.println("Registration for Tuition Loan completed.");
                        choinceMenu();
                    } else {
                        loginMenu();
                    }
                }
            } catch (Exception e) {
                System.out.println("An error occurred during loan registration: " + e.getMessage());
            }
        }
    }

    public void registerEducationalLoan() {
        boolean flag = false;
        while (!flag) {
            try {
                flag = getCardInfo();
                if (flag)
                    if (registerEducation()) {
                        Loan educationLoan = Loan.builder()
                                .loanType(LoanType.EDUCATION)
                                .student(student)
                                .amount(educationLoanAmount())
                                .grade(student.getGrade())
                                .date(LocalDate.now())
                                .checkOut(false)
                                .build();

                        //Set balance
                        List<Refund> educationRefund = calculateMonthlyInstallments(educationLoanAmount(), educationLoan);
                        educationLoan.setRefundList(educationRefund);
                        bankCard.setBalance(educationLoanAmount());

                        // Persist in the database
                        Loan savedLoan = loanService.saveOrUpdate(educationLoan);
                        bankCardService.saveOrUpdate(bankCard);
                        for (Refund refund : educationLoan.getRefundList()) {
                            refundService.saveOrUpdate(refund);
                        }
                        if (savedLoan.getId() != null) {
                            System.out.println("\n" + savedLoan.getLoanType() + " is now registered for " +
                                    student.getFirstName() + " " + student.getLastName());
                        }
                        // Back to the menu
                        choinceMenu();
                    } else {
                        loginMenu();
                    }
            } catch (Exception e) {
                System.out.println("An error occurred during loan registration: " + e.getMessage());
            }
        }
    }


    public void registerHousingDepositLoan() {
        boolean flag = false;
        while (!flag) {
            try {
                flag = getCardInfo();
                if (flag) {
                    System.out.println("Bank card information matches the student's bank card information.");
                    if (registerEducation()) {
                        if (checkIfValidForHousingLoan()) {
                            Loan housingLoan = Loan.builder()
                                    .loanType(LoanType.HOUSING)
                                    .student(student)
                                    .amount(housingLoanAmount())
                                    .grade(student.getGrade())
                                    .date(LocalDate.now())
                                    .checkOut(false)
                                    .build();

                            //Set balance
                            List<Refund> housingRefund = calculateMonthlyInstallments(housingLoanAmount(), housingLoan);
                            housingLoan.setRefundList(housingRefund);
                            bankCard.setBalance(housingLoanAmount());

                            // Persist in the database
                            Loan savedLoan = loanService.saveOrUpdate(housingLoan);
                            bankCardService.saveOrUpdate(bankCard);
                            for (Refund refund : housingLoan.getRefundList()) {
                                refundService.saveOrUpdate(refund);
                            }
                            if (savedLoan.getId() != null) {
                                System.out.println("\n" + savedLoan.getLoanType() + " is now registered for " +
                                        student.getFirstName() + " " + student.getLastName());
                            }
                            choinceMenu();
                        } else {
                            System.out.println("You're not valid for Housing Loan ..!");
                            loginMenu();
                        }
                    } else {
                        System.out.println("Your Education Register is not valid for this loan!");
                        loginMenu();
                    }
                }

            } catch (Exception e) {
                System.out.println("An error occurred during loan registration: " + e.getMessage());
            }
        }
    }


    //---------------------------------REGISTER LOAN--------------------------------------------


    public Boolean checkIfValidForHousingLoan() {
        if (student.getPartner().getLoanList().stream().anyMatch(loan -> !loan.getLoanType().equals(LoanType.HOUSING))) {
            if (!student.isDorm() && student.isMarried()) {
                if (student.getLoanList().stream().anyMatch(loan -> !loan.getLoanType().equals(LoanType.HOUSING))) {
                    return true;
                }
            }
        }
        return false;
    }

    public long housingLoanAmount() {
        int grade = student.getGrade().ordinal();
        return switch (grade) {
            case 0 -> 32000;
            case 1, 2, 3, 5, 6, 7, 11 -> 26000;
            default -> {
                yield 19500;
            }
        };
    }

    private boolean getCardInfo() {

        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        BankCard bankCard1 = new BankCard();


        System.out.println(bankCard.toString());
        try {
            System.out.println("Please enter your bank card number (16 digits):");
            String cardNumber = scanner.next().trim();
            scanner.nextLine();
            while (!cardNumber.matches("\\d{16}")) {
                System.out.println("Invalid card number. Please try again:");
                cardNumber = scanner.nextLine();
            }
            bankCard1.setCardNumber(cardNumber);


            System.out.println("Please enter your CVV2 (4 digits):");
            int cvv2 = scanner.nextInt();
            while (cvv2 < 1000 || cvv2 > 9999) {
                System.out.println("Invalid CVV2. Please try again:");
                cvv2 = scanner.nextInt();
            }
            bankCard1.setCvv2(cvv2);

            LocalDate expireDate = null;
            while (expireDate == null) {
                System.out.println("Please enter your card's expiration date (yyyy-MM-dd):");
                String input = scanner.next().trim();
                try {
                    expireDate = LocalDate.parse(input, DATE_FORMATTER);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please try again using the format yyyy-MM-dd:");
                }
            }
            bankCard1.setExpireDate(expireDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bankCard1.getCardNumber().equals(student.getBankCard().getCardNumber()) &&
                bankCard1.getCvv2() == student.getBankCard().getCvv2() &&
                bankCard1.getExpireDate().equals(student.getBankCard().getExpireDate());

    }

    public boolean registerEducation() {

        LocalDate currentDate = LocalDate.now();
        LocalDate firstSemesterStart = LocalDate.of(currentDate.getYear(), Month.MARCH, 1);
        LocalDate firstSemesterEnd = LocalDate.of(currentDate.getYear(), Month.AUGUST, 31);
        LocalDate secondSemesterStart = LocalDate.of(currentDate.getYear(), Month.SEPTEMBER, 1);
        LocalDate secondSemesterEnd = LocalDate.of(currentDate.getYear(), Month.FEBRUARY, 28);

        //check if graduated!
        if (isGraduated()) {
            //check if in window of registaring loan
            if (checkLoanRegistrationWindow()) {
                //check if not in semester
                if (hasStudentTakenLoanThisSemester(firstSemesterStart, firstSemesterEnd) && hasStudentTakenLoanThisSemester(secondSemesterStart, secondSemesterEnd)) {
                    return true;
                } else {
                    System.out.println("You have been taken loan before in this semester");
                }
            } else {
                System.out.println("Your not in the right registration window !");
            }
        } else {
            System.out.println("You are graduated and caanot apply for any loan !");
            refundMenu();
        }
        return false;
    }

    //--------------------------------REGISTER LOAN METHODS------------------------------------


    public boolean checkLoanRegistrationWindow() {
        /**
         * In this method
         * 1->get current date
         * 2->get choosen deadline
         * 3->check the specified dates
         * 4->return boolean
         */
        LocalDate currentDate = LocalDate.now();

        LocalDate startAban = LocalDate.of(currentDate.getYear(), Month.NOVEMBER, 1);
        LocalDate endAban = startAban.plusWeeks(1);

        LocalDate startBahman = LocalDate.of(currentDate.getYear(), Month.FEBRUARY, 25);
        LocalDate endBahman = startBahman.plusWeeks(1);

        if (!currentDate.isBefore(startAban) && !currentDate.isAfter(endAban)) {
            return true;
        } else return !currentDate.isBefore(startBahman) && !currentDate.isAfter(endBahman);
    }

    public List<Refund> calculateMonthlyInstallments(double loanAmount, Loan loan) {
        final double ANNUAL_INTEREST_RATE = 0.04; // Annual interest rate (4%)
        final int LOAN_PERIOD_YEARS = 5; // Loan period (5 years)
        final int TOTAL_INSTALLMENTS = 60; // Total number of installments
        List<Refund> list = new ArrayList<>();
        double baseInstallment = loanAmount / TOTAL_INSTALLMENTS; // Base monthly installment
        LocalDate lcd = LocalDate.now();
        int loanNum = 0;

        for (int year = 1; year <= LOAN_PERIOD_YEARS; year++) {
            double installmentMultiplier = Math.pow(2, year - 1); // Step-up multiplier for each year
            double installment = baseInstallment * installmentMultiplier; // Adjusted monthly installment
            double interest = installment * ANNUAL_INTEREST_RATE / 12; // Monthly interest
            double totalMonthlyInstallment = installment + interest;// Total monthly installment with interest
            for (int i = 0; i < 12; i++) {
                loanNum += 1;
                lcd = lcd.plusMonths(1);
                list.add(Refund.builder().
                        refundNum(loanNum)
                        .date(lcd)
                        .loan(loan)
                        .price(totalMonthlyInstallment)
                        .checkout(false)
                        .build()
                );
            }
            lcd = lcd.plusYears(1);
        }
        return list;
    }

    public long educationLoanAmount() {
        int grade = student.getGrade().ordinal();
        return switch (grade) {
            case 0, 1, 2 -> 1900;
            case 4, 5, 6, 7 -> 2250;
            case 8 -> 2600;
            default -> throw new IllegalStateException("Unexpected value: " + grade);
        };
    }

    public long tuituinLoanAmount() {
        int grade = student.getGrade().ordinal();
        return switch (grade) {
            case 0, 1, 2 -> 1300;
            case 4, 5, 6, 7 -> 2600;
            case 8 -> 6500;
            default -> throw new IllegalStateException("Unexpected value: " + grade);
        };
    }

    public boolean isGraduated() {
        LocalDate localDateNow = LocalDate.now();
        LocalDate studentDate = student.getEnteryDate();

        int grade = student.getGrade().ordinal();
        int yearsPassed = localDateNow.getYear() - studentDate.getYear();

        int requiredYears = switch (grade) {
            case 0, 4 -> 2;
            case 1 -> 4;
            case 3 -> 6;
            case 5, 6, 7 -> 5;
            default -> 0;
        };

        return yearsPassed >= requiredYears;
    }

    public boolean hasStudentTakenLoanThisSemester(LocalDate semesterStart, LocalDate semesterEnd) {
        for (Loan loan : student.getLoanList()) {
            if (!loan.getDate().isBefore(semesterStart) && !loan.getDate().isAfter(semesterEnd)) {
                //TODO if loan registered in semester of Year this will return false
                return false;
            }
        }
        return true;
    }
    //-----------------------------REFUND MENU ------------------------------------

    private void refundMenu() {
        System.out.printf("Greeting's %s %s Nice To See You Again !", student.getFirstName(), student.getLastName());

        System.out.println("Your Loans:\n");
        student.getLoanList().forEach(loan -> System.out.printf("ID: %d - Type: %s%n", loan.getId(), loan.getLoanType()));


        System.out.print("Please enter the ID of the loan you want to make a payment for: ");
        long loanId = scanner.nextLong();

        Loan selectedLoan = student.getLoanList().stream()
                .filter(loan1 -> loan1.getId() == loanId)
                .findFirst()
                .orElse(null);

        if (selectedLoan != null) {
            displayRefundMenu(selectedLoan);
        } else {
            System.out.println("Procedding to payment for loan ID: " + loanId + " not found !");
            refundMenu();
        }


    }

    private void displayRefundMenu(Loan selectedLoan) {
        while (true) {
            try {
                System.out.println("\nWelcome back! Please choose an option:");
                System.out.println("1. View Paid Installments");
                System.out.println("2. View Unpaid Installments");
                System.out.println("3. Make a Payment");
                System.out.println("4. Exit");
                System.out.print("Enter your choice (1/2/3/4): ");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> showPaidInstallments(selectedLoan);
                    case 2 -> showUnpaidInstallments(selectedLoan);
                    case 3 -> makePayment(selectedLoan);
                    case 4 -> choinceMenu();
                    default -> System.out.println("Invalid choice. Please select 1, 2, or 3.");
                }
            } catch (InputMismatchException inputMismatchException) {
                inputMismatchException.getMessage();
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void showPaidInstallments(Loan selectedLoan) {
        System.out.println("Paid Installments for Loan Type: " + selectedLoan.getLoanType());
        selectedLoan.getRefundList().stream()
                .filter(refund1 -> refund1.isCheckout()) // فیلتر کردن بر اساس وضعیت پرداخت شده
                .forEach(refund -> System.out.printf("NO:%s  ---> date : %s, amount : %d%n", refund.getRefundNum(), refund.getDate(), refund.getPrice().intValue()));
        displayRefundMenu(selectedLoan);
    }

    private void showUnpaidInstallments(Loan selectedLoan) {
        System.out.println("UnPaid Installments for Loan Type: " + selectedLoan.getLoanType());
        selectedLoan.getRefundList().stream()
                .filter(refund1 -> !refund1.isCheckout()) // فیلتر کردن بر اساس وضعیت پرداخت نشده
                .forEach(refund -> System.out.printf("NO:%s  ---> date : %s, amount : %d%n", refund.getRefundNum(), refund.getDate(), refund.getPrice().intValue()));
        displayRefundMenu(selectedLoan);
    }

    private void makePayment(Loan selectedLoan) throws NotFoundException {
        try {
            System.out.println("UnPaid Installments for Loan Type: " + selectedLoan.getLoanType());
            selectedLoan.getRefundList().stream()
                    .filter(refund -> !refund.isCheckout()) // فیلتر کردن بر اساس وضعیت پرداخت نشده
                    .forEach(refund -> System.out.printf("NO:%s  ---> date : %s, amount : %d%n",
                            refund.getId(), refund.getDate(), refund.getPrice().intValue()));

            System.out.println("Choose to refund loan:");
            long refundId = scanner.nextLong();

            Refund refund = refundService.findById(refundId);
            if (refund != null) {
                refund.setCheckout(true);
                refundService.saveOrUpdate(refund);
                System.out.println("Refund with ID: " + refundId + " has been marked as checked.");
            } else {
                System.out.println("Refund with ID: " + refundId + " not found.");
            }
        } catch (InputMismatchException ime) {
            System.out.println("Error: Invalid input. Please enter a valid number for the refund ID.");
            scanner.nextLine(); // to clear the buffer
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            displayRefundMenu(selectedLoan);
        }
    }


    // Main method to run the menu


    //-----------------------------SIGN UP METHOD------------------------------------
    public void studentSignUp() throws InterruptedException {

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
        int contractNumber = Integer.parseInt(getInput(scanner, "enter your contract number:"));
        String phoneNumber = getInput(scanner, "enter your phone number:");
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
            String input = scanner.next();
            scanner.nextLine();

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
        Student partner = null;

        try {
            if ("Y".equalsIgnoreCase(marriedInput)) {
                System.out.println("Enter your partner's national code:");
                String nationalCodePartner = scanner.next();
                System.out.println("Enter your partner's password:");
                String passwordOfPartner = scanner.next();

                partner = studentService.getStudentByNationalCodeAndPassword(nationalCodePartner, passwordOfPartner);
                isMarried = partner != null && partner.getPartner() == null;
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
        Student student1 = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .fatheName(fatherName)
                .motherName(motherName)
                .nationalCode(nationalCode)
                .studentNumber(studentNumber)
                .city(selectedCity)
                .isMarried(isMarried)
                .partner(partner)
                .enteryDate(date)
                .universityName(uniName)
                .typeUniversity(selectedType)
                .grade(selectedGrade)
                .dorm(dorm)
                .isEducate(isEducating)
                .password(password)
                .bankCard(bankCard)
                .contractNum(contractNumber)
                .phoneNumber(phoneNumber)
                .build();
        // Print national code and password
        System.out.println("****************************************");
        System.out.println("Your Username and Password is :");
        System.out.println("Username : " + nationalCode);
        System.out.println("Password: " + password);
        System.out.println("Please keep it secure and dont share with any one !");
        System.out.println("****************************************");
        //Saving logic
        try {
            // Save student information
            studentService.saveOrUpdate(student1);
            Thread.sleep(1000);
            // Associate bank card with student and save
            bankCard.setStudent(student1);
            bankCardService.saveOrUpdate(bankCard);
            Thread.sleep(1000);
            // Update partner information if married
            if (isMarried) {
                partner.setMarried(true);
                partner.setPartner(student1);
                studentService.saveOrUpdate(partner);
            }
        } catch (DataException e) {
            // Handle database access errors
            e.getMessage();
            throw new ServiceException("Unable to save student information.", e);
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
            throw new ServiceException("An unexpected error occurred while saving data.", e);
        }
    }


    //------------------------------------------METHOD-------------------------------------------------

    private String generatePassword() {
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


    private String getInput(Scanner scanner, String prompt) {
        System.out.println(prompt);
        String input = scanner.next();
        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty.");
        }
        return input;
    }

    private String getInput(Scanner scanner, String prompt, String regex) {
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

    private boolean getYesNoInput(Scanner scanner, String question) {
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


        Bank selectedBank = null;

        while (selectedBank == null) {
            System.out.println("Please enter a bank name (MELLAT, TEJARAT, or BLUEBANK):");
            String bankNameInput = scanner.next().toUpperCase();
            try {
                selectedBank = Bank.valueOf(bankNameInput);
                System.out.println("You have selected: " + selectedBank);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid bank name. Please enter one of the valid options.");
            }
            bankCard.setBank(selectedBank);
        }


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




