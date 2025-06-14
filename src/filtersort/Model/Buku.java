/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filtersort.Model;

/**
 *
 * @author Lenovo
 */
public class Buku {
     private String kd_buku;  
    private String judul;
    private String pengarang;
    private String penerbit;
    private String tahun_terbit;
    private String edisi;
    private String kode_kategori;
    private String tanggal;
    private String nama_kategori;


    public Buku(String kd_buku,String judul,String pengarang,String penerbit,String tahun_terbit,String edisi,String kode_kategori,String nama_kategori,String tanggal) {
        this.kd_buku=kd_buku;
        this.judul = judul;  
         this.pengarang = pengarang;  
          this.penerbit = penerbit;  
           this.tahun_terbit = tahun_terbit; 
            this.edisi = edisi;
            this.kode_kategori=kode_kategori;
            this.nama_kategori=nama_kategori;
            this.tanggal=tanggal;
    }
    
    public Buku(String kd_buku,String judul,String pengarang,String penerbit,String tahun_terbit,String edisi,String kode_kategori,String tanggal) {
        this.kd_buku=kd_buku;
        this.judul = judul;  
         this.pengarang = pengarang;  
          this.penerbit = penerbit;  
           this.tahun_terbit = tahun_terbit; 
            this.edisi = edisi;
            this.kode_kategori=kode_kategori;
            this.tanggal=tanggal;
           
    }

    public String getKd_buku() {
        return kd_buku;
    }

    public void setKd_buku(String kd_buku) {
        this.kd_buku = kd_buku;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getTahun_terbit() {
        return tahun_terbit;
    }

    public void setTahun_terbit(String tahun_terbit) {
        this.tahun_terbit = tahun_terbit;
    }

    public String getEdisi() {
        return edisi;
    }

    public void setEdisi(String edisi) {
        this.edisi = edisi;
    }

    public String getKode_kategori() {
        return kode_kategori;
    }

    public void setKode_kategori(String kode_kategori) {
        this.kode_kategori = kode_kategori;
    }
 public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }
    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

}
