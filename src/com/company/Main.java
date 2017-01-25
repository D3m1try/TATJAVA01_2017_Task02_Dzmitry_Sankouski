package com.company;

import java.io.*;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException { // TODO handle exceptions {
        String userInput = "";
        String[] command;
        BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
        Pattern p = Pattern.compile("\\s+");
        Administrator administrator = new Administrator();

        while (true) {
            try {
                userInput = user.readLine();
            } catch (IOException e) {
                System.out.println("IO Exception.");
            }

            command = p.split(userInput,2); // parsing into command and arguments & executin the command
            if (command.length == 1){
                administrator.doCommand(command[0],new String[1]);
            } else {
                administrator.doCommand(command[0], p.split(command[1]));
            }

            //command execution results
            if ((administrator.getStatus() & administrator.ERROR) != 0) {
                System.out.print("Error:");

            }

            if ((administrator.getStatus()& administrator.EXIT) != 0) {
                break;
            }

            if ((administrator.getStatus()& administrator.UNKNOWN_COMMAND ) != 0){
                System.out.println("Unknown command, type help");
            }
            System.out.println(administrator.getStatusMessage());
        }

    }
}
