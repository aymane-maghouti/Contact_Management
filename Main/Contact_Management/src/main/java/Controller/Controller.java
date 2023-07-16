package Controller;
import com.example.contact_management.BO.Contact;
import com.example.contact_management.BO.Groupe;
import com.example.contact_management.DataBase.DataBaseConnection;
import com.example.contact_management.DataBase.DataBaseException;
import com.example.contact_management.DataBase.GroupeDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.contact_management.DataBase.ContactDB.*;
import static com.example.contact_management.DataBase.GroupeDB.*;

public class Controller implements Initializable {
    @FXML
    private Button AC_group_add;

    @FXML
    private Button about_btn;

    @FXML
    private AnchorPane about_form;

    @FXML
    private Button add_btn_con_grp;

    @FXML
    private Button close;

    @FXML
    private Button contact_add_btn;

    @FXML
    private TextField contact_address;

    @FXML
    private Button contact_btn;

    @FXML
    private Button contact_clear_btn;

    @FXML
    private TableColumn<Contact, String> contact_col_Per_email;

    @FXML
    private TableColumn<Contact, String> contact_col_Pro_email;

    @FXML
    private TableColumn<Contact, String> contact_col_address;

    @FXML
    private TableColumn<Contact, String> contact_col_firstname;

    @FXML
    private TableColumn<Contact, String> contact_col_gender;

    @FXML
    private TableColumn<Contact, String> contact_col_lastname;

    @FXML
    private TableColumn<Contact, String> contact_col_phone1;

    @FXML
    private TableColumn<Contact, String> contact_col_phone2;

    @FXML
    private Button contact_del_btn;

    @FXML
    private TextField contact_email_per;

    @FXML
    private TextField contact_email_pro;

    @FXML
    private TextField contact_fisrt_name;

    @FXML
    private AnchorPane contact_form;

    @FXML
    private ComboBox<?> contact_gender;

    @FXML
    private TextField contact_last_name;

    @FXML
    private TextField contact_phone1;

    @FXML
    private TextField contact_phone2;

    @FXML
    private TextField contact_search;

    @FXML
    private AnchorPane contact_tableView;

    @FXML
    private Button contact_update_btn;

    @FXML
    private Button del_btn_con_grp;

    @FXML
    private TableColumn<Groupe, String> groupNmae_col;

    @FXML
    private TableView<Groupe> group_table_view;

    @FXML
    private Button group_add_btn;

    @FXML
    private Button group_btn;

    @FXML
    private Button group_clear_btn;

    @FXML
    private Button group_del_btn;

    @FXML
    private TextField group_first_name_contact;

    @FXML
    private AnchorPane group_form;

    @FXML
    private TextField group_group_name;

    @FXML
    private TextField group_last_name_contact;

    @FXML
    private TextField group_name;

    @FXML
    private AnchorPane menu;

    @FXML
    private ImageView minimize;

    @FXML
    private TextField search_grp;

    @FXML
    private AnchorPane main_form;


    @FXML
    private TableView<Contact> contact_table_view;


    @FXML
    public void switchForm(ActionEvent event) {

        if (event.getSource() == contact_btn) {
            contact_form.setVisible(true);
            about_form.setVisible(false);
            group_form.setVisible(false);

            contact_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            group_btn.setStyle("-fx-background-color:transparent");
            about_btn.setStyle("-fx-background-color:transparent");

            ContactGenderList(new ActionEvent());
            ContactSearch(new KeyEvent(KeyEvent.KEY_TYPED, "", "", KeyCode.UNDEFINED, false, false, false, false));


        } else if (event.getSource() == group_btn) {
            contact_form.setVisible(false);
            group_form.setVisible(true);
            about_form.setVisible(false);

            group_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            contact_btn.setStyle("-fx-background-color:transparent");
            about_btn.setStyle("-fx-background-color:transparent");

            GroupSearch(new KeyEvent(KeyEvent.KEY_TYPED, "", "", KeyCode.UNDEFINED, false, false, false, false));


        } else if (event.getSource() == about_btn) {
            contact_form.setVisible(false);
            about_form.setVisible(true);
            group_form.setVisible(false);

            about_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            group_btn.setStyle("-fx-background-color:transparent");
            contact_btn.setStyle("-fx-background-color:transparent");


        }

    }

    @FXML
    public void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void minimize(ActionEvent event) {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }


