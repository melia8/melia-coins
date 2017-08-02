package com.melia.coins;

import com.melia.coins.exceptions.VendingException;
import com.melia.coins.model.Coin;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean quit = false;
        Collection<Coin> coins = null;
        Scanner scanner = new Scanner(System.in);
        VendingMachine vendingMachine = new VendingMachine();

        while (!quit) {
            System.out.println("Input Value , ( or 0 to quit)");
            int v = scanner.nextInt();
            if (v == 0) {
                quit = true;
                continue;
            }
            System.out.println("Input u for unlimited coins or l for limited ");
            String s = scanner.next();

            try {

                if (s.equals("l")) {
                    coins = vendingMachine.getChangeFor(v);
                } else if (s.equals("u")) {
                    coins = vendingMachine.getOptimalChangeFor(v);
                } else {
                    System.out.println("Not recognised input");
                    continue;
                }

            } catch (IOException e) {
                e.printStackTrace();
                continue;
            } catch (VendingException e) {
                System.out.println("Not enough coins for your change!");
                continue;
            }

            coins.stream()
                    .map(c -> c.getDenomination())
                    .forEach(c -> System.out.print(c + " "));
            System.out.println();

        }
    }
}
