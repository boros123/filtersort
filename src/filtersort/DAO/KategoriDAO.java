/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filtersort.DAO;

import filtersort.Model.Kategori;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Lenovo
 */
public class KategoriDAO {
       public static ObservableList<Kategori> getKategori() {
        // ObservableList untuk menyimpan data user
        ObservableList<Kategori> kategoriList = FXCollections.observableArrayList();
        String query = "select * from kategori_buku";

        try {
            // Membuat koneksi ke database
            Connection koneksi = DBConnection.getConnection();
            // Membuat statement
            Statement stmt = koneksi.createStatement();
            // Query untuk mengambil data user
            ResultSet rs = stmt.executeQuery(query);
            
             // Menambahkan data ke ObservableList
              while (rs.next()) {
            String kode_kategori = rs.getString("kode_kategori");  
            String nama_kategori = rs.getString("nama_kategori");  
            

            kategoriList.add(new Kategori(kode_kategori,nama_kategori));
        }

            // Menutup koneksi
            rs.close();
            stmt.close();
            koneksi.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        
     
        return kategoriList;
    }
    
    public static void addKategori(Kategori kategori) {
        String query = "INSERT INTO organisasi (nama,jenis) VALUES (?,?)";
    
    try {
        Connection koneksi = DBConnection.getConnection();
        PreparedStatement smt = koneksi.prepareStatement(query);
        
        smt.setString(1, kategori.getKode_kategori());
        smt.setString(2, kategori.getNama_kategori());
       
        
        smt.executeUpdate();
        
        smt.close();
        koneksi.close();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    
      public static void updateKategori(Kategori kategori) {
    String query = "UPDATE organisasi SET nama = ?, jenis = ? WHERE id = ?";

    try (Connection koneksi = DBConnection.getConnection()) {
        PreparedStatement mst = koneksi.prepareStatement(query);
        mst.setString(1, kategori.getNama_kategori()); 
        mst.setString(2, kategori.getKode_kategori()); 

        mst.executeUpdate();
        mst.close();
        koneksi.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public static void deleteOrganisasi(String nama) {
    String query = "DELETE FROM organisasi WHERE nama = ?";

    try (Connection koneksi = DBConnection.getConnection()) {
        PreparedStatement mst = koneksi.prepareStatement(query);
        mst.setString(1, nama);

        mst.executeUpdate();
        mst.close();
        koneksi.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
