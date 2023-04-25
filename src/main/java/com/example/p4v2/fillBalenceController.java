package com.example.p4v2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.Date;

import java.io.IOException;
import java.sql.*;

import static java.lang.String.valueOf;

public class fillBalenceController {
    static Users currentUser;
    private static float amount;
    private Integer id;
    @FXML
    public static Label currentBalance;
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
    public static void setUser(Users currentUser) {
        fillBalenceController.currentUser = currentUser;

        //currentBalance.setText(" ");
    }



    public static float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        fillBalenceController.amount = amount;
    }

    public Users getCurrentUser() {
        return fillBalenceController.currentUser;
    }

    public Label getCurrentBalance() {
        return currentBalance;
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

    public static void fillBalence(){
        fillBalenceController.currentUser.deposit(getAmount());
    }

    public void addCurrency50(ActionEvent actionEvent) throws IOException, SQLException {
        setAmount(50);
        paymentHistory();
        Main.showPopupMobilpay(currentUser, this.id);
    }

    public void addCurrency100(ActionEvent actionEvent) throws IOException {
        setAmount(100);
        Main.showPopupMobilpay(currentUser, this.id);
    }
    public void addCurrency150(ActionEvent actionEvent) throws IOException {
        setAmount(150);
        Main.showPopupMobilpay(currentUser, this.id);
    }
    public void addCurrency200 (ActionEvent actionEvent) throws IOException {
        setAmount(200);
        Main.showPopupMobilpay(currentUser, this.id);
    }

    public void paymentHistory () throws IOException, SQLException {
        Connection con = ConnectionManager.getConnection();
        String checkQuery = "SELECT * FROM customer WHERE email = ?";
        PreparedStatement checkStatement = con.prepareStatement(checkQuery);
        customer_id = currentUser.getId();
        //ResultSet result = checkStatement.executeQuery();

        conformation_id = (int) (new Date().getTime()/1000);

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

    public void addCurrencyCustom (ActionEvent actionEvent) throws IOException {
        Float inputAmount = Float.parseFloat(customAmount.getText());
        setAmount(inputAmount);
        Main.showPopupMobilpay(currentUser, this.id);
    }
}
