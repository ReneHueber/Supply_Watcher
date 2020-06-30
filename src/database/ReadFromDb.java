package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReadFromDb {

    public static ObservableList<Product> getProducts(String sqlStmt){
        ObservableList<Product> products = FXCollections.observableArrayList();

        try (Connection con = Connect.connectDb()) {
            assert con != null;
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlStmt)){

                while (rs.next()){
                    String barcode = rs.getString("barcode");
                    String name = rs.getString("name");
                    String brand = rs.getString("brand");
                    String category = rs.getString("category");
                    String place = rs.getString("place");
                    String unit = rs.getString("unit");
                    int capacity = rs.getInt("capacity");
                    float minAmount = rs.getFloat("minAmount");

                    products.add(new Product(barcode, name, brand, category, place, unit, capacity, minAmount));
                }
                return products;
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
