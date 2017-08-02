package com.melia.coins;

import com.melia.coins.exceptions.VendingException;
import com.melia.coins.model.Coin;

import java.io.IOException;
import java.util.Collection;

public class Main {

    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine();
        try {
            Collection<Coin> coins = vendingMachine.getChangeFor(1110);

            coins.stream()
                    .map(c -> c.getDenomination())
                    .forEach(System.out::println);

        } catch (VendingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
