package com.sda.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class DebitAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int debitAccountId;

    private String iban;
    private String currency;
    private String friendlyName;
    private int amount=0;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private UserAccount userAccount;

    public DebitAccount(String currency, String friendlyName) {
        this.currency = currency;
        this.friendlyName = friendlyName;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
