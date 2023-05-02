package com.example.p4v2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {
    public TableView<TransactionDetails> history;
    public TableColumn<TransactionDetails, Timestamp> colDate;
    public TableColumn<TransactionDetails, String> colProduct;
    public TableColumn<TransactionDetails, Double> colPrice;
    public TableColumn<TransactionDetails, Integer> colAmount;
    public TableColumn<TransactionDetails, Double> colSum;
    public TableColumn<TransactionDetails, Integer> colTransaction;

    @FXML
    protected void goBackButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showUserPage();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection con = ConnectionManager.getConnection();
        ResultSet rs = null;
        ResultSet rs2 = null;
        PreparedStatement st = null;
        String query = "SELECT id FROM transactions WHERE customer =?";
        try {
            st = con.prepareStatement(query);
            st.setInt(1, Main.getCurrentuser().getId());
            rs = st.executeQuery();
            while(rs.next()){
                query = "SELECT id FROM transactions_info WHERE transaction_id =?";
                st = con.prepareStatement(query);
                st.setInt(1, rs.getInt("id"));
                rs2 = st.executeQuery();
                while (rs2.next()){
                    observableList.add(new TransactionDetails(rs2.getInt("id")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // closing the connection:
        }
        colTransaction.setCellValueFactory(new PropertyValueFactory<TransactionDetails, Integer>("transactionId"));
        colDate.setCellValueFactory(new PropertyValueFactory<TransactionDetails, Timestamp>("date"));
        colProduct.setCellValueFactory(new PropertyValueFactory<TransactionDetails, String>("productName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<TransactionDetails, Double>("Price"));
        colAmount.setCellValueFactory(new PropertyValueFactory<TransactionDetails, Integer>("amount"));
        colSum.setCellValueFactory(new PropertyValueFactory<TransactionDetails,Double>("sumPrice"));
        history.setItems(observableList);

    }

    private void getTransactionDetails() {

    }

    ObservableList<TransactionDetails> observableList = FXCollections.observableArrayList();
}
