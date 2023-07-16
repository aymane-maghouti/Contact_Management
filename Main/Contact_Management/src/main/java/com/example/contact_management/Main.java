package com.example.contact_management;

import com.example.contact_management.BO.Contact;
import com.example.contact_management.DataBase.AppInstaller;
import com.example.contact_management.DataBase.DataBaseException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Main extends Application {
    private static final Logger LOGGER = Logger.getLogger(Contact.class);

    double x,y=0;

    @Override
    public void start(Stage stage) throws IOException, DataBaseException {
            LOGGER.info("app start");
        try {
            if (AppInstaller.checkIfAlreadyInstalled()) {
                System.out.println("the database is already exist");
            } else {
                AppInstaller.run();
                System.out.println("the data base is created successfully ");
            }
        } catch (Exception e) {
            throw new DataBaseException(e);
        }

        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene sc = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(evt ->{
            x = evt.getScreenX();
            y = evt.getScreenY();
        });
        root.setOnMouseDragged(evt ->{
            stage.setX(evt.getScreenX()-x);
            stage.setY(evt.getScreenY()-y);
        });
        stage.setScene(sc);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}