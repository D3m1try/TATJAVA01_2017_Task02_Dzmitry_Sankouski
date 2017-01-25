package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dzmitry_Sankouski on 20-Jan-17.
 */
public class RentUnit implements Serializable {
    private Map<String,ArrayList<SportEquipment>> 	accountBook = new HashMap<String,ArrayList<SportEquipment>>();
    private ArrayList<SportEquipment> units = new ArrayList<>();
    private String clientName;
    public static final String FILE_NAME = "RentUnit.obj";
    //private static RentUnit shop;

    public void checkInItem(SportEquipment equipment,String user){
        if (!accountBook.containsKey(user)){
            accountBook.put(user,new ArrayList());
        }

        accountBook.get(user).add(equipment);

    }

    public boolean CheckOutItem(SportEquipment equipment,String user) {
        if (accountBook.get(user) == null){
            return false;
        } if (accountBook.get(user).indexOf(equipment) == -1){
            return false;
        }

        accountBook.get(user).remove(equipment);
        if(accountBook.get(user).size() == 0){
            accountBook.remove(user);
        }
        return true;
    }

    public String report(){
        String result = new String();
        for (Map.Entry<String,ArrayList<SportEquipment>> entry : accountBook.entrySet()
                ) {
            result += "Customer -- " + entry.getKey() + " items:\n";
            for (SportEquipment equipment:entry.getValue()
                    ) {
                result += equipment.getTitle() + "\n";
            }
        }
        return result;
    }

    public SportEquipment findItem(String userName,String title){
        for (SportEquipment equipment:accountBook.get(userName)
             ) {
            if (equipment.getTitle().equalsIgnoreCase(title)){
                return equipment;
            }
        }
        return null;
    }

    //----------------------------------------getters & setters
    public ArrayList<SportEquipment> getEquipment(String user) {
        return accountBook.get(user);
    }

    public ArrayList<SportEquipment> getEquipment() {
        ArrayList<SportEquipment> units = new ArrayList<>();
        for (Map.Entry<String,ArrayList<SportEquipment>> entry: accountBook.entrySet()
             ) {
            units.addAll(entry.getValue());
        }
        return units;
    }

    public void setUnits(ArrayList<SportEquipment> units) {
        this.units = units;
    }

    public String getClientName() {
        return clientName;
    }


    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

}
