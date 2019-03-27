/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaredcover;

/**
 * @author jared
 */
import java.math.BigDecimal;
import java.util.Scanner;
import java.io.*;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;

public class LoanCalculator implements Serializable {

    ArrayList<Loan> loans = new ArrayList<Loan>();

    public void displayMenu() {
        System.out.println("--------------------------------------------\n"
                + "                   menu\n"
                + "--------------------------------------------\n"
                + "1. View Loans\n"
                + "\t Shows all loans.\n"
                + "2. Borrow More\n"
                + "\t Increase your total debt.\n"
                + "3. Make Payment\n"
                + "\t Decrease your total debt.\n"
                + "4. Take out a new loan.\n"
                + "\t Borrow a new sum from a specific lender.\n"
                + "5. Edit a loan.\n"
                + "\t Change the fields of a current loan.\n"
                + "6. Save and exit."
                + "\n"
                + "\n"
                + "Your total debt is: $" + displayTotalDebt() + "\n"
                + "Enter option number");
    }

    public void go() {
        loadLoans();
        updateLoans();
        displayMenu();
        Scanner scan = new Scanner(System.in);
        try {
            int optionNumber = scan.nextInt();
            switch (optionNumber) {
                case 1:
                    displayLoans();
                    scan.close();
                    break;
                case 2: ;
                    borrowMore();
                    scan.close();
                    break;
                case 3:
                    makePayment();
                    scan.close();
                    break;
                case 4:
                    addLoan();
                    scan.close();
                    break;
                case 5:
                    editLoan();
                    scan.close();
                case 6:
                    saveLoans();
                    System.exit(0);
                default:
                    System.out.println("Sorry, that's not an option. Please try again.");
                    scan.next();
                    go();
            }
        } catch (Exception ex) {
            System.out.println("Sorry, that's not an option. Please try again.");
            scan.close();
            go();
        }

    }

    public void displayLoans() {
        for (Loan loan : loans) {
            System.out.println("Name: " + loan.getName());
            System.out.println("Lender: " + loan.getLender());
            System.out.println("Principal: " + loan.getPrincipal());
            System.out.println("Amount Owed: " + loan.getAmountOwed());
            System.out.println("Interest Rate: " + loan.getInterestRate());
            System.out.println("Date Created: " + loan.getDateCreated().getTime());
            System.out.println("Number of Times Accrued: " + loan.getNumOfTimesAccrued());
            System.out.println("Day last Accrued: " + loan.getDayLastAccrued().getTime()
                    + "\n");
        }
        navOptions();
    }

    public void addLoan() {
        Loan newLoan = new Loan();

        Scanner scan = new Scanner(System.in);
        System.out.println("What would you like this loan to be called?");

        newLoan.setName(scan.nextLine());
        System.out.println("Fantastic! Your new loan will be called " + newLoan.getName() + ". Who is the Lender?");
        newLoan.setLender(scan.nextLine());
        System.out.println("Great, the Lender is " + newLoan.getLender() + ". How much money would you like to borrow?");
        newLoan.setPrincipal(new BigDecimal(scan.nextLine()));
        newLoan.setAmountOwed(newLoan.getPrincipal());
        System.out.println("Excellent... The principal for this loan will be " + newLoan.getPrincipal() + " dollars. What will be the APR of the loan?");
        newLoan.setInterestRate(new BigDecimal(scan.nextLine()));
        System.out.println("Splendid! The loans APR will be " + newLoan.getInterestRate() + ".");
        System.out.println("How often will the interest compound on this loan. Please enter Daily Monthly or Yearly");
        String compoundingAnswer = scan.nextLine();
        boolean needAnswer = true;
        do {
            switch (compoundingAnswer) {
                case "Daily":
                    newLoan.setCompoundsDaily(true);
                    needAnswer = false;
                    break;
                case "Monthly":
                    newLoan.setCompoundsMonthly(true);
                    needAnswer = false;
                    break;
                case "Yearly":
                    newLoan.setCompoundsYearly(true);
                    needAnswer = false;
                    break;
            }
        } while (needAnswer);
        if (newLoan.isCompoundsDaily()) {
            System.out.println("Thank you very much for taking out a loan called " + newLoan.getName() + " for an amount of " + newLoan.getPrincipal() + "\n" + "at a daily accrued compounded rate of "
                    + newLoan.getInterestRate() + ".");
            loans.add(newLoan);
        } else if (newLoan.isCompoundsMonthly()) {
            System.out.println("Thank you very much for taking out a loan called " + newLoan.getName() + " for an amount of " + newLoan.getPrincipal() + "\n" + "at a monthly compounded interest rate of "
                    + newLoan.getInterestRate() + ".");
            loans.add(newLoan);
        } else if (newLoan.isCompoundsYearly()) {
            System.out.println("Thank you very much for taking out a loan called " + newLoan.getName() + " for an amount of " + newLoan.getPrincipal() + "\n" + "at a yearly compunded interest rate of "
                    + newLoan.getInterestRate() + ".");
            loans.add(newLoan);
        }
        navOptions();

    }

