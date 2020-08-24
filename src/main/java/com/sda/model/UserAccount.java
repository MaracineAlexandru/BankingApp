package com.sda.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Data
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userAccountId;
    private String firstName;
    private String lastName;
    private String cnp;
    private String email;
    private String username;
    private String password;
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DebitAccount> debitAccountList= new ArrayList<>();

    public UserAccount(String firstName, String lastName, String cnp, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void  addDebitAccount(DebitAccount debitAccount){
        debitAccountList.add(debitAccount);
    }

    }
