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
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {
    public TableView<TransactionDetails> history;
    public TableColumn<TransactionDetails, Timestamp> colDate;
    public TableColumn<TransactionDetails, String> colProduct;
    public TableColumn<TransactionDetails, Double> colPrice;
    public TableColumn<TransactionDetails, Integer> colAmount;
    public TableColumn<TransactionDetails, Double> colSum;

    @FXML
    protected void goBackButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showUserPage();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection con = ConnectionManager.getConnection();
        ResultSet rs = null;
        PreparedStatement st = null;
        String query = "SELECT * FROM transactions WHERE customer =?";
        try {
            st = con.prepareStatement(query);
            st.setInt(1, Main.getCurrentuser().getId());
            rs = st.executeQuery();
            while(rs.next()){
                observableList.add(new TransactionDetails(rs.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // closing the connection:
        }
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



/*public class Statestik extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");

        // Define the x and y axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Money");

        // Create the chart
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Money Usage");

        // Define a data series
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("My Money");

        // Add some data to the series
        series.getData().add(new XYChart.Data<>(1, 100));
        series.getData().add(new XYChart.Data<>(2, 120));
        series.getData().add(new XYChart.Data<>(3, 150));
        series.getData().add(new XYChart.Data<>(4, 200));
        series.getData().add(new XYChart.Data<>(5, 180));
        series.getData().add(new XYChart.Data<>(6, 230));

        // Add the data series to the chart
        lineChart.getData().add(series);

        // Create the scene and add the chart to it
        Scene scene = new Scene(lineChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }*/