    // Group Form
    @FXML
    public void AddGroup(ActionEvent event) {
        try {
            Alert alert;
            if (group_name.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                if ((searchGroupeByName(group_name.getText()))!=null ) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Group " + contact_fisrt_name.getText() + " was already exist!");
                    alert.showAndWait();
                } else {
                    Groupe group = new Groupe(group_name.getText());
                    GroupeDB.create(group);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added!");
                    alert.showAndWait();
                    GroupShowListData();
                    GroupReset(new ActionEvent());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ACGroup(ActionEvent event) {
        try {
            Alert alert;
            CreationAutomatique();
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully added!");
            alert.showAndWait();
            GroupShowListData();
            GroupReset(new ActionEvent());
        }catch(Exception e){
            e.printStackTrace();

        }
    }

    @FXML
    void GroupSearch(KeyEvent event) {
        search_grp.textProperty().addListener((Observable, oldValue, newValue) -> {
            FilteredList<Groupe> filteredList = new FilteredList<>(GroupList, e -> true);

            filteredList.setPredicate(predicateGroup -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateGroup.getNom().contains(searchKey)) {
                    return true;
                }  else {
                    return false;
                }
            });

            SortedList<Groupe> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(group_table_view.comparatorProperty());
            group_table_view.setItems(sortedList);
        });
    }
    @FXML
    void DeleteGroup(ActionEvent event) {
        try {

            Alert alert;
            if (group_name.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Group: " + group_name.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String name = group_name.getText() ;

                    GroupeDB.delete(name);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    GroupShowListData();
                    GroupReset(new ActionEvent());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void GroupReset(ActionEvent event) {
        group_name.setText("");
        group_first_name_contact.setText("");
        group_last_name_contact.setText("");
        group_group_name.setText("");

    }


    public void GroupShowListData() {
        GroupList = GroupListData();
        groupNmae_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
        group_table_view.setItems(GroupList);
    }

    @FXML
    public void GroupSelect(MouseEvent event){
        Groupe groupe = group_table_view.getSelectionModel().getSelectedItem();
        int num = group_table_view.getSelectionModel().getSelectedIndex();

        if((num-1)<-1){return;}
        group_name.setText(groupe.getNom());

    }





    // Contac Form
    @FXML
    public void AddContact(ActionEvent event) {
        try {
            Alert alert;
            if (contact_fisrt_name.getText().isEmpty()
                    || contact_last_name.getText().isEmpty()
                    || contact_phone1.getText().isEmpty()
                    || contact_phone2.getText().isEmpty()
                    || contact_address.getText().isEmpty()
                    || contact_email_per.getText().isEmpty()
                    || contact_email_pro.getText().isEmpty()
                    || contact_gender.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                if ((searchContactByNumTele(contact_phone1.getText()))!=null || (searchContactByNumTele(contact_phone1.getText()))!=null  ) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Contact " + contact_fisrt_name.getText() + " "+ contact_last_name.getText() + " was already exist with this phone number : "+contact_phone1.getText()+"/"+contact_phone2.getText()+"!");
                    alert.showAndWait();
                } else {
                    Contact contact = new Contact(contact_last_name.getText(),contact_fisrt_name.getText(),contact_phone1.getText(),contact_phone2.getText(),contact_address.getText(),contact_email_per.getText(),contact_email_pro.getText(), (String) contact_gender.getSelectionModel().getSelectedItem());
                    create(contact);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added!");
                    alert.showAndWait();
                    ContactShowListData();
                    ContactReset(new ActionEvent());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void ContactReset(ActionEvent event){
        contact_fisrt_name.setText("");
        contact_last_name.setText("");
        contact_phone1.setText("");
        contact_phone2.setText("");
        contact_address.setText("");
        contact_email_per.setText("");
        contact_email_pro.setText("");
        contact_gender.getSelectionModel().clearSelection();
    }
    @FXML
    void ContactUpdate(ActionEvent event) {
        try {
            Alert alert;
            if (contact_fisrt_name.getText().isEmpty()
                    || contact_last_name.getText().isEmpty()
                    || contact_phone1.getText().isEmpty()
                    || contact_phone2.getText().isEmpty()
                    || contact_address.getText().isEmpty()
                    || contact_email_per.getText().isEmpty()
                    || contact_email_pro.getText().isEmpty()
                    || contact_gender.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Contact : " + contact_fisrt_name.getText() +" "+contact_last_name.getText()+ "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    Contact contact = new Contact(contact_last_name.getText(),contact_fisrt_name.getText(),contact_phone1.getText(),contact_phone2.getText(),contact_address.getText(),contact_email_per.getText(),contact_email_pro.getText(), (String) contact_gender.getSelectionModel().getSelectedItem());
                    update(contact);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();


                    ContactShowListData();
                    ContactReset(new ActionEvent());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void ContactDelete(ActionEvent event) {


        try {

            Alert alert;
            if (contact_fisrt_name.getText().isEmpty()
                    || contact_last_name.getText().isEmpty()
                    || contact_phone1.getText().isEmpty()
                    || contact_phone2.getText().isEmpty()
                    || contact_address.getText().isEmpty()
                    || contact_email_per.getText().isEmpty()
                    || contact_email_pro.getText().isEmpty()
                    ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Contact: " + contact_fisrt_name.getText() +" "+contact_last_name.getText()+ "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String firstname = contact_fisrt_name.getText() ;
                    String lasename = contact_last_name.getText();
                    delete(lasename,firstname);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    ContactShowListData();
                    ContactReset(new ActionEvent());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    @FXML
    public void ContactSearch(KeyEvent event) {
        contact_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            FilteredList<Contact> filteredList = new FilteredList<>(ContactList, e -> true);

            filteredList.setPredicate(predicateContact -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateContact.getNom().contains(searchKey)) {
                    return true;
                } else if (predicateContact.getPrenom().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateContact.getTele1().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateContact.getTele2().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateContact.getAdresse().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateContact.getEmail_pers().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateContact.getEmail_profesionnel().contains(searchKey)) {
                    return true;
                } else if (predicateContact.getGenre().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });

            SortedList<Contact> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(contact_table_view.comparatorProperty());
            contact_table_view.setItems(sortedList);
        });
    }




    @FXML
    public void ContactSelect(MouseEvent event){
        Contact contact = contact_table_view.getSelectionModel().getSelectedItem();
        int num = contact_table_view.getSelectionModel().getSelectedIndex();

        if((num-1)<-1){return;}

        contact_fisrt_name.setText(contact.getPrenom());
        contact_last_name.setText(contact.getNom());
        contact_phone1.setText(contact.getTele1());
        contact_phone2.setText(contact.getTele2());
        contact_email_per.setText(contact.getEmail_pers());
        contact_email_pro.setText(contact.getEmail_profesionnel());
        contact_address.setText(contact.getAdresse());
    }









    private String[] genders = {"Male","Female"};
    @FXML
    public void ContactGenderList(ActionEvent event){
        List<String> listP = new ArrayList<>();

        for (String gender : genders) {
            listP.add(gender);
        }

        ObservableList listData = FXCollections.observableArrayList(listP);
        contact_gender.setItems(listData);
    }


    public ObservableList<Contact> ContactListData() {


        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        try{
            Connection conn = DataBaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("select * from contact order by nom,prenom");
            ResultSet contacts = statement.executeQuery();


            while(contacts.next()) {
                contactList.add(new Contact(contacts.getString("nom"), contacts.getString("prenom"), contacts.getString("tele1"), contacts.getString("tele2"), contacts.getString("adresse"), contacts.getString("email_personnel"), contacts.getString("email_professionnel"), contacts.getString("genre")));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return contactList;

    }

    public ObservableList<Groupe> GroupListData() {


        ObservableList<Groupe> groupes = FXCollections.observableArrayList();
        try{
            Connection conn = DataBaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("select * from groupe order by nom");
            ResultSet groups = statement.executeQuery();


            while(groups.next()) {
                groupes.add(new Groupe(groups.getString("nom")));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return groupes;

    }

    private ObservableList<Contact> ContactList;
    private ObservableList<Groupe> GroupList;
    public void ContactShowListData() {
        ContactList = ContactListData();
        
        contact_col_firstname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        contact_col_lastname.setCellValueFactory(new PropertyValueFactory<>("nom"));
        contact_col_phone1.setCellValueFactory(new PropertyValueFactory<>("tele1"));
        contact_col_phone2.setCellValueFactory(new PropertyValueFactory<>("tele2"));
        contact_col_address.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        contact_col_Per_email.setCellValueFactory(new PropertyValueFactory<>("email_pers"));
        contact_col_Pro_email.setCellValueFactory(new PropertyValueFactory<>("email_profesionnel"));
        contact_col_gender.setCellValueFactory(new PropertyValueFactory<>("genre"));

        contact_table_view.setItems(ContactList);

    }


    // contact <==> group
    @FXML
    void Add_Contact_To_Group(ActionEvent event) throws DataBaseException {
        try {
            Alert alert;
            if (group_first_name_contact.getText().isEmpty()
                    || group_last_name_contact.getText().isEmpty()
                    || group_group_name.getText().isEmpty()
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to Add Contact: " + group_first_name_contact.getText() + " " + group_last_name_contact.getText() + " to Group :" + group_group_name.getText());
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String firstname = group_first_name_contact.getText();
                    String lasename = group_last_name_contact.getText();
                    String groupname = group_group_name.getText();
                    addContactToGroupe(groupname, lasename, firstname);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    GroupReset(new ActionEvent());
                }

            }
        }catch(Exception e){
                e.printStackTrace();
            }

        }


    @FXML
    void delete_Contact_Group(ActionEvent event) {
        try {
            Alert alert;
            if (group_first_name_contact.getText().isEmpty()
                    || group_last_name_contact.getText().isEmpty()
                    || group_group_name.getText().isEmpty()
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to Delete Contact: " + group_first_name_contact.getText() + " " + group_last_name_contact.getText() + " from Group :" + group_group_name.getText());
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String firstname = group_first_name_contact.getText();
                    String lasename = group_last_name_contact.getText();
                    String groupname = group_group_name.getText();

//                    deleteContactFromGroupe(groupname, lasename, firstname);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    GroupReset(new ActionEvent());
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void defaultNav() {
        contact_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContactShowListData();
        defaultNav();
        ContactGenderList(new ActionEvent());

        GroupShowListData();

    }
}