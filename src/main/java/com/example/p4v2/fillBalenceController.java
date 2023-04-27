package com.example.p4v2;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.io.IOException;
import java.sql.*;

import static java.lang.String.valueOf;

public class fillBalenceController {
    Users currentUser;
    @FXML
    private TableView<Payment> paymentHistoryTable; //todo this.paymentHistoryTable is null

    @FXML
    private TableColumn<Payment, Timestamp> paymentHistoryTableDate;

    @FXML
    private TableColumn<Payment, Double> paymentHistoryTableAmount;
    private static float amount;
    private Integer id;
    @FXML
    public Label currentBalence;
    private Integer payment_id;
    private Integer customer_id;
    private String payment_provider = "MobilePay";
    private String status = "started";
    private static Integer conformation_id = 0;


    @FXML
    public Button id50button;
    public Button id100button;
    public Button id150button;
    public Button id200button;

    @FXML
    private TextField customAmount;

    /*public void setUser(r) {
        this.currentUser = currentUser;
        currentBalence.setText(valueOf(currentUser.getBalance()));

        //currentBalance.setText(" ");
    }*/


    public static float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        fillBalenceController.amount = amount;
    }

    public Users getCurrentUser() {
        return this.currentUser;
    }

    public Label getCurrentBalance() {
        return currentBalence;
    }


    /*public static void setCurrentUser(Users currentUser) {
        fillBalenceController.currentUser = currentUser;
    }*/

    public static Integer getConformation_id() {
        return conformation_id;
    }

    public void setPayment_id(Integer payment_id) {
        this.payment_id = payment_id;
    }

    @FXML
    protected void goBackButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showUserPage(currentUser);
    }

    public void fillBalence() {
        this.currentUser.deposit(getAmount());
    }

    public void addCurrency50(ActionEvent actionEvent) throws IOException, SQLException {
        setAmount(50);
        paymentHistory();
        Main.showPopupMobilpay(currentUser, this.payment_id, getAmount());
    }

    public void addCurrency100(ActionEvent actionEvent) throws IOException, SQLException {
        setAmount(100);
        paymentHistory();
        Main.showPopupMobilpay(currentUser, this.payment_id, getAmount());
    }

    public void addCurrency150(ActionEvent actionEvent) throws IOException, SQLException {
        setAmount(150);
        paymentHistory();
        Main.showPopupMobilpay(currentUser, this.payment_id, getAmount());
    }

    public void addCurrency200(ActionEvent actionEvent) throws IOException, SQLException {
        setAmount(200);
        paymentHistory();
        Main.showPopupMobilpay(currentUser, this.payment_id, getAmount());
    }

    public void paymentHistory() throws IOException, SQLException {
        Connection con = ConnectionManager.getConnection();
        String checkQuery = "SELECT * FROM customer WHERE email = ?";
        PreparedStatement checkStatement = con.prepareStatement(checkQuery);
        customer_id = currentUser.getId();
        //ResultSet result = checkStatement.executeQuery();

        conformation_id = (int) (new Date().getTime() / 1000);

        String qry = "INSERT INTO payment (CUSTOMER_ID, AMOUNT, PAYMENT_PROVIDER, STATUS, CONFIRMATION_ID) values(?,?,?,?,?)";
        PreparedStatement st = con.prepareStatement(qry);
        st.setInt(1, currentUser.getId());
        st.setFloat(2, getAmount());
        st.setString(3, payment_provider);
        st.setString(4, status);
        st.setInt(5, conformation_id);
        st.executeUpdate();
        //payment id equals to the id of the payment


        System.out.println("Payment ID: " + payment_id);
    }

    public void addCurrencyCustom(ActionEvent actionEvent) throws IOException, SQLException {
        Float inputAmount = Float.parseFloat(customAmount.getText());
        setAmount(inputAmount);
        paymentHistory();
        Main.showPopupMobilpay(currentUser, this.payment_id, getAmount());
    }

    public void setup(Users currentUser) {
        this.currentUser = currentUser;
        currentBalence.setText(valueOf(currentUser.getBalance()));

        paymentHistoryTableDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        paymentHistoryTableAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        ObservableList<Payment> payments = FXCollections.observableArrayList();
        // get payments for current user and add them to the TableView
        try (Connection con = ConnectionManager.getConnection()) {
            String qry = "SELECT * FROM payment WHERE customer_id = ?";
            PreparedStatement st = con.prepareStatement(qry);
            st.setInt(1, currentUser.getId());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Date date = new Date(rs.getLong("timestamp") * 1000);
                float amount = rs.getFloat("amount");
                Payment payment = new Payment(date, amount);
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        paymentHistoryTable.setItems(payments);
    }

}