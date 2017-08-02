package com.melia.coins;

import com.melia.coins.exceptions.VendingException;
import com.melia.coins.model.Coin;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;


public class VendingMachineTestPartTwo {
    VendingMachine vendingMachine;

    private Map<Integer, Integer> getDefaultInvMap() {
        Map<Integer, Integer> initialisedMap = new LinkedHashMap<>();
        initialisedMap.put(100, 11);
        initialisedMap.put(50, 24);
        initialisedMap.put(20, 0);
        initialisedMap.put(10, 99);
        initialisedMap.put(5, 200);
        initialisedMap.put(2, 11);
        initialisedMap.put(1, 23);
        return initialisedMap;
    }

    // Setup
    @Before
    public void createNewVendingMachineInstance() throws IOException {
        this.vendingMachine = new VendingMachine();
        //set the properties file to the default values
        EuroProperties euroProperties = new EuroProperties(VendingMachine.COIN_FILENAME);
        euroProperties.setInventory(getDefaultInvMap());
    }

    // Tests
    @Test
    public void should_Have_valid_instance_of_vending_machine(){
        assertThat(vendingMachine).isNotNull();
    }

    @Test
    public void should_get_a_map_of_the_properties_file() throws IOException {
        EuroProperties euroProperties = new EuroProperties(VendingMachine.COIN_FILENAME);
        Map<Integer,Integer> euroPropertyMap = euroProperties.getInventory();
        assertThat(euroPropertyMap).contains(entry(100,11),entry(50,24),entry(20,0),
                entry(10,99),entry(5,200),entry(2,11),entry(1,23));
    }

    @Test
    public void should_return_empty_collection_for_value_of_0() throws IOException, VendingException {
        Collection<Coin> coins = vendingMachine.getChangeFor(0);
        assertThat(coins.size()).isEqualTo(0);
    }

    @Test
    public void should_return_empty_collection_for_negative_value() throws IOException, VendingException {
        Collection<Coin> coins = vendingMachine.getChangeFor(-27);
        assertThat(coins.size()).isEqualTo(0);
    }

    @Test
    public void should_return_2x10cents_for_value_of_20() throws IOException, VendingException {
        Collection<Coin> coins = vendingMachine.getChangeFor(20);
        assertThat(coins).extractingResultOf("getDenomination").containsExactly(10,10);
    }

    @Test
    public void should_return_error_if_not_enough_coins_to_fulfil_request() throws IOException {
        //set up properties file with no inventory
        Map<Integer, Integer> defaultInvMap = getDefaultInvMap();
        for (Map.Entry<Integer,Integer> entry: defaultInvMap.entrySet()){
            entry.setValue(0);
        }
        EuroProperties euroProperties = new EuroProperties(VendingMachine.COIN_FILENAME);
        euroProperties.setInventory(defaultInvMap);
        try {
            vendingMachine.getChangeFor(20);
        } catch (Exception e){
            assertThat(e).isInstanceOf(VendingException.class);
        }

    }

    @Test
    public void should_reduce_the_amounts_in_the_inventory_properties_file() throws IOException, VendingException {
        Collection<Coin> coins = vendingMachine.getChangeFor(1200);
        EuroProperties euroProperties = new EuroProperties(VendingMachine.COIN_FILENAME);
        Map<Integer,Integer> euroPropertyMap = euroProperties.getInventory();
        assertThat(euroPropertyMap).contains(entry(100,0),entry(50,22),entry(20,0),
                entry(10,99),entry(5,200),entry(2,11),entry(1,23));
    }

}
