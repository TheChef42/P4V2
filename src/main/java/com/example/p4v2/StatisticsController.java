package com.example.p4v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class StatisticsController {
    Users currentUser;
    public void setUser(Users currentUser) {
        this.currentUser = currentUser;
    }

    @FXML
    protected void goBackButtonClick(ActionEvent event) throws IOException {
        //Change stage to user profile when the scene has been made
        Main.showUserPage(currentUser);
    }
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
