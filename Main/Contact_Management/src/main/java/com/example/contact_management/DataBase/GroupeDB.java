package com.example.contact_management.DataBase;

import com.example.contact_management.BO.Contact;
import com.example.contact_management.BO.Groupe;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GroupeDB {
    private static final Logger LOGGER = Logger.getLogger(GroupeDB.class);

    public GroupeDB() {
    }

    public static void create(Groupe groupe) throws DataBaseException {
        try {
            Connection conn = DataBaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("insert into groupe(nom) values(?)");
            statement.setString(1, groupe.getNom());
            statement.executeUpdate();
            LOGGER.info("le groupe " + groupe.getNom() + " a ete cree");
        } catch (Exception var3) {
            LOGGER.error("Erreur de creation du groupe");
            throw new DataBaseException(var3);
        }
    }

    public static List<Groupe> getAll() throws DataBaseException {
        try {
            Connection conn = DataBaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("select * from groupe order by nom");
            ResultSet groupes = statement.executeQuery();
            List<Groupe> groupeList = new ArrayList();

            while(groupes.next()) {
                groupeList.add(new Groupe(groupes.getInt("id_groupe"), groupes.getString("nom")));
            }

            return groupeList.isEmpty() ? null : groupeList;
        } catch (Exception var4) {
            throw new DataBaseException(var4);
        }
    }

    public static Groupe searchGroupeByName(String nom) throws DataBaseException {
        try {
            Connection conn = DataBaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("select * from groupe where soundex(nom)= soundex(?)");
            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();
            List<Groupe> groupeList = new ArrayList();

            while(resultSet.next()) {
                groupeList.add(new Groupe(resultSet.getInt("id_groupe"), resultSet.getString("nom")));
            }

            return groupeList.isEmpty() ? null : (Groupe)groupeList.get(0);
        } catch (Exception var5) {
            throw new DataBaseException(var5);
        }
    }

    public static int addContactToGroupe(String nomg, String nomc, String prenomc) throws DataBaseException {
        try {
            Connection conn = DataBaseConnection.getConnection();
            int idc = ContactDB.returnId(nomc, prenomc);
            if (idc == -1) {
                LOGGER.error("Erreur d'ajout  d'un contact dans le groupe" + nomg + " (le contact avec le nom complete :" + nomc + " " + prenomc + " n'exsite pas )");
                return 2;
            } else if (searchGroupeByName(nomg) == null) {
                LOGGER.error("Erreur d'ajout  du contact " + nomc + " " + prenomc + " dans un groupe  (le groupe avec le nom  :" + nomg + " n'exsite pas )");
                return 0;
            } else if (searchGroupeByName(nomg) != null) {
                int idg = searchGroupeByName(nomg).getId_groupe();
                PreparedStatement statement_update = conn.prepareStatement("insert into groupe_contact(id_contact, id_groupe) values (?,?)");
                statement_update.setInt(1, idc);
                statement_update.setInt(2, idg);
                statement_update.executeUpdate();
                LOGGER.info("le contact " + nomc + " " + prenomc + " a ete ajoutee dans le groupe " + nomg);
                return 1;
            } else {
                LOGGER.error("Erreur d'ajout  du contact " + nomc + " " + prenomc + " dans un groupe  (le groupe avec le nom  :" + nomg + " n'exsite pas )");
                return 0;
            }
        } catch (Exception var7) {
            LOGGER.error("Erreur d'ajout  d'un contact dans un groupe");
            throw new DataBaseException(var7);
        }
    }

    public static int delete(String nom_groupe) throws DataBaseException {
        try {
            if (searchGroupeByName(nom_groupe) != null) {
                Connection conn = DataBaseConnection.getConnection();
                int idGroupe = searchGroupeByName(nom_groupe).getId_groupe();
                Groupe_ContactDB.deletegroupefromgroupe_contact(idGroupe);
                PreparedStatement statement2 = conn.prepareStatement("delete from groupe where id_groupe=? ");
                statement2.setInt(1, idGroupe);
                LOGGER.info("le groupe " + nom_groupe + " a ete supprimee");
                statement2.executeUpdate();
                return 1;
            } else {
                return 0;
            }
        } catch (Exception var4) {
            LOGGER.error("Erreur de suppression du groupe");
            throw new DataBaseException(var4);
        }
    }

    public static void CreationAutomatique() throws DataBaseException {
        try {
            Connection conn = DataBaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT DISTINCT  nom FROM contact");
            ResultSet res = statement.executeQuery();

            while(true) {
                String nomG;
                ResultSet resultSet;
                int idG;
                PreparedStatement id_contact;
                ResultSet id_contacts;
                PreparedStatement insert_statement;
                PreparedStatement statement2;
                label37:
                do {
                    while(res.next()) {
                        nomG = res.getString(1);
                        PreparedStatement statement1;
                        if (searchGroupeByName(nomG) != null) {
                            statement1 = conn.prepareStatement("select id_groupe from groupe where nom = ?");
                            statement1.setString(1, nomG);
                            resultSet = statement1.executeQuery();
                            continue label37;
                        }

                        statement1 = conn.prepareStatement("INSERT INTO groupe(nom) VALUES (?)", 1);
                        statement1.setString(1, nomG);
                        statement1.executeUpdate();
                        resultSet = statement1.getGeneratedKeys();
                        if (resultSet.next()) {
                            idG = resultSet.getInt(1);
                            id_contact = conn.prepareStatement("select id_contact from contact where nom = ? ");
                            id_contact.setString(1, nomG);
                            id_contacts = id_contact.executeQuery();

                            while(id_contacts.next()) {
                                insert_statement = conn.prepareStatement("insert into groupe_contact(id_contact) values (?) ");
                                insert_statement.setInt(1, id_contacts.getInt(1));
                                insert_statement.executeUpdate();
                                statement2 = conn.prepareStatement("UPDATE groupe_contact SET id_groupe=? WHERE id_contact = ? and id_groupe is null ");
                                statement2.setInt(1, idG);
                                statement2.setInt(2, id_contacts.getInt(1));
                                LOGGER.info("la creation automatique des groupe a ete realisee");
                                statement2.executeUpdate();
                            }
                        }
                    }

                    return;
                } while(!resultSet.next());

                idG = resultSet.getInt(1);
                id_contact = conn.prepareStatement("select id_contact from contact where nom = ? ");
                id_contact.setString(1, nomG);
                id_contacts = id_contact.executeQuery();

                while(id_contacts.next()) {
                    insert_statement = conn.prepareStatement("insert into groupe_contact(id_contact) values (?) ");
                    insert_statement.setInt(1, id_contacts.getInt(1));
                    insert_statement.executeUpdate();
                    statement2 = conn.prepareStatement("UPDATE groupe_contact SET id_groupe=? WHERE id_contact =? and id_groupe is null ");
                    statement2.setInt(1, idG);
                    statement2.setInt(2, id_contacts.getInt(1));
                    LOGGER.info("la creation automatique des groupe a ete realisee");
                    statement2.executeUpdate();
                }
            }
        } catch (Exception var11) {
            LOGGER.error("Erreur de creation automatique des groupes");
            throw new DataBaseException(var11);
        }
    }

    public static List<Contact> getContactOfGroupe(String nomg) throws DataBaseException {
        try {
            List<Contact> contactList = new ArrayList();
            if (searchGroupeByName(nomg) == null) {
                throw new DataBaseException();
            } else {
                Connection conn = DataBaseConnection.getConnection();
                PreparedStatement search_statement = conn.prepareStatement("select distinct c.nom,c.prenom,c.tele1,c.tele2,c.adresse,c.email_personnel ,c.email_professionnel ,c.genre from groupe g ,contact c , groupe_contact gc where g.id_groupe = gc.id_groupe and c.id_contact = gc.id_contact  and g.nom = ?");
                search_statement.setString(1, nomg);
                ResultSet cont_lis = search_statement.executeQuery();

                while(cont_lis.next()) {
                    String nom = cont_lis.getString(1);
                    String prenom = cont_lis.getString(2);
                    String tele1 = cont_lis.getString(3);
                    String tele2 = cont_lis.getString(4);
                    String adresse = cont_lis.getString(4);
                    String email_per = cont_lis.getString(5);
                    String email_pro = cont_lis.getString(6);
                    String genre = cont_lis.getString(7);
                    contactList.add(new Contact(nom, prenom, tele1, tele2, adresse, email_per, email_pro, genre));
                }

                return contactList.isEmpty() ? null : contactList;
            }
        } catch (Exception var13) {
            throw new DataBaseException(var13);
        }
    }

}
