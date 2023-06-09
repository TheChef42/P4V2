package com.example.p4v2;

import javafx.scene.Node;
import java.util.regex.*;

import javafx.scene.control.Button;

import java.sql.*;
import java.util.Objects;
public class Users {
    private int id;
    public static final int LogRound = 10;
    private String email;
    protected String password;
    public String firstName;
    public String lastName;
    private float balance;
    private double key;
    public Timestamp created_at;
    public String isAdmin;

    public Users(){}

    protected static boolean createUser(String email, String password, String firstName, String lastName) {
        boolean success = false;
        try {
            Connection con = ConnectionManager.getConnection();
            String checkQuery = "SELECT * FROM customer WHERE email = ?";
            String checkAdmin = "SELECT * FROM admins";
            PreparedStatement checkStatement = con.prepareStatement(checkQuery);
            PreparedStatement checkStateAdmin = con.prepareStatement(checkAdmin);
            checkStatement.setString(1, email);

            ResultSet result = checkStatement.executeQuery();
            ResultSet resultAdmin = checkStateAdmin.executeQuery();

            if (result.next()) {
                success = false;
            } else if (!resultAdmin.next()) {
                String qry = "INSERT INTO admins (username, created_by) values(?,1)";
                PreparedStatement st = con.prepareStatement(qry);
                st.setString(1, email);
                st.executeUpdate();

                String qry1 = "INSERT INTO customer (EMAIL, PASSWORD, FIRSTNAME, LASTNAME) values(?,?,?,?)";
                PreparedStatement st1 = con.prepareStatement(qry1);
                st1.setString(1, email);
                String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
                st1.setString(2, hashed);
                st1.setString(3, firstName);
                st1.setString(4, lastName);
                st1.executeUpdate();
                success = true;
            } else {
                String qry = "INSERT INTO customer (EMAIL, PASSWORD, FIRSTNAME, LASTNAME) values(?,?,?,?)";
                PreparedStatement st = con.prepareStatement(qry);
                st.setString(1, email);
                String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
                st.setString(2, hashed);
                st.setString(3, firstName);
                st.setString(4, lastName);
                st.executeUpdate();
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
    public static Users login(String email, String password) throws SQLException {
        // declaring it out of the if statement to return it at the end
        Users currentUser = new Users();


        if (verifyPassword(email, password)) {
            SendMail.createKey(email);
            SendMail.Send(email);
            Connection con = ConnectionManager.getConnection();
            PreparedStatement st = null;
            ResultSet rs = null;
            String query = "SELECT * FROM customer WHERE EMAIL=?";
            try {
                st = con.prepareStatement(query);
                st.setString(1, email);
                rs = st.executeQuery();
    
                if (rs.next()) {
                    currentUser.id = rs.getInt("id");
                    currentUser.email = email;
                    currentUser.password = password;
                    currentUser.firstName = rs.getString("firstname");
                    currentUser.lastName = rs.getString("lastname");
                    currentUser.balance = rs.getFloat("balance");
                    currentUser.created_at = rs.getTimestamp("created_at");
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
            currentUser = null;
            System.out.println("Access denied!");
        }
        return currentUser;
    }
    public static boolean emailRegex(String email){
        if(email.matches("^[a-zA-Z0-9_]+@[a-zA-Z_]+.aau.dk$")){
             return true;
        } else {
            return false;
        }
    }
    public static boolean verifyKey(String email, String verificationCode) {
        Connection con = ConnectionManager.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT SECRET_KEY FROM customer WHERE EMAIL=?";
        try {
            st = con.prepareStatement(query);
            st.setString(1, email);
            rs = st.executeQuery();
            if (rs.next() && Objects.equals(rs.getString("SECRET_KEY"), verificationCode)) {
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

    public static boolean verifyPassword(String email, String password) {
        Connection con = ConnectionManager.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT PASSWORD FROM customer WHERE EMAIL=?";
        try {
            st = con.prepareStatement(query);
            st.setString(1, email);
            rs = st.executeQuery();
            rs.next();
            String tempPass = rs.getString("PASSWORD");

            if (BCrypt.checkpw(password, tempPass)) {
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
    public static void deleteAccount(Users currentUser) {
        //TODO: Implement how to delete a useraccount
        Connection con = ConnectionManager.getConnection();
        PreparedStatement pstmt = null;
    
        try {
            // Prepare the SQL query to delete the account
            String sql = "UPDATE customer SET email=?, password=?, firstname=?, lastname=?, balance=?, secret_key=? WHERE id=?";
            pstmt = con.prepareStatement(sql);
    
            // Set the account id parameter
            pstmt.setString(1, "DELETED");
            pstmt.setString(2, null);
            pstmt.setString(3, null);
            pstmt.setString(4, null);
            pstmt.setString(5, null);
            pstmt.setString(6, null);
            pstmt.setInt(7, currentUser.id);
    
            // Execute the query and check the return value
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                String sqlAdmin = "UPDATE admins SET username = 'DELETED' WHERE id = ?";

                PreparedStatement adminQuery = con.prepareStatement(sqlAdmin);

                adminQuery.setInt(1, currentUser.id);
                rowsAffected = adminQuery.executeUpdate();
                System.out.println("Account deleted successfully");
                //end that users session as well
                currentUser = null;
            } else {
                System.out.println("Account not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the prepared statement and database connection
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    //updates new name in database (AdminUserOverview):
    public void updateName(String newName){
        String[] split = newName.split("\\s+");

        String newFirstName = "";

        for(int i = 0; i < split.length - 1 ; i++){
            if(i != 0){
                newFirstName += " ";
            }
            newFirstName += split[i];
        }
        String newLastName = split[split.length - 1];

        this.firstName = newFirstName;
        this.lastName = newLastName;
        Connection con = ConnectionManager.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "UPDATE customer SET firstname=?, lastname=? WHERE id=?";
        try {
            st = con.prepareStatement(query);
            st.setString(1, newFirstName);
            st.setString(2, newLastName);
            st.setInt(3, this.id);
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

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String name) {
        this.lastName = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int thisid) {
        this.id = thisid;
    }

    public String getEmail() {
        return this.email;
    }

    public void setObjectEmail(String mail) {
        this.email = mail;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
        Connection con = ConnectionManager.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "UPDATE customer SET email=? WHERE id=?";
        try {
            st = con.prepareStatement(query);
            st.setString(1, newEmail);
            st.setInt(2, this.id);
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

    private String getPassword(){
        return this.password;
    }

    public void setPassword(String passwordString){
        this.password = passwordString;
    }

    public float getBalance() {
        return this.balance;
    }

    public void setBalance(float money) {
        this.balance = money;
    }

    public Timestamp getCreatedAt() {
        return this.created_at;
    }

    public void setCreatedAt(Timestamp time) {
        this.created_at = time;
    }

    public Double getKey() {
        return this.key;
    }

    public void setKey(Double key) {
        this.key = key;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void deposit(float money) {
        this.balance += money;
        Connection con = ConnectionManager.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "UPDATE customer SET balance=? WHERE id=?";
        try {
            st = con.prepareStatement(query);
            st.setFloat(1, this.balance);
            st.setInt(2, this.id);
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

    public void requestPayout() {
        System.out.print("You have requestet to get " + this.balance + " money payed back");
        // something something mobilepay and money
    }
}