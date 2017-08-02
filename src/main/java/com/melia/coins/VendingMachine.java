package com.melia.coins;

import com.melia.coins.exceptions.VendingException;
import com.melia.coins.model.Coin;

import java.io.IOException;
import java.util.*;

public class VendingMachine {

    public static final String COIN_FILENAME = "coin-inventory.properties";

    /**
     *
     * @return An Insertion-Ordered, (Descending Value), Map with Coin value as key
     * and unlimited, (kind of..), number of coins of each denomination
     */
    private Map<Integer, Integer> getInitialisedInvMap() {
        Map<Integer, Integer> initialisedMap = new LinkedHashMap<>();
        initialisedMap.put(100, Integer.MAX_VALUE);
        initialisedMap.put(50, Integer.MAX_VALUE);
        initialisedMap.put(20, Integer.MAX_VALUE);
        initialisedMap.put(10, Integer.MAX_VALUE);
        initialisedMap.put(5, Integer.MAX_VALUE);
        initialisedMap.put(2, Integer.MAX_VALUE);
        initialisedMap.put(1, Integer.MAX_VALUE);

        return initialisedMap;
    }

    /**
     *
     * @param coinInv Insertion-ordered,(Descending Value), Map containing coin value as key and
     *                number of coins remaining as value
     * @param cents   value to return coin change for
     * @return Collection of Coin Objects representing change
     * @throws VendingException
     */
    private Collection<Coin> getCoinList(Map<Integer,Integer> coinInv, int cents) throws VendingException {
        List<Coin> coins = new ArrayList();

        for (Map.Entry<Integer,Integer> entry : coinInv.entrySet()) {
            while (cents >= entry.getKey() && entry.getValue()>0) {
                coins.add(new Coin(entry.getKey()));
                cents -= entry.getKey();
                entry.setValue(entry.getValue()-1);
            }
        }

        if (cents > 0){
            throw new VendingException("Not Enough Coins!");
        }

        return coins;
    }

    /**
     *
     * @param cents value to return change for
     * @return Collection of Coins for change when unlimited coins available
     * @throws VendingException
     */
    public Collection<Coin> getOptimalChangeFor(int cents) throws VendingException {
        return getCoinList(getInitialisedInvMap(), cents);
    }

    /**
     *
     * @param cents value to return change for
     * @return  Collection of Coins for change when number of coins available for each
     * denomination are specified in a properties file
     * @throws IOException
     * @throws VendingException
     */
    public Collection<Coin> getChangeFor(int cents) throws IOException, VendingException {
        EuroProperties euroProperties = new EuroProperties(COIN_FILENAME);
        // Read properties from file into Map
        Map<Integer,Integer> coinInv = euroProperties.getInventory();
        Collection<Coin> coins = getCoinList(coinInv, cents);
        // write updated Map as the contents of the inventory file
        euroProperties.setInventory(coinInv);
        return coins;
    }
}


