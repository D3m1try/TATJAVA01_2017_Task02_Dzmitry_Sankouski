package com.company;

import java.io.*;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Dzmitry_Sankouski on 20-Jan-17.
 */
public class Administrator extends CommandProcessor {
    public final String USAGE = "";
    public Stack<Object> modifiedObjects;
    private RentUnit rentUnit;
    public int EXIT = USER_BIT1;
    public static final String SHOP_FILE_NAME = "Shop.obj";


    public Administrator()  {
        FileInputStream fis = null;
        ObjectInputStream oin = null;

        try {
            fis = new FileInputStream(new File(SHOP_FILE_NAME));
        } catch (FileNotFoundException e) {
            System.out.println("no savings found");
            rentUnit = new RentUnit();
            return;
        }
        try {
            oin = new ObjectInputStream(fis);
            Shop.setShop((Shop) oin.readObject());
        } catch (IOException e) {
            System.out.println("IO Exception while reading " + SHOP_FILE_NAME);
        } catch (ClassNotFoundException e) {
            System.out.println("Class \"Shop\" not found.");
        }


        try {
            fis = new FileInputStream(new File(RentUnit.FILE_NAME));
            oin = new ObjectInputStream(fis);
            rentUnit = (RentUnit) oin.readObject();
        } catch (IOException e) {
            System.out.println("IO Exception while reading " + RentUnit.FILE_NAME + ". Rent data might be not loaded.");
        } catch (ClassNotFoundException e) {
            System.out.println("Class \"Rent unit\" not found.");
        }
    }

    private void save(String[] args) {

        FileOutputStream fos;
        ObjectOutputStream oos;

        // saving shop
        try {
            fos = new FileOutputStream(new File(Shop.FILE_NAME));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(Shop.getInstance());
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println(Shop.FILE_NAME + " not found");
        } catch (IOException e) {
            System.out.println("IO Exception while saving" + Shop.FILE_NAME);
        }

        // saving rentunit

        try {
            fos = new FileOutputStream(new File(RentUnit.FILE_NAME));
        oos = new ObjectOutputStream(fos);
        oos.writeObject(rentUnit);
        oos.flush();
        oos.close();
        } catch (FileNotFoundException e) {
            System.out.println(RentUnit.FILE_NAME + " not found");
        } catch (IOException e) {
            System.out.println("IO Exception while saving" + RentUnit.FILE_NAME);
        }

    }

    private void addItem(String[] args) {
        final String USAGE = "addItem [Category name] [item title] [price] [count]";
        int count = 0;
        int price = 0;
        if (args.length != 4) {
            this.setStatusMessage(USAGE);               // sevice code to communicate with user
            this.setStatus(this.getStatus() | ERROR);
            return;                                        //end of service code
        }
        Category category = new Category(args[0]);
        String title = args[1];

        try {
            price = Integer.valueOf(args[2]);
            count = Integer.valueOf(args[3]);
        } catch (NumberFormatException e) {
            this.setStatusMessage(USAGE);
            this.setStatus(this.getStatus() | ERROR);
            return;
        }

        Shop.getInstance().addItem(new SportEquipment(category, title, price), count);
    }

    private void removeItem(String[] args) {
        final String USAGE = "removeItem [item title]";
        if (args.length != 1) {
            this.setStatusMessage(USAGE);
            this.setStatus(this.getStatus() | ERROR);
            return;
        }
        String title = args[0];

        Shop.getInstance().removeItem(Shop.getInstance().findItem(title));
    }

    private void item(String[] args) {
        final String USAGE = "item [action] [item title] [user name]\naction take --> moving item from shop to user\n" +
                "action return --> moving item from user to shop";
        String userName = "";
        String action = "";
        SportEquipment equipment;

        if (args == null) {
            this.setStatusMessage(USAGE);
            this.setStatus(this.getStatus() | ERROR);
            return;
        }
        if (args.length != 3) {
            this.setStatusMessage(USAGE);
            this.setStatus(this.getStatus() | ERROR);
            return;
        }
        userName = args[2];
        action = args[0];

        if (!action.equalsIgnoreCase("take") && !(action.equalsIgnoreCase("return"))) {
            this.setStatusMessage("unknown action " + action);
            this.setStatus(this.getStatus() | ERROR);
            return;
        }


        if (action.equalsIgnoreCase("take")) {
            if ((rentUnit.getEquipment(userName) != null) && (rentUnit.getEquipment(userName).size() >= 3)) {
                this.setStatus(this.getStatus() | ERROR);
                this.setStatusMessage("This user has 3 items already");
                return;
            }
            equipment = Shop.getInstance().findItem(args[1]);
            if (equipment == null){
                this.setStatus(this.getStatus() | ERROR);
                this.setStatusMessage("No such equipment");
                return;
            }

            if (Shop.getInstance().checkOutItem(equipment)) {
                rentUnit.checkInItem(equipment, userName);
            } else {

            }
        }

        if (action.equalsIgnoreCase("return")) {
            equipment = rentUnit.findItem(userName,args[1]);
            if (equipment == null){
                this.setStatus(this.getStatus() | ERROR);
                this.setStatusMessage("No such equipment");
                return;
            }
            if (rentUnit.CheckOutItem(equipment, userName)) {
                Shop.getInstance().checkInItem(equipment);
            }
        }
    }

    private void findItem(String[] args) {
        String searchResult = "Item title --- count\n";
        int counter = 0;
        final String USAGE = "findItem [item title]";
        if (args.length != 1) {
            this.setStatusMessage(USAGE);
            this.setStatus(this.getStatus() | ERROR);
            return;
        }

        for (Map.Entry<SportEquipment, Integer> entry:Shop.getInstance().findItems(args[0]).entrySet()
             ) {
            searchResult += entry.getKey().getTitle() + "  ---  " + entry.getValue() + "\n";
                    counter +=entry.getValue();
        }
        searchResult += "Total items count = " + counter;

        this.setStatusMessage(searchResult);
    }

    private void findCategory(String[] args) {

    }

    private void report(String[] args) {
        final String USAGE = "report [shop or users]";
        Map<SportEquipment, Integer> goods = Shop.getInstance().getGoods();
        if (args.length != 1) {
            this.setStatusMessage(USAGE);
            this.setStatus(this.getStatus() | ERROR);
            return;
        }

        if (args[0].equalsIgnoreCase("shop")) {
            System.out.println(Shop.getInstance().report());
        }

        if (args[0].equalsIgnoreCase("users")) {
            System.out.println(rentUnit.report());
        }
    }


    private void exit(String[] args) {
        setStatus(getStatus() | EXIT);
    }

    private void quit(String[] args) {
        exit(null);
    }


}
