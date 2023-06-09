package com.example.p4v2;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Transaction {
    private int id;
    //private Time date;
    private ArrayList<Products> products = new ArrayList<Products>();
    private int userId;
    public final ArrayList<Products> basket = new ArrayList<Products>();
    private ArrayList<TransactionDetails> details = new ArrayList<TransactionDetails>();


    public Transaction() {
        products = this.setProducts();
    }
    public Transaction(int transactionId) {
        setDetails(transactionId);
    }
    public ArrayList<Products> getProducts() {
        return products;
    }

    public ArrayList<Products> setProducts() {
        //TODO: implement how to set the products
        try {
            Connection con = ConnectionManager.getConnection();
            String qry = "SELECT id FROM products WHERE NOT stock = -1";
            PreparedStatement st = con.prepareStatement(qry);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Products product = new Products(rs.getInt("id"));
                products.add(product);
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return products;
    }

    public ArrayList<TransactionDetails> getDetails() {
        return details;
    }

    public void setDetails(int transactionId) {
        try {
            Connection con = ConnectionManager.getConnection();
            String qry = "SELECT id FROM transactions_info WHERE transaction_id = ?";
            PreparedStatement st = con.prepareStatement(qry);
            st.setInt(1, transactionId);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                TransactionDetails transactionDetails = new TransactionDetails(rs.getInt("id"));
                details.add(transactionDetails);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public String storeTransaction(ObservableList<Products> basket){
        //TODO: implement to store the transaction in the database
        String success = "allGood";
        boolean inStock = true;
        float sum = 0;
        for (Products products1: basket){
            sum += (products1.price * products1.selectAmount);
            if(products1.getStock() < products1.selectAmount){
                inStock = false;
            }
        }
        if (sum <= Main.getCurrentuser().getBalance()){
            if(inStock){
                try{
                    Connection con = ConnectionManager.getConnection();
                    String transactions_qry = "INSERT INTO transactions (sum, customer_id) values(?,?)";
                    PreparedStatement st = con.prepareStatement(transactions_qry, Statement.RETURN_GENERATED_KEYS);
                    st.setInt(2, Main.getCurrentuser().getId());
                    st.setFloat(1,sum);
                    st.executeUpdate();
                    ResultSet rs = st.getGeneratedKeys();
                    rs.next();
                    int newId = rs.getInt(1);
                    String transactionsInfoQry = "INSERT INTO transactions_info (transaction_id, productId, amount, price, sum_price) values(?,?,?,?,?)";
                    PreparedStatement st1 = con.prepareStatement(transactionsInfoQry);
                    for (Products products: basket) {
                        st1.setInt(1, newId);
                        st1.setInt(2, products.getid());
                        st1.setInt(3, products.selectAmount);
                        st1.setFloat(4, products.price);
                        st1.setFloat(5, (products.price * products.selectAmount));
                        st1.addBatch();
                        int newstock = products.getStock() - products.selectAmount;
                        products.setStock(newstock);
                    }
                    st1.executeBatch();
                    Main.getCurrentuser().deposit(-sum);
                    return success;
                }catch(SQLException e){
                    e.printStackTrace();
                    return success;
                }
            }else{
                success = "stockIssue";
                return success;
            }
        }else{
            success = "insufficientFunds";
            return success;

        }
    }

    public void deleteProductFromList(Products product){
        //TODO: implement to delete a product from the list
        if (basket.contains(product)) {
            basket.remove(product);
            System.out.println("Product '" + product + "' removed from basket.");
        } else {
            System.out.println("Product '" + product + "' not found in basket.");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<Products> getBasket() {
        return basket;
    }
}
