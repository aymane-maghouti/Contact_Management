package com.example.contact_management.DataBase;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {

    private static Logger logger = Logger.getLogger(AppInstaller.class);

    private static String dbUrl;

    private static String dbName;

    private static String login;
    private static String password;
    private static String driver;
    private static Connection connection;

    private static Connection rootConnection;


    private DataBaseConnection() throws DataBaseException {
        try {
            // Lire le fichier de configuration conf.propeties
            Properties dbProperties = DbPropertiesLoader.loadPoperties("conf.properties");
            dbName = dbProperties.getProperty("db.name");
            dbUrl = dbProperties.getProperty("db.url");
            login = dbProperties.getProperty("db.login");
            password = dbProperties.getProperty("db.password");
            driver = dbProperties.getProperty("db.driver");

            // charger le pilote
            Class.forName(driver);

            // Créer une connexion à la base de données
            rootConnection = DriverManager.getConnection(dbUrl, login, password);
        } catch (Exception ex) {
            //tracer cette erreur
            logger.error(ex);
            //raise the exception stack
            throw new DataBaseException(ex);
        }
    }

    public static Connection getConnection() throws DataBaseException {
        if(connection == null) {
            if (rootConnection == null) {
                new DataBaseConnection();
            }
            try {
                connection = DriverManager.getConnection(dbUrl + dbName, login, password);
            } catch (SQLException ex) {
                logger.error(ex);
                throw new DataBaseException(ex);
            }
        }
        return connection;
    }

    public static Connection getRootConnection() throws DataBaseException {
        if(rootConnection == null){
            new DataBaseConnection();
        }
        return rootConnection;
    }


}
