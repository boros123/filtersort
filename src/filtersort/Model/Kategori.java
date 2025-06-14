/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filtersort.Model;

/**
 *
 * @author Lenovo
 */
public class Kategori {
     private String kode_kategori;  
    private String nama_kategori;



    public Kategori(String kode_kategori,String nama_kategori) {
        this.kode_kategori = kode_kategori; 
        this.nama_kategori=nama_kategori;
    }

     public Kategori(String nama_kategori) {
        this.nama_kategori=nama_kategori;
    }
//    

    public String getKode_kategori() {
        return kode_kategori;
    }


    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

 
    
    
    @Override
 public String toString() {
 return nama_kategori; }
}
