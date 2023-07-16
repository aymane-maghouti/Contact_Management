package com.example.contact_management.DataBase;

public class DataBaseException extends Exception {

    public DataBaseException() {
        super("Error : database error");
    }

    public DataBaseException(Throwable ex) {
        super(ex);
    }


}




