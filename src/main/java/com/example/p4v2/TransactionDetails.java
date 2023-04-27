package com.example.p4v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDetails {
    private int id;
    private int transactonId;
    private String productName;
    private int amount;
    private Double price;
    private Double sumPrice;

    public TransactionDetails(int id){
        this.id = id;
        try {
            Connection con = ConnectionManager.getConnection();
            String qry = "SELECT * FROM transactions_info, products WHERE id = ?, transactions_info.product=products.product(+)";
            PreparedStatement st = con.prepareStatement(qry);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            System.out.println("test");
            while(rs.next()){
                this.productName = rs.getString("product");
                System.out.println(rs.getString("product"));
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        TransactionDetails trans = new TransactionDetails(3);
        System.out.println(trans );

    }
    public int getTransactonId() {
        return transactonId;
    }

    public void setTransactonId(int transactonId) {
        this.transactonId = transactonId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Double sumPrice) {
        this.sumPrice = sumPrice;
    }
}
