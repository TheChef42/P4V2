package com.example.p4v2;
import java.sql.*;
import java.util.ArrayList;

public class Admin {

    private int id;
    private String username;
    protected String password;
    public String firstName;
    public String lastName;
    public Timestamp created_at;

    public Admin(){}

    public void editUser(){
        //TODO: how to edit admin
    }

    public static ArrayList<Users> getUsers() {
        ArrayList<Users> userList = new ArrayList<>();
    
        try {
            Connection con = ConnectionManager.getConnection();
            String qry = "SELECT * FROM customer";
            PreparedStatement st = con.prepareStatement(qry);
            ResultSet rs = st.executeQuery();
    
            while(rs.next()){
                if(rs.getString("email").equals("DELETED")){
                    continue;
                }
                Users user = new Users();
                user.setId(rs.getInt("id"));
                user.setObjectEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setBalance(rs.getFloat("balance"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                userList.add(user);
            }
            System.out.println(userList.get(0).getId());
    
        } catch(SQLException e){
            e.printStackTrace();
        }
    
        return userList;
    }
    

    public static Admin login(String username, String password){
        // declaring it out of the if statement to return it at the end
        if(!verifyAdmin(username)){
            return null;
        }
        Admin currentAdmin = new Admin();
        if (Users.verifyPassword(username, password)) {
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

    public static boolean verifyAdmin(String username) {
        Connection con = ConnectionManager.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT id FROM admins WHERE username=?";
        try {
            st = con.prepareStatement(query);
            st.setString(1, username);
            rs = st.executeQuery();
            if (rs.next()) {
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

    public void createProduct(Products product){
        int productStock = product.getStock();

        try {
            Connection con = ConnectionManager.getConnection();
            String qry = "INSERT INTO products (product, price, stock) VALUES (?,?,?)";
            PreparedStatement st = con.prepareStatement(qry);
            st.setString(1,product.name);
            st.setFloat(2, product.price);
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
                st.setInt(4, product.getid());
                int numRowsAffected = st.executeUpdate();
                if (numRowsAffected == 0) {
                    System.out.println("Product with ID " + product.getid() + " does not exist.");
                } else {
                    System.out.println("Product with ID " + product.getid() + " updated successfully.");
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

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public int getId() {
        return id;
    }

    public boolean makeAdmin(Users user) {
        boolean success = false;
        try {
            Connection con = ConnectionManager.getConnection();
                String qry = "INSERT INTO admins (id, username, created_by) values(?,?,?)";
                PreparedStatement st = con.prepareStatement(qry);
                st.setInt(1, user.getId());
                st.setString(2, user.getEmail());
                st.setInt(3, this.getId());
                st.executeUpdate();
                success = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}