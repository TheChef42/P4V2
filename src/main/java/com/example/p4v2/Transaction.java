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
    public Transaction() {
        products = this.setProducts();
    }
    public Transaction(int transactionId) {
        setDetails(transactionId);
    }
    public ArrayList<Products> getProducts() {
        return products;
    }
    private ArrayList<TransactionDetails> details = new ArrayList<TransactionDetails>();

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

    public ArrayList<Products> setProducts() {
        //TODO: implement how to set the products
        try {
            Connection con = ConnectionManager.getConnection();
            String qry = "SELECT id FROM products";
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

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int choice = 0;
        Transaction currentTransaction = new Transaction();
        while(true) {
            System.out.println("Available products: ");
            //https://www.callicoder.com/java-arraylist/
            currentTransaction.products.forEach(products -> {
                System.out.println(products.getProductID() + "\t" + products.name + "\t" + products.price + "\t" + products.getStock());
            });
            System.out.println("\n\n");
            System.out.println("Current basket:");
            if (currentTransaction.basket.isEmpty())
                System.out.println("Basket is empty");
            else {
                currentTransaction.basket.forEach(products -> {
                    System.out.println(products.name + "\t" + products.price + "\t" + products.selectAmount + "\t" +(products.price * products.selectAmount));
                });
            }
            System.out.print("\n Add product to basket (select id): ");
            choice = scan.nextInt();
            if (choice == -1){
                //currentTransaction.storeTransaction(1);
                break;
            }
            currentTransaction.addProductToTransaction(choice);
        }
    }
    public void addProductToTransaction(int productID){
        //TODO: implement to add product to transaction
        if(searchBasketID(productID) != null){
            Products product = searchBasketID(productID);
            product.selectAmount++;
        } else {
        Products product = new Products(productID, 1);
        basket.add(product);
        }
    }
    private Products searchBasketID (int parameterValue){
        Products product = null;
        for (Products products1: basket) {
            if (products1.getProductID() == parameterValue)
                product = products1;
        }
        return product;
    }

    public boolean storeTransaction(Users currentUser, ObservableList<Products> basket){
        //TODO: implement to store the transaction in the database
        boolean success = false;
        float sum = 0;
            for (Products products1: basket){
                sum += (products1.price * products1.selectAmount);
            }

        if (sum <= currentUser.getBalance()){
        try{
            Connection con = ConnectionManager.getConnection();
            String transactions_qry = "INSERT INTO transactions (sum, customer) values(?,?)";
            PreparedStatement st = con.prepareStatement(transactions_qry, Statement.RETURN_GENERATED_KEYS);
            st.setInt(2, currentUser.getId());
            st.setFloat(1,sum);
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            int newId = rs.getInt(1);
            String transactionsInfoQry = "INSERT INTO transactions_info (transaction_id, product, amount, price, sum_price) values(?,?,?,?,?)";
            PreparedStatement st1 = con.prepareStatement(transactionsInfoQry);
            for (Products products: basket) {
                st1.setInt(1, newId);
                st1.setInt(2, products.getProductID());
                st1.setInt(3, products.selectAmount);
                st1.setFloat(4, products.price);
                st1.setFloat(5, (products.price * products.selectAmount));
                st1.addBatch();
            }
            st1.executeBatch();
            currentUser.deposit(-sum);
            success = true;
            return success;

        }catch(SQLException e){
            e.printStackTrace();
            return success;
        }
    }else{
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
    public void checkOut(){
        //TODO: implement the checkout
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProducts(ArrayList<Products> products) {
        this.products = products;
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
