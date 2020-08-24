package com.sda.util;

import java.util.Random;

public class BankingUtil {
    public static String generateIban() {
        String iban = "RO12INGB";
        Random random = new Random();
        for (int index=0; index< 15 ; index++) {
            int randomNo = random.nextInt(10);
            iban = iban + randomNo;
        }
        return iban;
    }
}
