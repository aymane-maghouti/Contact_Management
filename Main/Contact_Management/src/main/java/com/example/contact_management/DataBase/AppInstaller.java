package com.example.contact_management.DataBase;


import com.example.contact_management.utils.FileManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class AppInstaller {
    private static Logger logger = Logger.getLogger(AppInstaller.class);

    private static final Logger LOGGER = Logger.getLogger(AppInstaller.class);

    public AppInstaller() {
    }

    public static void run() throws Exception {
        Connection conn = DataBaseConnection.getRootConnection();
        Properties properties = DbPropertiesLoader.loadPoperties("conf.properties");
        String databaseName = properties.getProperty("db.name");
        Statement statement = conn.createStatement();
        statement.executeUpdate("CREATE DATABASE " + databaseName);
        String requeteUtilisationBD = "USE " + databaseName;
        statement.executeUpdate(requeteUtilisationBD);

        String[] sqlStatements = new String[]{
                "create table groupe (id_groupe int primary key auto_increment,nom varchar(250) not null unique);",
                "create table contact (id_contact int primary key auto_increment,nom varchar(250) not null,prenom varchar(250) not null,tele1 varchar(250) not null unique,tele2 varchar(250) not null unique,adresse varchar(250) not null,email_personnel varchar(250) not null,email_professionnel varchar(250) not null,genre varchar(250) not null);",
                "create table groupe_contact (id_groupe_contact int primary key auto_increment,id_contact int default null,id_groupe int default null);",
                "alter table groupe_contact add constraint fk_groupe foreign key (id_groupe) references groupe(id_groupe);",
                "alter table groupe_contact add constraint fk_contact foreign key (id_contact) references contact(id_contact);"
        };
        Statement stmt = conn.createStatement();

        for (String sql : sqlStatements) {
            stmt.executeUpdate(sql);
        }

        LOGGER.info("la base de donnes est cree correctement");

    }
    public static  boolean checkIfAlreadyInstalled() throws DataBaseException {

        try {
            Connection rootConn = DataBaseConnection.getRootConnection();

            Properties properties = DbPropertiesLoader.loadPoperties("conf.properties");
            String  databaseName = properties.getProperty("db.name");
            Statement statement = rootConn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + databaseName + "'");
            if (resultSet.next()) {
                return true; // deja exsite
            }
            return false;// n'exsite pas
        } catch (Exception ex){
            logger.error(ex);
            throw new DataBaseException(ex);
        }
    }
}
