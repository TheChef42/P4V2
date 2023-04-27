package com.example.p4v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PopupMobilepayController {

    private Stage stage;

    Users currentUser;
    private Integer id;
    private boolean answer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(Users user) {
        this.currentUser = user;
    }

    public void confirmPayment(Integer id) {
        Connection con = ConnectionManager.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "UPDATE payment SET STATUS = ?, CONFIRMATION_ID = ?  WHERE CONFIRMATION_ID=?";
        try {
            st = con.prepareStatement(query);
            st.setString(1, "confirmed");
            st.setInt(2, 69);
            st.setInt(3, id);
            int rowsAffected = st.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
            st.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }

    }

    @FXML
    private void handleConfirmButton(ActionEvent actionEvent) throws IOException {
        id = fillBalenceController.getConformation_id();
        confirmPayment(id);
        this.currentUser.deposit(fillBalenceController.getAmount());
        Main.showFillBalence(Main.getCurrentuser());
    }

    @FXML
    private void handleCancelButton() throws IOException {
        setAnswer(false);
        Main.showFillBalence(currentUser);
    }
}

