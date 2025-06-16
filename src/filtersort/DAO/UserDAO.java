/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filtersort.DAO;

import filtersort.Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Lenovo
 */
public class UserDAO {
    public User GetAccount(String username, String password) {

        String query = "SELECT * FROM users WHERE username = ? AND password = md5(?)";

        try {
            // Membuat koneksi ke database
            Connection koneksi = DBConnection.getConnection();

            // Membuat prepared statement
            PreparedStatement pstmt = koneksi.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            // Menjalankan query
            ResultSet rs = pstmt.executeQuery();

            // Menambahkan data ke User kalau ditemukan
            if (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("fullname"),
                    rs.getString("role")
                );

                // Menutup koneksi
                rs.close();
                pstmt.close();
                koneksi.close();

                return user;
            }

            // Menutup koneksi
            rs.close();
            pstmt.close();
            koneksi.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
