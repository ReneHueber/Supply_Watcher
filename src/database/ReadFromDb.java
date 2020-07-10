package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Product;
import objects.StoredProduct;

import java.sql.*;
import java.util.ArrayList;

public class ReadFromDb {

    public static ObservableList<Product> getProducts(String sqlStmt){
        ObservableList<Product> products = FXCollections.observableArrayList();

        try (Connection con = Connect.connectDb()) {
            assert con != null;
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlStmt)){

                while (rs.next()){
                    int id = rs.getInt("id");
                    String barcode = rs.getString("barcode");
                    String name = rs.getString("name");
                    String brand = rs.getString("brand");
                    String category = rs.getString("category");
                    String place = rs.getString("place");
                    String unit = rs.getString("unit");
                    int capacity = rs.getInt("capacity");
                    float minAmount = rs.getFloat("minAmount");

                    products.add(new Product(id, barcode, name, brand, category, place, unit, capacity, minAmount));
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return products;
    }

    public static ObservableList<StoredProduct> getStoredProducts(String sqlStmt){
        ObservableList<StoredProduct> storedProducts = FXCollections.observableArrayList();

        try (Connection conn = Connect.connectDb()){
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlStmt)){

                while (rs.next()){
                    int id = rs.getInt("id");
                    int productId = rs.getInt("productId");
                    int leftCapacity = rs.getInt("leftCapacity");
                    String placeOpen = rs.getString("placeOpen");
                    Date openSince = rs.getDate("openSince");
                    int amountClosed = rs.getInt("amountClosed");
                    float amountOpen = rs.getFloat("amountOpen");

                    storedProducts.add(new StoredProduct(id, productId, leftCapacity, placeOpen,
                            openSince, amountClosed, amountOpen));
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return storedProducts;
    }
}
