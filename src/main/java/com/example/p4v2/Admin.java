package com.example.p4v2;
import java.util.Objects;
import java.sql.*;

public class Admin {

    private int id;
    private String username;
    protected String password;
    public String firstName;
    public String lastName;
    public Timestamp created_at;

    //public Admin(){}

    public void editUser(){
        //TODO: how to edit admin
    }

    public static Admin login(String username, String password){
        // declaring it out of the if statement to return it at the end
        Admin currentAdmin = new Admin();
        if (verifyPasswordAdmin(username, password)) {
            Connection con = ConnectionManager.getConnection();
            PreparedStatement st = null;
            ResultSet rs = null;
            String query = "SELECT * FROM customer WHERE EMAIL=?";
    
            try {
                st = con.prepareStatement(query);
                st.setString(1, username);
                rs = st.executeQuery();
    
                if (rs.next()) {
                    currentAdmin.id = rs.getInt("id");
                    currentAdmin.username = username;
                    currentAdmin.password = password;
                    currentAdmin.firstName = rs.getString("firstname");
                    currentAdmin.lastName = rs.getString("lastname");
                    //currentUser.balance = rs.getFloat("balance");
                    currentAdmin.created_at = rs.getTimestamp("created_at");
                }
    
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                try{
                    // closing input objects
                if (rs != null){
                    rs.close();
                }
                if (st != null){
                    st.close();
                }
                if (con != null){
                    con.close();
                }

                } catch (SQLException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        } else {
            currentAdmin = null;
            System.out.println("Access denied!");
        }
        return currentAdmin;
    }

    public static boolean verifyPasswordAdmin(String username, String password) {
        Connection con = ConnectionManager.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT PASSWORD FROM admins WHERE username=?";
        try {
            st = con.prepareStatement(query);
            st.setString(1, username);
            rs = st.executeQuery();
            if (rs.next() && Objects.equals(rs.getString("PASSWORD"), password)) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
            // closing the connection:
        } finally {
            try {
                // closing input objects
                if (rs != null){
                    rs.close();
                }
                if (st != null){
                    st.close();
                }
                if (con != null){
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void viewUsers(){
        try {
            Connection con = ConnectionManager.getConnection();
            String qry = "SELECT * FROM customer";
            PreparedStatement st = con.prepareStatement(qry);
            ResultSet rs = st.executeQuery();
            System.out.print("id" + "\t\t");
            System.out.print("email" + "\t\t");
            System.out.print("password" + "\t\t");
            System.out.print(("firstname") + "\t\t");
            System.out.print(("lastname") + "\t\t");
            System.out.print(("balance") + "\t\t");
            System.out.println(("created_at"));
            while(rs.next()){
                System.out.print(rs.getInt("id") + "\t\t");
                System.out.print(rs.getString("email") + "\t\t");
                System.out.print(rs.getString("password") + "\t\t");
                System.out.print(rs.getString("firstname") + "\t\t");
                System.out.print(rs.getString("lastname") + "\t\t");
                System.out.print(rs.getFloat("balance") + "\t\t");
                System.out.println(rs.getTimestamp("created_at"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void deleteUser(int id){
        try{
            Connection con = ConnectionManager.getConnection();
            String qry = "DELETE FROM customer WHERE id = ?";
            PreparedStatement st = con.prepareStatement(qry);
            st.setInt(1,id);
            st.executeUpdate();
    } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int viewProducts(){
        //TODO: implement how to view and select a product
        int productID = 0;
        return productID;
    }

    public void createProduct(String productName, float productPrice, int productStock){
        try {
            Connection con = ConnectionManager.getConnection();
            String qry = "INSERT INTO products (product, price, stock) VALUES (?,?,?)";
            PreparedStatement st = con.prepareStatement(qry);
            st.setString(1,productName);
            st.setFloat(2, productPrice);
            st.setInt(3, productStock);
            st.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void updateProduct(Products product, String newName, float newPrice, int newStock) {
        //TODO: implement updateProduct
            try {
                Connection con = ConnectionManager.getConnection();
                String qry = "UPDATE products SET product = ?, price = ?, stock = ? WHERE id = ?";
                PreparedStatement st = con.prepareStatement(qry);
                st.setString(1, newName);
                st.setFloat(2, newPrice);
                st.setInt(3, newStock);
                st.setInt(4, product.getProductID());
                int numRowsAffected = st.executeUpdate();
                if (numRowsAffected == 0) {
                    System.out.println("Product with ID " + product.getProductID() + " does not exist.");
                } else {
                    System.out.println("Product with ID " + product.getProductID() + " updated successfully.");
                    // Update the object's attributes if the database update was successful
                    product.setName(newName);
                    product.setPrice(newPrice);
                    product.setStock(newStock);
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    public void removeProduct(int productId) {
        //TODO: implement removeProduct
            Connection con = null;
            PreparedStatement pstmt = null;
        
            try {
                con = ConnectionManager.getConnection();
                pstmt = con.prepareStatement("DELETE FROM products WHERE id = ?");
                pstmt.setInt(1, productId);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected == 0) {
                    System.out.println("No product found with ID " + productId);
                } else {
                    System.out.println(rowsAffected + " product(s) removed.");
                }
            } catch (SQLException ex) {
                System.out.println("Error removing product: " + ex.getMessage());
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    System.out.println("Error closing connection: " + ex.getMessage());
                }
            }
        }
        public static void main(String[] args) {
            Admin admin = new Admin();
            admin.viewUsers();
            admin.removeProduct(2);
            admin.viewProducts();
            Products product = new Products("product name", 9.99f, 10);
            admin.updateProduct(product, "new product name", 19.99f, 20);
            admin.viewProducts();
            admin.createProduct("new product", 14.99f, 30);
            admin.viewProducts();
        }

        public String getName() {
            return this.firstName + " " + this.lastName;
        }

        public int getId() {
            return id;
        }
    }        
