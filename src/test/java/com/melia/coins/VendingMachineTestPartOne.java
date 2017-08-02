package com.melia.coins;

import com.melia.coins.exceptions.VendingException;
import com.melia.coins.model.Coin;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


public class VendingMachineTestPartOne {
    VendingMachine vendingMachine;


    // Setup
    @Before
    public void createNewVendingMachineInstance(){
        this.vendingMachine = new VendingMachine();
    }

    // Tests
    @Test
    public void should_Have_valid_instance_of_vending_machine(){
        assertThat(vendingMachine).isNotNull();
    }

    @Test
    public void should_return_empty_collection_for_zero_value() throws VendingException {
        Collection<Coin> coins = vendingMachine.getOptimalChangeFor(0);
        assertThat(coins.size()).isEqualTo(0);

    }

    @Test
    public void should_return_empty_collection_for_negative_value() throws VendingException {
        Collection<Coin> coins = vendingMachine.getOptimalChangeFor(-1000);
        assertThat(coins.size()).isEqualTo(0);
    }

    @Test
    public void should_return_1cent_for_value_of_1() throws VendingException {
        Collection<Coin> coins = vendingMachine.getOptimalChangeFor(1);
        assertThat(coins.size()).isEqualTo(1);
        assertThat(coins).extractingResultOf("getDenomination").containsExactly(1);
    }

    @Test
    public void should_return_2cent_for_value_of_2() throws VendingException {
        Collection<Coin> coins = vendingMachine.getOptimalChangeFor(2);
        assertThat(coins.size()).isEqualTo(1);
        assertThat(coins).extractingResultOf("getDenomination").containsExactly(2);
    }


    @Test
    public void should_return_2cents_and_1cent_for_val_of_3() throws VendingException {
        Collection<Coin> coins = vendingMachine.getOptimalChangeFor(3);
        assertThat(coins.size()).isEqualTo(2);
        assertThat(coins).extractingResultOf("getDenomination").containsExactly(2,1);
    }

    @Test
    public void should_return_2cent_and_2cent_for_val_of_4() throws VendingException {
        Collection<Coin> coins = vendingMachine.getOptimalChangeFor(4);
        assertThat(coins.size()).isEqualTo(2);
        assertThat(coins).extractingResultOf("getDenomination").containsExactly(2,2);
    }

    @Test
    public void should_return_5cent_for_val_of_5() throws VendingException {
        Collection<Coin> coins = vendingMachine.getOptimalChangeFor(5);
        assertThat(coins.size()).isEqualTo(1);
        assertThat(coins).extractingResultOf("getDenomination").containsExactly(5);
    }

    @Test
    public void should_return_5cent_and_1cent_for_val_of_6() throws VendingException {
        Collection<Coin> coins = vendingMachine.getOptimalChangeFor(6);
        assertThat(coins.size()).isEqualTo(2);
        assertThat(coins).extractingResultOf("getDenomination").containsExactly(5,1);
    }

    @Test
    public void should_return_5cents_and_2x2cents_for_val_of_nine() throws VendingException {
        Collection<Coin> coins = vendingMachine.getOptimalChangeFor(9);
        assertThat(coins.size()).isEqualTo(3);
        assertThat(coins).extractingResultOf("getDenomination").containsExactly(5,2,2);
    }

    @Test
    public void should_return_10cents_and_1cent_for_val_of_11() throws VendingException {
        Collection<Coin> coins = vendingMachine.getOptimalChangeFor(11);
        assertThat(coins.size()).isEqualTo(2);
        assertThat(coins).extractingResultOf("getDenomination").containsExactly(10,1);
    }

    @Test
    public void should_return_20cents_5cents_2cents_and_2cents_for_val_of_29() throws VendingException {
        Collection<Coin> coins = vendingMachine.getOptimalChangeFor(29);
        assertThat(coins.size()).isEqualTo(4);
        assertThat(coins).extractingResultOf("getDenomination").containsExactly(20,5,2,2);
    }

    @Test
    public void should_return_50cents_20cents_5cents_2cents_and_1cent_for_val_of_78() throws VendingException {
        Collection<Coin> coins = vendingMachine.getOptimalChangeFor(78);
        assertThat(coins.size()).isEqualTo(5);
        assertThat(coins).extractingResultOf("getDenomination").containsExactly(50,20,5,2,1);
    }

    @Test
    public void should_return_2x100cents_50cents_20cents_5cents_2cents_and_1cent_for_val_of_278() throws VendingException {
        Collection<Coin> coins = vendingMachine.getOptimalChangeFor(278);
        assertThat(coins.size()).isEqualTo(7);
        assertThat(coins).extractingResultOf("getDenomination").containsExactly(100,100,50,20,5,2,1);
    }
}
