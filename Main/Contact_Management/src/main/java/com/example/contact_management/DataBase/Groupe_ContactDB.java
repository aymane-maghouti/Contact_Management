package com.example.contact_management.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Groupe_ContactDB {
    public Groupe_ContactDB(){}

    public static void insertIntogroupe_contact(int id_contact) throws DataBaseException {
        try {
            Connection conn = DataBaseConnection.getConnection();
            PreparedStatement insert_statement = conn.prepareStatement("insert into groupe_contact(id_contact) values (?) ");
            insert_statement.setInt(1, id_contact);
            insert_statement.executeUpdate();
        } catch (Exception e) {
            throw new DataBaseException();
        }
    }

    public static void deletefromgroupe_contact(int id_contact) throws DataBaseException {
        try {
            Connection conn = DataBaseConnection.getConnection();
            PreparedStatement insert_statement = conn.prepareStatement("delete from groupe_contact where id_contact = ? ");
            insert_statement.setInt(1, id_contact);
            insert_statement.executeUpdate();
        } catch (Exception e) {
            throw new DataBaseException();
        }
    }

    public static void deletegroupefromgroupe_contact(int id_groupe) throws DataBaseException {
        try {
            Connection conn = DataBaseConnection.getConnection();
            PreparedStatement update_statement = conn.prepareStatement("update groupe_contact set id_groupe = null where id_groupe = ?");
            update_statement.setInt(1, id_groupe);
            update_statement.executeUpdate();
        } catch (Exception e) {
            throw new DataBaseException();
        }
    }


}
