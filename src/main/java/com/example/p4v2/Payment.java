package com.example.p4v2;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Payment {
    private Timestamp created_at;
    private float amount;
    public final ArrayList<Payment> payments = new ArrayList<Payment>();

    public Payment(){}

    public Payment(Timestamp date, float amount) {
        this.created_at = date;
        this.amount = amount;
    }

    public ArrayList<Payment> getPayments() {

        try (Connection con = ConnectionManager.getConnection()) {
            String qry = "SELECT amount, created_at FROM payment WHERE customer_id = ? AND status = 'confirmed'";
            PreparedStatement st = con.prepareStatement(qry);
            System.out.println(Main.getCurrentuser().getId());
            st.setInt(1, Main.getCurrentuser().getId());
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

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
