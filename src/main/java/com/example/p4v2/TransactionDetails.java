package com.example.p4v2;

import java.sql.*;

public class TransactionDetails {
    private int id;

    private Timestamp date;
    private int transactonId;
    private String productName;
    private int amount;
    private Double price;
    private Double sumPrice;
    public Timestamp getDate() {
        return date;
    }

    public TransactionDetails(int id){
        this.id = id;
        try {
            Connection con = ConnectionManager.getConnection();
            String qry = "SELECT * FROM transactions_info INNER JOIN products ON transactions_info.productId = products.id WHERE transactions_info.id = ?";
            PreparedStatement st = con.prepareStatement(qry);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                productName = rs.getString("product");
                date = rs.getTimestamp("created_at");
                amount = rs.getInt("amount");
                price = rs.getDouble("price");
                sumPrice = rs.getDouble("sum_price");
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