    public void borrowMore() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Which loan would you like to add to?");
        for (Loan loan : loans) {
            System.out.println(loans.indexOf(loan) + ": " + loan.getName() + "Aamount owed: " + loan.getAmountOwed());

        }
        int optionNumber = scan.nextInt();
        scan.nextLine();
        for (Loan loan : loans) {
            if (optionNumber == loans.indexOf(loan)) {
                System.out.println("You have selected: " + loan.getName());
                System.out.println("You owe" + loan.getLender() + " " + loan.getAmountOwed());
                System.out.println("How much more would you like borrow?");
                BigDecimal borrowAmount = new BigDecimal(scan.nextLine());
                loan.setAmountOwed(loan.getAmountOwed().add(borrowAmount));
                System.out.println("Thank you, you have borrowed an additional " + borrowAmount + " dollars from " + loan.getLender() + "\n"
                        + "Your new amount owed is " + loan.getAmountOwed() + ".");
                navOptions();
            }
        }
    }

    public void makePayment() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Which loan would you like to pay down?");
        for (Loan loan : loans) {
            System.out.println(loans.indexOf(loan) + ": " + loan.getName() + "Aamount owed: " + loan.getAmountOwed());

        }
        int optionNumber = scan.nextInt();
        scan.nextLine();
        for (Loan loan : loans) {
            if (optionNumber == loans.indexOf(loan)) {
                System.out.println("You have selected: " + loan.getName());
                System.out.println("You owe" + loan.getLender() + " " + loan.getAmountOwed());
                System.out.println("How much would you like pay?");
                BigDecimal paymentAmount = new BigDecimal(scan.nextLine());
                loan.setAmountOwed(loan.getAmountOwed().subtract(paymentAmount));
                System.out.println("Thank you, your payment of " + paymentAmount + " has been recieved by " + loan.getLender() + " \n"
                        + "Your new amount owed is " + loan.getAmountOwed() + ".");
                navOptions();
            }
        }
    }

    public void editLoan() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the number of the loan you would like to edit.");
        for (Loan loan : loans) {
            System.out.println(loans.indexOf(loan) + ": " + loan.getName());
        }
        int optionNumber = scan.nextInt();
        scan.nextLine();
        for (Loan loan : loans) {
            if (optionNumber == loans.indexOf(loan)) {
                System.out.println("Name: " + loan.getName());
                System.out.println("Lender: " + loan.getLender());
                System.out.println("Principal: " + loan.getPrincipal());
                System.out.println("Amount Owed" + loan.getAmountOwed());
                System.out.println("Interest Rate: " + loan.getInterestRate());
                System.out.println("Date Created " + loan.getDateCreated().getTime());
                System.out.println("Which field would you like to change? \n"
                        + "Enter: Name, Lender, Principal, Amount Owed, Interest Rate, Date Created, Day Last Accrued, Compound Frequency, Delete");

                String optionString = scan.nextLine();
                switch (optionString) {
                    case "Name":
                        System.out.println("What is the new name of this loan?");
                        String newName = scan.nextLine();
                        loan.setName(newName);
                        System.out.println("Thank you. The new Name is " + loan.getName());
                        editLoanSwitch();
                        break;
                    case "Lender":
                        System.out.println("Who is the new Lender?");
                        String newLender = scan.nextLine();
                        loan.setLender(newLender);
                        System.out.println("Thank you. The new Lender is " + loan.getLender());
                        editLoanSwitch();
                        break;
                    case "Principal":
                        System.out.println("What is the new Principal amount?");
                        BigDecimal newPrincipal = scan.nextBigDecimal();
                        loan.setPrincipal(newPrincipal);
                        System.out.println("Thank you. The new Principal Amount is " + loan.getPrincipal());
                        editLoanSwitch();
                        break;
                    case "Amount Owed":
                        System.out.println("What is the new Amount Owed?");
                        BigDecimal newAmountOwed = scan.nextBigDecimal();
                        loan.setAmountOwed(newAmountOwed);
                        System.out.println("Thank you. The new Amount Owed is " + loan.getAmountOwed());
                        editLoanSwitch();
                        break;
                    case "Interest Rate":
                        System.out.println("What is the new Interest Rate?");
                        BigDecimal newInterestRate = scan.nextBigDecimal();
                        loan.setInterestRate(newInterestRate);
                        System.out.println("Thank you. The new Interest Rate is " + loan.getInterestRate());
                        editLoanSwitch();
                        break;
                    case "Date Created":
                        Calendar newDateCreated = Calendar.getInstance();
                        int month,
                         day,
                         year;
                        System.out.println("Please enter a month MM: ");
                        month = scan.nextInt();
                        System.out.println("Please enter a day DD: ");
                        day = scan.nextInt();
                        System.out.println("Please enter a year YYYY: ");
                        year = scan.nextInt();
                        System.out.println("You chose: " + month + " " + day + " " + year);
                        month = month - 1;
                        newDateCreated.set(year, month, day);
                        loan.setDateCreated(newDateCreated);
                        editLoanSwitch();
                        break;
                    case "Day Last Accrued":
                        Calendar newDayLastAccrued = Calendar.getInstance();
                        int DayLastAccruedMonth,
                         DayLastAccruedDay,
                         DayLastAccruedYear;
                        System.out.println("Please enter a month MM: ");
                        DayLastAccruedMonth = scan.nextInt();
                        System.out.println("Please enter a day DD: ");
                        DayLastAccruedDay = scan.nextInt();
                        System.out.println("Please enter a year YYYY: ");
                        DayLastAccruedYear = scan.nextInt();
                        System.out.println("You chose: " + DayLastAccruedMonth + " " + DayLastAccruedDay + " " + DayLastAccruedYear);
                        DayLastAccruedMonth = DayLastAccruedMonth - 1;
                        newDayLastAccrued.set(DayLastAccruedYear, DayLastAccruedMonth, DayLastAccruedDay);
                        loan.setDayLastAccrued(newDayLastAccrued);
                        editLoanSwitch();
                        break;
                    case "Compound Frequency":
                        System.out.println("How often will the loan compound");
                        String compoundingAnswer = scan.nextLine();
                        boolean needAnswer = true;
                        do {
                            switch (compoundingAnswer) {
                                case "Daily":
                                    loan.setCompoundsDaily(true);
                                    needAnswer = false;
                                    break;
                                case "Monthly":
                                    loan.setCompoundsMonthly(true);
                                    needAnswer = false;
                                    break;
                                case "Yearly":
                                    loan.setCompoundsYearly(true);
                                    needAnswer = false;
                                    break;
                                
                                default:
                                    System.out.println("Please enter Daily Yearly or monthly.");
                            }
                        } while (needAnswer);
                        editLoanSwitch();
                    case "Delete":
                        System.out.println(loan.getName() + " has been removed. If you didn't want to delete it, exit without saveing.");
                        loans.remove(loan);
                        editLoanSwitch();
                    default:
                        System.out.println("Sorry, command not recognized. Please try again.");
                        editLoan();
                }
                scan.close();
            }
        }
        navOptions();
    }

    public void editLoanSwitch() {
        try (Scanner scan = new Scanner(System.in)) {
            System.out.println("Would you like to make another change? (y/n)");
            String answer = scan.nextLine();
            switch (answer) {
                case "y":
                    editLoan();
                case "n":
                    navOptions();
                default:
                    System.out.println("Sorry does not compute please enter y or n.");
                    editLoanSwitch();
            }
        }
    }

    public BigDecimal displayTotalDebt() {
        BigDecimal totalDebt = new BigDecimal(0);
        for (Loan loan : loans) {
            totalDebt = totalDebt.add(loan.getAmountOwed());
        }
        return totalDebt.setScale(2, RoundingMode.UP);
    }

    public void updateLoans() {
        for (Loan loan : loans) {
            loan.updateLoan();
        }
    }

    public void navOptions() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Menu to return to the main menu or Exit to save and exit the program.");
        String navOptions = scan.nextLine();
        switch (navOptions) {
            case "Menu":
                saveLoans();
                go();
                break;
            case "Exit":
                saveLoans();
                System.exit(0);
                break;
            default:
                System.out.println("Sorry, unrecognized command. Please try again");
                navOptions();

        }
        scan.close();
    }

    public void saveLoans() {
        String filename = "loans.ser";
        try (ObjectOutputStream out
                = new ObjectOutputStream(new FileOutputStream(filename))) {

            out.writeObject(loans);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadLoans() {
        String filename = "loans.ser";
        try (ObjectInputStream in
                = new ObjectInputStream(new FileInputStream(filename))) {

            loans = (ArrayList) in.readObject();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
