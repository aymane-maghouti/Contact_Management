package com.example.contact_management.IHM;

import com.example.contact_management.DataBase.AppInstaller;
import com.example.contact_management.DataBase.DataBaseException;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws Exception {
        try {
            if (AppInstaller.checkIfAlreadyInstalled()) {
                System.out.println("la base de donnees est deja exsite");
            } else {
                AppInstaller.run();
                System.out.println("la base de donnes a ete bien cree");
            }
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
    }

}
