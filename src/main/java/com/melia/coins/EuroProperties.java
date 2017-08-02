package com.melia.coins;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class EuroProperties {

    private String propFilePath;

    public EuroProperties(String filename) {
        this.propFilePath = filename;
    }

    /**
     * Helper function to return an integer from property
     * @param props properties file
     * @param key key to return integer value for
     * @return
     */
    private int getIntProp(Properties props, String key){
        return Integer.parseInt(props.getProperty(key));
    }

    private File getPropFile(){
        return new File(this.getClass().getClassLoader().getResource(this.propFilePath).getFile());
    }

    /**
     *
     * @return An Insertion-Ordered, (Descending Value), Map with Coin value as key,
     * and number of each denomination as value, retrieved from a properties file
     * @throws IOException
     */
    public Map<Integer, Integer> getInventory() throws IOException {
        Map<Integer, Integer> invMap = new LinkedHashMap<>();
        Properties props = new Properties();

        File file = getPropFile();
       try(FileInputStream input = new FileInputStream(file)){
           props.load(input);
           invMap.put(100, getIntProp(props,"100"));
           invMap.put(50, getIntProp(props,"50"));
           invMap.put(20, getIntProp(props,"20"));
           invMap.put(10, getIntProp(props,"10"));
           invMap.put(5, getIntProp(props,"5"));
           invMap.put(2, getIntProp(props,"2"));
           invMap.put(1, getIntProp(props,"1"));
       }
        return invMap;
    }

    /**
     * Take a Map as input and write a new properties file using the map values as the
     * property key and value
     * @param inv
     * @throws IOException
     */
    public void setInventory(Map<Integer, Integer> inv) throws IOException {
        Properties props = new Properties();
        File file = getPropFile();
        try(FileOutputStream output = new FileOutputStream(file, false)){
            for (Map.Entry<Integer,Integer> entry : inv.entrySet()){
               props.setProperty(entry.getKey().toString(), entry.getValue().toString());
            }
            props.store(output,null);
        }
    }
}
