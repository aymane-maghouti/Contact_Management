package com.example.contact_management.BO;

import java.util.ArrayList;
import java.util.List;

public class Groupe {
    private int id_groupe;
    private String nom;
    private List<Contact> contactList = new ArrayList();

    public Groupe(int id_groupe, String nom) {
        this.id_groupe = id_groupe;
        this.nom = nom;
    }

    public Groupe(String nom) {
        this.nom = nom;
    }

    public int getId_groupe() {
        return this.id_groupe;
    }

    public void setId_groupe(int id_groupe) {
        this.id_groupe = id_groupe;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Contact> getContactList() {
        return this.contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public String toString() {
        return "Groupe :[ Nom : " + this.nom + " ]";
    }
}
