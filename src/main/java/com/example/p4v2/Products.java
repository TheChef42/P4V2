package com.example.p4v2;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


public class Products {
    private int productID;
    public String name;
    public float price;
    private int stock;
    public int selectAmount = 1;
    public float sum;

    public Products() {
    }


    public Products(int productID) {
        try {
            Connection con = ConnectionManager.getConnection();
            String qry = "SELECT * FROM products WHERE id = ?";
            PreparedStatement st = con.prepareStatement(qry);
            st.setInt(1, productID);
            ResultSet rs = st.executeQuery();
            rs.next();
            this.productID = productID;
            this.name = rs.getString("product");
            this.price = rs.getFloat("price");
            this.stock = rs.getInt("stock");
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public String toString(){
        return this.productID + "\t" + this.name + "\t" + this.price + "\t" + this.stock + "\n";
    }

    public int getProductID() {
        return this.productID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int newStock) {
        this.stock = newStock;
        Connection con = ConnectionManager.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "UPDATE products SET stock=? WHERE id=?";
        try {
            st = con.prepareStatement(query);
            st.setFloat(1, newStock);
            st.setInt(2, this.productID);
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
    public void setSelectAmount(int selectAmount) {
        this.selectAmount = selectAmount;
    }

    public float getSum() {
        sum = this.price*this.selectAmount;
        return sum;
    }
    public int getSelectAmount() {
        return selectAmount;
    }
}