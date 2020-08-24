package com.sda;

import com.sda.dao.DebitAccountDao;
import com.sda.dao.UserAccountDao;
import com.sda.model.DebitAccount;
import com.sda.model.UserAccount;
import com.sda.service.IOService;
import com.sda.service.UserAccountValidatorService;
import com.sda.util.BankingUtil;

import java.util.List;
import java.util.Optional;

public class BankingApp {

    private IOService ioService;
    private UserAccountDao userAccountDao;
    private UserAccountValidatorService userAccountValidatorService;
    private DebitAccountDao debitAccountDao;

    public BankingApp(){
        this.ioService = new IOService();
        this.userAccountDao= new UserAccountDao();
        this.userAccountValidatorService= new UserAccountValidatorService(userAccountDao,ioService);
        this.debitAccountDao= new DebitAccountDao();
    }
    public void start() {
        while (true) {
            ioService.displayUnauthenticatedMenu();

            String userInput = ioService.getUserInput();

            processUserInput(userInput);
        }
    }

    private void  authenticatedFlow(UserAccount userAccount){
        boolean shouldContinue= true;
        while (shouldContinue){
            ioService.displayAuthenticatedMenu(userAccount.getFirstName());
            String authenticatedUserInput= ioService.getUserInput();
            shouldContinue= processAuthenticatedUserInput(authenticatedUserInput,userAccount);

        }
    }

    private void processUserInput(String userInput) {
        switch (userInput) {
            case "1":
                register();
                break;
            case "2":
                Optional<UserAccount> optionalUserAccount = login();
                if (optionalUserAccount.isPresent()) {
                    UserAccount userAccount = optionalUserAccount.get();
                    authenticatedFlow(userAccount);
                }
                break;
            default:
                ioService.displayError("Eroare");
        }
    }


        private boolean processAuthenticatedUserInput (String authenticatedUserInput,UserAccount userAccount){
            switch (authenticatedUserInput) {
                case "x": {
                    return false;
                }
                case "1": {
                    ioService.displayGeneralView(userAccount);
                    break;
                }
                case "3":{
                    depositCash(userAccount);
                    break;
                }
                case "4":{
                    createDebitAccount(userAccount);
                    break;
                }

            }
            return true;
        }

    private void depositCash(UserAccount userAccount) {
        List<DebitAccount> debitAccountList = userAccount.getDebitAccountList();
        ioService.displayAccountInfo(debitAccountList);
        String accountFriendlyName= ioService.getField("friendly name");
        String amountOfCash = ioService.getField("amount of cash");

        for (int index=0; index<debitAccountList.size();index++) {
            DebitAccount currentAcount = debitAccountList.get(index);
            if(currentAcount.getFriendlyName().equals(accountFriendlyName)){
                boolean updateWasSuccesful= updateCashAmount(amountOfCash, currentAcount);
                if (!updateWasSuccesful) {
                    return;
                }
                debitAccountDao.updateEntity(currentAcount);
                ioService.displayInfo("Update succesful");
                return;
            }
        }
        ioService.displayError("Friendly name nu exista");

    }

    private boolean updateCashAmount(String amountOfCash, DebitAccount currentAcount) {
        int newAmount =currentAcount.getAmount();
        try {
            int amountOfCashAsInt = Integer.parseInt(amountOfCash);
            if(amountOfCashAsInt<0){
                ioService.displayError("Negative balance or 0 is not allowed");
                return false;
            }
            newAmount += amountOfCashAsInt;
        } catch (NumberFormatException e){
            ioService.displayError("Amount is not a number");
            return false;

        }
        currentAcount.setAmount(newAmount);
        return true;
    }

    private void createDebitAccount(UserAccount userAccount) {
        String friendlyName = ioService.getField("friendly name");
        String currency= ioService.getField("currency");

        DebitAccount debitAccount= new DebitAccount(currency,friendlyName);
        debitAccount.setIban(BankingUtil.generateIban());
        userAccount.addDebitAccount(debitAccount);
        debitAccount.setUserAccount(userAccount);
        debitAccountDao.saveEntity(debitAccount);
        ioService.displayInfo("Account has been created!");

    }




    private void register () {
            String userFirstName = ioService.getField("first name");
            String userLastName = ioService.getField("last name");
            String cnp = ioService.getField("cnp");
            String email = ioService.getField("email");
            String username = ioService.getField("username");
            String password = ioService.getField("password");

            UserAccount userAccount = new UserAccount(userFirstName, userLastName, cnp, email, username, password);
            if (userAccountValidatorService.validate(userAccount)) {
                userAccountDao.saveEntity(userAccount);
            }

        }

        private Optional<UserAccount> login () {
            String username = ioService.getField("username");
            String password = ioService.getField("password");
            Optional<UserAccount> optionalUserAccount = userAccountDao.findByUsername(username);
            if (optionalUserAccount.isPresent()) {
                UserAccount userAccount = optionalUserAccount.get();
                String passwordFromDatabase = userAccount.getPassword();
                if (passwordFromDatabase.equals(password)) {
                    ioService.displayInfo("Login succesful!");
                    //afisam meniul de authenticated
                    return optionalUserAccount;
                }
            }
            ioService.displayError("Username or password invalid");
            return Optional.empty();

        }

    }

