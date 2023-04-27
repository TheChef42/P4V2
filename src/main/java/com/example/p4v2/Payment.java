package com.example.p4v2;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Payment {
    public Timestamp created_at;
    public float amount;
    public final ArrayList<Payment> payments = new ArrayList<Payment>();
    public Payment(){}

    public Payment(Timestamp date, float amount) {
        this.created_at = date;
        this.amount = amount;
    }

    public ArrayList<Payment> getPayments(int currentUserId) {

        try (Connection con = ConnectionManager.getConnection()) {
            String qry = "SELECT amount, created_at FROM payment WHERE customer_id = ?";
            PreparedStatement st = con.prepareStatement(qry);
            st.setInt(1, 1);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Payment payment = new Payment(rs.getTimestamp("created_at"), rs.getFloat("amount"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this.payments;
    }
}
