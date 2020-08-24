package com.sda.service;

import com.sda.dao.UserAccountDao;
import com.sda.model.UserAccount;

public class UserAccountValidatorService {

    private UserAccountDao userAccountDao;
    private IOService ioService;

    public UserAccountValidatorService(UserAccountDao userAccountDao, IOService ioService) {
        this.userAccountDao = userAccountDao;
        this.ioService= ioService;
    }


    public boolean validate(UserAccount userAccount) {
        //aici trebuie sa validam fiecare field in parte, verificam unicitatea numelui urmand ca restul sa fie tema optionala
        validateUsername(userAccount.getUsername());
        validateCnp(userAccount.getCnp());
        return  validateUsername(userAccount.getUsername()) && validateCnp(userAccount.getCnp());
    }

    private boolean validateUsername(String username){
        boolean userNameUnique = userAccountDao.isUserNameUnique(username);
        if(!userNameUnique){
            ioService.displayUserNameNotUniqueError();
        }
        return userNameUnique;

    }

    private boolean validateCnp(String cnp){
        //cnp este valid daca are 13 cifre si e format doar din numere
        if(cnp.length()!=13){
            ioService.displayError("Cnp should have 13 characters!");
            return false;
        }
        if(!cnp.matches("[0-9]+")){
            ioService.displayError("Cnp should have only digits!");
            return false;
        }
        return true;
    }
}
