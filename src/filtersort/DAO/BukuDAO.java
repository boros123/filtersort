/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filtersort.DAO;

import filtersort.Model.Buku;
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
public class BukuDAO {
    public static String filterTanggalDari = null;     // Filter tanggal mulai pengadaan
    public static String filterTanggalSampai = null;   // Filter tanggal akhir pengadaan
    public static String filterJudul = null;     // Filter judul buku (partial match)
    public static String filterKategori = null; 
      public static String sortingOption = "";
    
public static ObservableList<Buku> getBuku() {
    ObservableList<Buku> bukuList = FXCollections.observableArrayList();

    String sql = "SELECT buku.*, kategori_buku.nama_kategori FROM buku " +
                 "LEFT JOIN kategori_buku " +
                 "ON buku.kode_kategori = kategori_buku.kode_kategori " +
                 "WHERE 1=1";

    if (filterTanggalDari != null && filterTanggalSampai != null
    && !filterTanggalDari.isEmpty() && !filterTanggalSampai.isEmpty()) {
    sql += " AND buku.tanggal BETWEEN '"
         + filterTanggalDari + "' AND '" + filterTanggalSampai + "' ";
    }


    if (filterJudul != null && !filterJudul.isEmpty() ) {
        sql += " AND (buku.judul LIKE '%" + filterJudul + "%' OR buku.pengarang LIKE '%" + filterJudul + "%')";

    }

    if (filterKategori != null && !filterKategori.isEmpty()) {
        sql += " AND buku.kode_kategori = '" + filterKategori + "'";
    }
    
     if (!sortingOption.isEmpty()) {
            switch (sortingOption) {
                case "Judul A-Z":
                    sql += " ORDER BY buku.judul ASC"; // Sort judul A-Z
                    break;
                case "Judul Z-A":
                    sql += " ORDER BY buku.judul DESC"; // Sort judul Z-A
                    break;
                case "Pengadaan Terbaru":
                    // Sort tanggal terbaru
                    sql += " ORDER BY buku.tanggal DESC";
                    break;
                case "Pengadaan Lama":
                    // Sort tanggal terlama
                    sql += " ORDER BY buku.tanggal ASC";
                    break;
            }
        }

    try {
        Connection koneksi = DBConnection.getConnection();
        Statement stmt = koneksi.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String kd_buku = rs.getString("kd_buku");
            String judul = rs.getString("judul");
            String kode_kategori = rs.getString("kode_kategori");
            String nama_kategori = rs.getString("nama_kategori");
            String pengarang = rs.getString("pengarang");
            String penerbit = rs.getString("penerbit");
            String tahun_terbit = rs.getString("tahun_terbit");
            String edisi = rs.getString("edisi");
            String tanggal = rs.getString("tanggal");

            bukuList.add(new Buku(kd_buku, judul, kode_kategori ,nama_kategori,pengarang, penerbit, tahun_terbit, edisi, tanggal));
        }

        rs.close();
        stmt.close();
        koneksi.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return bukuList;
}
//Generate kode baru secara berurutan
    public static String generateKodeBuku() {
    String kodeBaru = "B00001"; // default jika tabel masih kosong
    String query = "SELECT kd_buku FROM buku ORDER BY kd_buku DESC LIMIT 1";

    try {
        Connection koneksi = DBConnection.getConnection();
        Statement stmt = koneksi.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        if (rs.next()) {
//            cek kode  buku yang paling terakhir 
            String lastKode = rs.getString("kd_buku");
//            menambahkan satu angka berdasarkan hasil cek kode buku terakhir
            int nomor = Integer.parseInt(lastKode.substring(1)) + 1;
//            format untuk jika digit 0 di depan kurang,panjang digit 5, dan untuk tipe nya integer  
            kodeBaru = String.format("B%05d", nomor); // hasil "B00013"
        }

        rs.close();
        stmt.close();
        koneksi.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return kodeBaru;
}
    public static void addBuku(Buku buku) {
        String query = "INSERT INTO buku (kd_buku,judul,pengarang,penerbit,tahun_terbit,edisi,kode_kategori,tanggal) VALUES (?,?,?,?,?,?,?,?)" 
                        + "ON DUPLICATE KEY UPDATE kd_buku = kd_buku";
                ;
    
    try {
        Connection koneksi = DBConnection.getConnection();
        PreparedStatement smt = koneksi.prepareStatement(query);
        
        smt.setString(1, buku.getKd_buku());
          smt.setString(2, buku.getJudul());
        smt.setString(3, buku.getPengarang());
          smt.setString(4, buku.getPenerbit());
            smt.setString(5, buku.getTahun_terbit());
              smt.setString(6, buku.getEdisi());
                  smt.setString(7, buku.getKode_kategori());
                      smt.setString(8, buku.getTanggal());
       
        
        smt.executeUpdate();
        
        smt.close();
        koneksi.close();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    
    public static void updateBuku(Buku buku) {
    String query = "UPDATE buku SET judul = ?,pengarang = ?,penerbit = ?,tahun_terbit = ?,edisi = ?,kode_kategori = ?,tanggal = ? WHERE kd_buku = ?";

    try (Connection koneksi = DBConnection.getConnection()) {
        PreparedStatement mst = koneksi.prepareStatement(query);
       
        mst.setString(1, buku.getJudul());
         mst.setString(2, buku.getPengarang());
          mst.setString(3, buku.getPenerbit());
           mst.setString(4, buku.getTahun_terbit());
            mst.setString(5, buku.getEdisi());
             mst.setString(6, buku.getKode_kategori());
             mst.setString(7, buku.getTanggal());
         mst.setString(8, buku.getKd_buku());

        mst.executeUpdate();
        mst.close();
        koneksi.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public static void deleteBuku(String kd_buku) {
    String query = "DELETE FROM buku WHERE kd_buku = ?";

    try (Connection koneksi = DBConnection.getConnection()) {
        PreparedStatement mst = koneksi.prepareStatement(query);
        mst.setString(1, kd_buku);

        mst.executeUpdate();
        mst.close();
        koneksi.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
 }
}
