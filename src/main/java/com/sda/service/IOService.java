package com.sda.service;

import com.sda.model.DebitAccount;
import com.sda.model.UserAccount;

import java.util.List;
import java.util.Scanner;

public class IOService {

    private Scanner scanner = new Scanner(System.in);
    public void displayUnauthenticatedMenu(){

        System.out.println("Welcome,guest: Please select one of the following options:");
        System.out.println("1 Register");
        System.out.println("2 Login");
        System.out.println("Your answer: ");

    }

    public void displayAuthenticatedMenu(String firstName){

        System.out.println("Welcome, "+firstName+" Please select one of the following options:");
        System.out.println("1 -View portofolio and balance");
        System.out.println("2 -Transfer money");
        System.out.println("3 -Deposit cash at an ATM");
        System.out.println("4 -Create debit account");
        System.out.println("2 -Create credit account");
        System.out.println("x- Logout");
        System.out.println("Your answer: ");
    }

    public String getUserInput() {
        String userInput = scanner.next();
        return userInput;
    }

    public void displayInvalidInputMessage(){
        System.out.println("Invalid input");
    }


    public String getField(String field) {
        System.out.println("Please insert " +field+" :");
        String userInput= scanner.next();
        return userInput;
    }

    public void displayUserNameNotUniqueError() {
        System.out.println("Sorry , the name is already taken");
    }

    public void displayError(String message) {
        System.out.println("There is an error: "+ message);
    }

    public void displayInfo(String message) {
        System.out.println(message);
    }

    public void displayAccountInfo(List<DebitAccount> debitAccountList){
        for(int index=0;index<debitAccountList.size();index++){
            DebitAccount currentDebitAccount = debitAccountList.get(index);
            System.out.println((index+1)+" - "+currentDebitAccount.getFriendlyName()+" "
                    + currentDebitAccount.getCurrency()+" "
                    + currentDebitAccount.getAmount()+" "
                    + currentDebitAccount.getIban());
        }
    }

    public void displayGeneralView(UserAccount userAccount) {
        for(int index=0; index<userAccount.getDebitAccountList().size();index++){

        }
        System.out.println(userAccount.getFirstName()+ ", you have a total of "+userAccount.getDebitAccountList().size()+" bank accounts");
    }

}
