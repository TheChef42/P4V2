package com.example.p4v2;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;

public class AdminUserOverviewController implements Initializable {

    public TableView<Users> usersTable;
    public TableColumn<Users, Integer> colUserID;
    public TableColumn<Users, String> colName;
    public TableColumn<Users, String> colEmail;
    public TableColumn<Users, Float> colBalance;
    public TableColumn<Users, Date> colCreatedAt;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Users> observableList = FXCollections.observableArrayList(Main.getCurrentAdmin().getUsers());
        colUserID.setCellValueFactory(new PropertyValueFactory<Users, Integer>("id"));
        
        colName.setCellValueFactory(new PropertyValueFactory<Users, String>("name"));
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Users,String>>() {

            @Override
            public void handle(CellEditEvent<Users, String> event){
                Users user = event.getRowValue();
                user.updateName(event.getNewValue());
            }
            
        });
        
        colEmail.setCellValueFactory(new PropertyValueFactory<Users, String>("email"));
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        colEmail.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Users,String>>() {

            @Override
            public void handle(CellEditEvent<Users, String> event){
                Users user = event.getRowValue();
                user.setObjectEmail(event.getNewValue());
                user.setEmail(event.getNewValue());
            }
            
        });

        colBalance.setCellValueFactory(new PropertyValueFactory<Users, Float>("balance"));
        colBalance.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        colBalance.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Users,Float>>() {

            @Override
            public void handle(CellEditEvent<Users, Float> event){
                event.getRowValue().deposit( - event.getRowValue().getBalance() + event.getNewValue());
            }
            
        });

        colCreatedAt.setCellValueFactory(new PropertyValueFactory<Users, Date>("createdAt"));

        usersTable.setEditable(true);
        usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        usersTable.setItems(observableList);
    }

    @FXML
    protected void showProductOverview() throws IOException {
        Main.showProductOverview();
    }

    @FXML
    protected void goBack() throws IOException {
        Main.showAdminPage();
    }

    @FXML
    protected void logoutButtonClick() throws IOException {
        Main.setCurrentAdmin(null);
        Main.showLoginView();
    }

    @FXML
    protected void deleteUserClick(ActionEvent event) throws IOException {
        Users user = usersTable.getSelectionModel().getSelectedItem();
        Users.deleteAccount(user);

        //Reloads the page:
        ObservableList<Users> observableList = FXCollections.observableArrayList(Main.getCurrentAdmin().getUsers());
        usersTable.setItems(observableList);

        }
}

