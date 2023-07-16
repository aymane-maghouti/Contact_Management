package com.example.contact_management.BO;

public class Contact {

    private int id;
    private String nom;
    private String prenom;
    private String tele1;
    private String tele2;
    private String adresse;
    private String email_pers;
    private String email_profesionnel;
    private String genre;

    public Contact(int id, String nom, String prenom, String tele1, String tele2, String adresse, String email_pers, String email_profesionnel, String genre) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.tele1 = tele1;
        this.tele2 = tele2;
        this.adresse = adresse;
        this.email_pers = email_pers;
        this.email_profesionnel = email_profesionnel;
        this.genre = genre;
    }

    public Contact(String nom, String prenom, String tele1, String tele2, String adresse, String email_pers, String email_profesionnel, String genre) {
        this.nom = nom;
        this.prenom = prenom;
        this.tele1 = tele1;
        this.tele2 = tele2;
        this.adresse = adresse;
        this.email_pers = email_pers;
        this.email_profesionnel = email_profesionnel;
        this.genre = genre;
    }

    public String toString() {
        return "Contact [Nom: " + this.nom + ", Prenom : " + this.prenom + ", Tele 1: " + this.tele1 + ", Tele 2: " + this.tele2 + ", Adresse : " + this.adresse + ", Email personnel : " + this.email_pers + ", Email professionnel : " + this.email_profesionnel + ", Genre : " + this.genre + " ]";
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTele1() {
        return this.tele1;
    }

    public void setTele1(String tele1) {
        this.tele1 = tele1;
    }

    public String getTele2() {
        return this.tele2;
    }

    public void setTele2(String tele2) {
        this.tele2 = tele2;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail_pers() {
        return this.email_pers;
    }

    public void setEmail_pers(String email_pers) {
        this.email_pers = email_pers;
    }

    public String getEmail_profesionnel() {
        return this.email_profesionnel;
    }

    public void setEmail_profesionnel(String email_profesionnel) {
        this.email_profesionnel = email_profesionnel;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
