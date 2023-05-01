package com.example.p4v2;


import javafx.scene.Node;

import java.sql.*;
import java.util.Objects;

public class Users {
    private int id;
    public static final int LogRound = 10;
    private String email;
    protected String password;
    public String firstName;
    public String lastName;
    private String name;
    private float balance;
    public Timestamp created_at;

    public Users(){
    }

    protected static boolean createUser(String email, String password, String firstName, String lastName) {
        boolean success = false;
        try {
            Connection con = ConnectionManager.getConnection();
            String checkQuery = "SELECT * FROM customer WHERE email = ?";
            PreparedStatement checkStatement = con.prepareStatement(checkQuery);
            checkStatement.setString(1, email);

            ResultSet result = checkStatement.executeQuery();

            if (result.next()) {
                
                success = false;
                
            } else if(password.length() < 10) {

                success = false;

            }else{
                
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
    public static Users login(String email, String password) {
        // declaring it out of the if statement to return it at the end
        Users currentUser = new Users();

        if (verifyPassword(email, password)) {
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

    public static void logout(Users currentUser) {
        // this method doesn work, and cannot
        // use this method like this:
        // currentUser = null;
        currentUser = null;
    }
    public void seeUserAccount() {
        System.out.println("Id: " + this.id);
        System.out.println("Email: " +  this.email);
        // may have to be protected somehow:
        System.out.println("Password" + this.password);
        System.out.println("First Name:" + this.firstName);
        System.out.println("Last Name: " + this.lastName);
        System.out.println("Balance:" + this.balance);
        System.out.println("Created at:" + this.created_at);
    }
    public static void deleteAccount(Users currentUser) {
        //TODO: Implement how to delete a useraccount
        Connection con = ConnectionManager.getConnection();
        PreparedStatement pstmt = null;
    
        try {
            // Prepare the SQL query to delete the account
            String sql = "DELETE FROM customer WHERE id = ?";
            pstmt = con.prepareStatement(sql);
    
            // Set the account id parameter
            pstmt.setInt(1, currentUser.id);
    
            // Execute the query and check the return value
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
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

    public void updateFirstName(String name) {
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

    // Testing
    public static void main(String[] args) throws Exception {
        String email = "casperbramm@hotmail.com";
        String password = "goodPassword";
        String firstName = "Casper";
        String lastName = "Bramm";

        createUser(email, password, firstName, lastName);
        
        Users myUser = Users.login(email, password);

        myUser.seeUserAccount();

        myUser.getName();

        String newEmail = "casperbramm@gmail.com";

        myUser.setEmail(newEmail);

        myUser.getBalance();

        myUser.deposit(100);

        myUser.requestPayout();

        // deleteAccount(myUser);

    }

}