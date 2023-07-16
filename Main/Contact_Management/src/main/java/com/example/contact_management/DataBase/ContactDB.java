package com.example.contact_management.DataBase;

import com.example.contact_management.BO.Contact;
import com.example.contact_management.BO.Groupe;
import org.apache.log4j.Logger;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContactDB {
    private static final Logger LOGGER = Logger.getLogger(Contact.class);

    public ContactDB() {
    }

    public static boolean phoneticSearchC(String nom, String prenom) throws DataBaseException {
        try {
            Connection conn = DataBaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT count(*) FROM contact WHERE SOUNDEX(nom) = SOUNDEX(?) and SOUNDEX(prenom) = SOUNDEX(?) ");
            statement.setString(1, nom);
            statement.setString(2, prenom);
            ResultSet res = statement.executeQuery();
            int n = 0;
            if (res.next()) {
                n = res.getInt(1);
            }

            return n != 0;
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
    }

    public static int returnId(String nom, String prenom) throws DataBaseException {
        try {
            Connection conn = DataBaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT id_contact FROM contact WHERE SOUNDEX(nom) = SOUNDEX(?) and SOUNDEX(prenom) = SOUNDEX(?) ");
            statement.setString(1, nom);
            statement.setString(2, prenom);
            ResultSet res = statement.executeQuery();
            List<Integer> id = new ArrayList();

            while(res.next()) {
                id.add(res.getInt(1));
            }

            return id.isEmpty() ? -1 : (Integer)id.get(0);
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
    }

    public static boolean phoneticSearch(String nom, String prenom) throws DataBaseException {
        try {
            Connection conn = DataBaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT count(*) FROM contact WHERE SOUNDEX(nom) = SOUNDEX(?) and SOUNDEX(prenom) = SOUNDEX(?) ");
            statement.setString(1, nom);
            statement.setString(2, prenom);
            ResultSet res = statement.executeQuery();
            int n = 0;
            if (res.next()) {
                n = res.getInt(1);
            }

            return n != 0;
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
    }

    public static void create(Contact contact) throws Exception {
        try {
            Connection conn = DataBaseConnection.getConnection();
            String var10001;
            if (!phoneticSearchC(contact.getNom(), contact.getPrenom())) {
                PreparedStatement statement = conn.prepareStatement("insert into contact(nom,prenom,tele1,tele2,adresse,email_personnel,email_professionnel,genre) values(?,?,?,?,?,?,?,?)");
                statement.setString(1, contact.getNom());
                statement.setString(2, contact.getPrenom());
                statement.setString(3, contact.getTele1());
                statement.setString(4, contact.getTele2());
                statement.setString(5, contact.getAdresse());
                statement.setString(6, contact.getEmail_pers());
                statement.setString(7, contact.getEmail_profesionnel());
                statement.setString(8, contact.getGenre());
                statement.executeUpdate();
                Logger var10000 = LOGGER;
                var10001 = contact.getNom();
                var10000.info("Contact " + var10001 + " " + contact.getPrenom() + " is created successfully ");
            } else {
                LOGGER.error("Error: creation of Contact ");
                PrintStream var4 = System.out;
                var10001 = contact.getNom();
                var4.println("This Contact is already exist ! with the full name  " + var10001 + " " + contact.getPrenom());
            }

        } catch (Exception var3) {
            throw new DataBaseException(var3);
        }
    }

    public static boolean delete(String nom, String prenom) throws DataBaseException {
        try {
            if (returnId(nom, prenom) != -1) {
                Groupe_ContactDB.deletefromgroupe_contact(returnId(nom, prenom));
                Connection conn = DataBaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement("delete FROM contact WHERE id_contact = ?;");
                statement.setInt(1, returnId(nom, prenom));
                statement.executeUpdate();
                LOGGER.info("The contact " + nom + " " + prenom + " is deleted successfully ");
                return true;
            } else {
                return false;
            }
        } catch (Exception var4) {
            LOGGER.error("Error : delete contact");
            throw new DataBaseException(var4);
        }
    }

    public static List<Contact> getAllContact() throws DataBaseException {
        try {
            Connection conn = DataBaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("select * from contact order by nom,prenom");
            ResultSet contacts = statement.executeQuery();
            List<Contact> contactList = new ArrayList();

            while(contacts.next()) {
                contactList.add(new Contact(contacts.getString("nom"), contacts.getString("prenom"), contacts.getString("tele1"), contacts.getString("tele2"), contacts.getString("adresse"), contacts.getString("email_personnel"), contacts.getString("email_professionnel"), contacts.getString("genre")));
            }

            return contactList.isEmpty() ? null : contactList;
        } catch (Exception var4) {
            throw new DataBaseException(var4);
        }
    }

    public static List<Contact> searchContactByNom(String nom) throws DataBaseException {
        try {
            Connection conn = DataBaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("select * from contact where soundex(nom) = soundex(?)");
            statement.setString(1, nom);
            ResultSet contact = statement.executeQuery();
            List<Contact> contactList = new ArrayList();

            while(contact.next()) {
                contactList.add(new Contact(contact.getInt("id_contact"), contact.getString("nom"), contact.getString("prenom"), contact.getString("tele1"), contact.getString("tele2"), contact.getString("adresse"), contact.getString("email_personnel"), contact.getString("email_professionnel"), contact.getString("genre")));
            }

            return contactList.isEmpty() ? null : contactList;
        } catch (Exception var5) {
            throw new DataBaseException(var5);
        }
    }

    public static void update(Contact contact) throws DataBaseException {
        try {
            Connection conn = DataBaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("update contact set nom=?,prenom=?,tele1=?,tele2=?,adresse=?,email_personnel=?,email_professionnel=?,genre=? where id_contact=?");
            int id = returnId(contact.getNom(), contact.getPrenom());
            statement.setString(1, contact.getNom());
            statement.setString(2, contact.getPrenom());
            statement.setString(3, contact.getTele1());
            statement.setString(4, contact.getTele2());
            statement.setString(5, contact.getAdresse());
            statement.setString(6, contact.getEmail_pers());
            statement.setString(7, contact.getEmail_profesionnel());
            statement.setString(8, contact.getGenre());
            statement.setInt(9, id);
            statement.executeUpdate();
            Logger var10000 = LOGGER;
            String var10001 = contact.getNom();
            var10000.info("The contact " + var10001 + " " + contact.getPrenom() + " is updated successfully");
        } catch (Exception var4) {
            LOGGER.error("Error : Update Contact");
            throw new DataBaseException(var4);
        }
    }

    public static Contact searchContactByNumTele(String tele) throws DataBaseException {
        try {
            Connection conn = DataBaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("select * from contact where tele1 = ? or tele2 = ?");
            statement.setString(1, tele);
            statement.setString(2, tele);
            ResultSet contact = statement.executeQuery();
            List<Contact> contactList = new ArrayList();

            while(contact.next()) {
                Contact contact1 = new Contact(contact.getString("nom"), contact.getString("prenom"), contact.getString("tele1"), contact.getString("tele2"), contact.getString("adresse"), contact.getString("email_personnel"), contact.getString("email_professionnel"), contact.getString("genre"));
                contactList.add(contact1);
            }

            return contactList.isEmpty() ? null : (Contact)contactList.get(0);
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
    }

    public static List<Groupe> getGroupeofContact(String nomc, String prenomc) throws DataBaseException {
        try {
            if (!phoneticSearch(nomc, prenomc)) {
                throw new DataBaseException();
            } else {
                Connection conn = DataBaseConnection.getConnection();
                PreparedStatement search_stat = conn.prepareStatement("select distinct g.nom from groupe g , contact c , groupe_contact gc where gc.id_groupe = g.id_groupe and gc.id_contact = c.id_contact and soundex(c.nom) =soundex(?) and soundex(c.prenom) = soundex(?)");
                search_stat.setString(1, nomc);
                search_stat.setString(2, prenomc);
                ResultSet groupes = search_stat.executeQuery();
                List<Groupe> groupeList = new ArrayList();

                while(groupes.next()) {
                    groupeList.add(new Groupe(groupes.getString(1)));
                }

                return groupeList.isEmpty() ? null : groupeList;
            }
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
    }



    }
