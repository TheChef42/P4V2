package com.example.p4v2;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;

import static java.lang.Math.random;
import static java.lang.Math.subtractExact;

public class SendMail {
    public static Users currentUser;

    public void setUser(){
        currentUser = Main.getCurrentuser();
    }
    protected static void createKey(String email) throws SQLException {
        PreparedStatement st = null;
        Connection con = null;
        try {
            con = ConnectionManager.getConnection();
            Random random = new Random();
            int secretKey = random.nextInt(1000000,9999999);
            String query = "UPDATE customer SET secret_key = ? WHERE email = ?";

            st = con.prepareStatement(query);
            st.setInt(1, secretKey);
            st.setString(2, email);

            st.executeUpdate();
        } finally {
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static String getKey(String email) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT secret_key FROM customer WHERE email = ?;";
        try {
            st = con.prepareStatement(query);
            st.setString(1, email);
            rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString("secret_key");
            } else {
                throw new IllegalArgumentException("Customer not found");
            }
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            con.close();
        }
    }


  /*  public static boolean verifyKey(String email, String verificationCode) {
        Connection con = ConnectionManager.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT SECRRET_KEY FROM customer WHERE EMAIL=?";
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
    }*/


    public static void Send(String email) throws SQLException {
        final String messageContent = "Here is your key: " + getKey(email);
        final String from = "p4v2@outlook.dk";
        final String password = "Amped.selfservice69";
        final String to = email;
        final String subject = "verification key";


        // Set mail server properties.
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");

        // Get a session with authentication.
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Create a new message.
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(messageContent);

            // Send the message.
            Transport.send(message);
            System.out.println("Message sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

