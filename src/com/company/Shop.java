package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dzmitry_Sankouski on 20-Jan-17.
 */
public class Shop implements Serializable {
    public final String USAGE = "";
    private Map<SportEquipment, Integer> goods = new HashMap<>();



    public static final String FILE_NAME = "Shop.obj";
    private static Shop shop;

    private Shop() {
        shop = this;
    }


    public String report(){
        String result = new String();
        result += "Title -- Count\n";
        for (Map.Entry<SportEquipment,Integer> entry:goods.entrySet()
                ) {
            result += entry.getKey().getTitle() + "  " + entry.getValue() + "\n";
        }
        return result;
    }

    public void addItem(SportEquipment equipment,int count){
        goods.put(equipment, count);
    }

    public void removeItem(SportEquipment equipment){
        goods.remove(equipment);
    }

    public boolean checkOutItem(SportEquipment equipment){
        if (goods.get(equipment) == null){
            return false;
        }
        if (goods.get(equipment) == 1){
            goods.remove(equipment);
        } else {
            goods.replace(equipment, goods.get(equipment), goods.get(equipment) - 1);
        }
        return true;
    }

    public void checkInItem(SportEquipment equipment){
        goods.replace(equipment, goods.get(equipment), goods.get(equipment) + 1);
    }

    public SportEquipment findItem(String name){
        for (Map.Entry<SportEquipment, Integer> entry: goods.entrySet()) {
            if (entry.getKey().getTitle().equalsIgnoreCase(name)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Map<SportEquipment, Integer> findItems(String query){
        Map<SportEquipment, Integer> result = new HashMap<>();
        for (Map.Entry<SportEquipment, Integer> entry: goods.entrySet()) {
            if (entry.getKey().getTitle().equalsIgnoreCase(query)) {
                result.put(entry.getKey(),entry.getValue());
                continue;
            } if (entry.getKey().getCategory().getCategoryName().equalsIgnoreCase(query)){
                result.put(entry.getKey(),entry.getValue());
                continue;
            }
        }

        return result;
    }



    //---------------------------------------getters & setters


    public static Shop getInstance() {
        if (shop != null) {
            return shop;
        } else {
            return new Shop();
        }
    }

    public static void setShop(Shop shop) {
        Shop.shop = shop;
    }

    public Map<SportEquipment, Integer> getGoods() {
        return goods;
    }
}
