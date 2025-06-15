/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package filtersort.Controller;

import filtersort.DAO.BukuDAO;
import filtersort.DAO.KategoriDAO;
import filtersort.Model.Buku;
import filtersort.Model.Kategori;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Lenovo
 */
public class MainController implements Initializable {
    
  private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
     
   private void showAlertTrue(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
     
             private ButtonType showAlertConfirm(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
 
        alert.getButtonTypes().setAll(ButtonType.OK,ButtonType.CANCEL);
      
        return  alert.showAndWait().orElse(ButtonType.CANCEL);
    }
             
             
        private ButtonType showAlertUpdate(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
 
        alert.getButtonTypes().setAll(ButtonType.OK,ButtonType.CANCEL);
      
        return  alert.showAndWait().orElse(ButtonType.CANCEL);
    }
   

    
     @FXML
    private TableView<Buku> Tblbuku;
  
    @FXML
    private TableColumn<Buku, String> colKdBuku;

    @FXML
    private TableColumn<Buku, String> colJudul;
    
    @FXML
    private TableColumn<Buku, String> colPengarang;

    @FXML
    private TableColumn<Buku, String> colPenerbit;
    
    @FXML
    private TableColumn<Buku, String> colTahunterbit;

    @FXML
    private TableColumn<Buku, String> colEdisi;
    
    @FXML
    private TableColumn<Buku, String> colKdKategori;
    
    @FXML
    private TableColumn<Buku, String> colNamaKategori;
    
    @FXML
    private TableColumn<Buku, String> colTgl;
//    Button
    @FXML
    private Button btnCari;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnOpen;
    @FXML
    private Button btnSaveAs;
    @FXML
    private Button btnSave;
     
//Atribut form
    @FXML
    private TextField txtCariJudul; 
    @FXML
    private TextField txtJudul;
    @FXML
    private TextField txtPengarang;
    @FXML
    private TextField txtPenerbit;
    @FXML
    private TextField txtTahunTerbit;
    @FXML
    private TextField txtEdisi;
    @FXML
    private TextField txtKodebuku;
    @FXML
    private DatePicker tglPengadaan;
    
    @FXML
    private DatePicker dpcDari;
    
    @FXML
    private DatePicker dpcSampai;
    
     @FXML
    private ComboBox<Kategori>  comboxKategori;
    
    @FXML
    private ComboBox<Kategori>  comboxFilterKategori;
    
    @FXML
    private ComboBox<String>  comboxSorting;
    
    
    //    Import data ke database
    @FXML
    private void handleButtonSave(ActionEvent event) throws IOException {
        ObservableList<Buku> bukuList = Tblbuku.getItems();

        
        BufferedWriter writer = new BufferedWriter(
            new FileWriter("storage/data_buku.csv")
        );

        for (Buku buku : bukuList) {
            writer.write(buku.getKd_buku() + "," +
                         buku.getKode_kategori()+ "," +
                         buku.getJudul()+ "," +
                         buku.getNama_kategori()+ "," +
                         buku.getTanggal()+ "," +
                         buku.getPengarang()+ "," +
                         buku.getPenerbit()+ "," +
                         buku.getEdisi()+ "," +
                         buku.getTahun_terbit() + "\n");
        }

        writer.close();
    }


    
    
        
//    Untuk Load data buku ke view
    private BukuDAO BukuDAO; 

    @FXML
    private void loadDataBuku() {
        BukuDAO = new BukuDAO(); 
        ObservableList<Buku> bukuList = FXCollections.observableArrayList(BukuDAO.getBuku());
        Tblbuku.setItems(bukuList);

    }
    //    Untuk Load kategori buku kecombo box
   @FXML
   private KategoriDAO KategoriDAO; 
    
   @FXML
    private void loadDataKategori() {     
    KategoriDAO = new KategoriDAO(); 
    ObservableList<Kategori> kategoriList = FXCollections.observableArrayList(KategoriDAO.getKategori());
    comboxKategori.setItems(kategoriList);  
    comboxFilterKategori.setItems(kategoriList);  
    }
    
   
    
    
    private void initComboboxSorting() {
    // Mengisi combobox dengan opsi-opsi sorting
    comboxSorting.setItems(
        FXCollections.observableArrayList(
            "",                   // Tidak ada sorting
            "Judul A-Z",          // Sorting judul A-Z
            "Judul Z-A",          // Sorting judul Z-A
            "Pengadaan Terbaru",  // Sorting pengadaan terbaru
            "Pengadaan Lama"      // Sorting pengadaan terlama
        )
    );

 
    comboxSorting.getSelectionModel().selectFirst();
}

public String getSelectedComboboxSorting() {
    String sortingOption = comboxSorting.getSelectionModel().getSelectedItem();
    return sortingOption;
}
    
    
    @FXML
    public String selectedKategori(){
    Kategori selectedKategori = comboxFilterKategori.getSelectionModel().getSelectedItem();
    
    if (selectedKategori != null) {
          return selectedKategori.getKode_kategori();
    }
    return null;
    }
    
     
     
    @FXML
    private Buku selectedBuku;
    
    private void clearFilterFields() {
    txtCariJudul.clear();
    dpcDari.setValue(null);
    dpcSampai.setValue(null);
    comboxFilterKategori.getSelectionModel().clearSelection();
    comboxSorting.getSelectionModel().clearSelection();
}

    
        private void clearFields() {
        txtKodebuku.clear();
        txtKodebuku.setEditable(true); 
        txtKodebuku.setFocusTraversable(true); 
        txtJudul.clear();
        txtPengarang.clear();
        txtPenerbit.clear();
        txtTahunTerbit.clear();
        txtEdisi.clear();
    tglPengadaan.setValue(null);
    comboxKategori.getSelectionModel().clearSelection();
        
        selectedBuku = null; 
    }
    @FXML
    private Kategori selectedKategori;
 

    @FXML
    private void selectBuku(Buku buku) {
        if (buku != null) {
        selectedBuku = buku;
        txtKodebuku.setText(buku.getKd_buku());
        txtKodebuku.setEditable(false); 
        txtKodebuku.setFocusTraversable(false); 
        txtJudul.setText(buku.getJudul());
        txtPengarang.setText(buku.getPengarang());
        txtPenerbit.setText(buku.getPenerbit());
        txtTahunTerbit.setText(buku.getTahun_terbit());
        txtEdisi.setText(buku.getEdisi());
        
         for (Kategori kategori : comboxKategori.getItems()) {
            if (kategori.getKode_kategori().equals(buku.getKode_kategori())) {
                comboxKategori.getSelectionModel().select(kategori);
                break;
            }
        }
         tglPengadaan.setValue(LocalDate.parse(buku.getTanggal()));

        }
    }
  
      @FXML
private void handleBtnFilter(ActionEvent event) {
    // Mengosongkan data buku sebelum memuat yang baru
    // Reset semua filter dulu
    BukuDAO.filterJudul = null;
    BukuDAO.filterTanggalDari = null;
    BukuDAO.filterTanggalSampai = null;
    BukuDAO.filterKategori = null;
    BukuDAO.sortingOption = ""; 

    ObservableList<Buku> bukuList = Tblbuku.getItems();
    bukuList.clear();

    // Mengatur filter judul dari input TextField
    BukuDAO.filterJudul = txtCariJudul.getText();

    // Mengatur filter tanggal jika ada input
    LocalDate dari = dpcDari.getValue();
    LocalDate sampai = dpcSampai.getValue();
    if (dari != null || sampai != null) {
        BukuDAO.filterTanggalDari = dari != null ? dari.toString() : null;
        BukuDAO.filterTanggalSampai = sampai != null ? sampai.toString() : null;
    }

    // Mengatur filter kategori jika ada yang dipilih
    if (selectedKategori() != null) {
        BukuDAO.filterKategori = selectedKategori();
    } else {
        BukuDAO.filterKategori = null;
    }

    // Mengatur opsi sorting jika ada yang dipilih
    if (comboxSorting.getValue() != null) {
        BukuDAO.sortingOption = comboxSorting.getValue();
    } else {
        BukuDAO.sortingOption = "";
    }

    // Memuat ulang data buku dengan filter yang telah ditetapkan
    loadDataBuku();
    clearFilterFields();
}

    
    @FXML
    private void addBuku() {  
    Kategori selectedKategori = comboxKategori.getValue();    
    String kdbuku = txtKodebuku.getText();
    String judul = txtJudul.getText();
    String pengarang = txtPengarang.getText();
    String penerbit = txtPenerbit.getText();
    String tahun_terbit = txtTahunTerbit.getText();
    String edisi = txtEdisi.getText();
    LocalDate tanggalPengadaan = tglPengadaan.getValue();
    
   if (!kdbuku.isEmpty() && !judul.isEmpty() && !pengarang.isEmpty() && !penerbit.isEmpty() && !tahun_terbit.isEmpty() && !edisi.isEmpty() ) {
       
        ButtonType results = showAlertUpdate("Konfirmasi Data", "Apakah Data Sudah Benar?");
        if(tahun_terbit.length() < 4){
        showAlert("Error", "Tahun Terbit Wajib 4 Digit Angka");
        return;
        }
        
       if (results == ButtonType.OK){
                try {
                   Buku newBuku = new Buku(kdbuku, judul, pengarang, penerbit, tahun_terbit, edisi, selectedKategori.getKode_kategori(),tanggalPengadaan.toString());
                   BukuDAO.addBuku(newBuku);
                   loadDataBuku();
                   clearFields();
                   showAlertTrue("Berhasil", "Data berhasil Di Buat!");
                } catch (Exception e) {
                   e.printStackTrace();
                   showAlert("Error", "Terjadi kesalahan: " + e.getMessage());
                }        
       }
       
   

    }else if (kdbuku.isEmpty() && judul.isEmpty() && pengarang.isEmpty() ||
        penerbit.isEmpty() && tahun_terbit.isEmpty() && edisi.isEmpty() && tanggalPengadaan==null || selectedKategori == null) {
        showAlert("Input Error", "Semua field harus diisi!");
        return;
    
    } 
    

}
     
     
         @FXML
private void updateBuku() {  
    
    Buku selectedBuku = Tblbuku.getSelectionModel().getSelectedItem();
     if (selectedBuku == null) {
        showAlert("Selection Error", "Tidak ada buku yang dipilih!");
        return;
    }
    
    Kategori selectedKategori = comboxKategori.getValue();    
    String kdbuku = txtKodebuku.getText();
    String judul = txtJudul.getText();
    String pengarang = txtPengarang.getText();
    String penerbit = txtPenerbit.getText();
    String tahun_terbit = txtTahunTerbit.getText();
    String edisi = txtEdisi.getText();
    LocalDate tanggalPengadaan = tglPengadaan.getValue();

    // Validasi input
    if (kdbuku.isEmpty() && judul.isEmpty() && pengarang.isEmpty() ||
        penerbit.isEmpty() && tahun_terbit.isEmpty() && edisi.isEmpty() && tanggalPengadaan==null || selectedKategori == null) {
        showAlert("Input Error", "Semua field harus diisi!");
        return;
        

    }else if(!kdbuku.isEmpty() && !judul.isEmpty() && !pengarang.isEmpty() && !penerbit.isEmpty() && !tahun_terbit.isEmpty() && !edisi.isEmpty()){
        
        ButtonType results = showAlertUpdate("Konfirmasi Update", "Yakin Untuk Mengubah Data?");
        
        if (results == ButtonType.OK){
             try {
    selectedBuku.setKd_buku(kdbuku);
    selectedBuku.setJudul(judul);
    selectedBuku.setPengarang(pengarang);
    selectedBuku.setPenerbit(penerbit);
    selectedBuku.setTahun_terbit(tahun_terbit);
    selectedBuku.setEdisi(edisi);
    selectedBuku.setKode_kategori(selectedKategori.getKode_kategori());
    selectedBuku.setTanggal(tanggalPengadaan.toString());
    

    // Panggil DAO untuk update ke database
    BukuDAO.updateBuku(selectedBuku);

    // Refresh data
    loadDataBuku();
    clearFields();
            showAlertTrue("Berhasil", "Data berhasil diperbarui!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Terjadi kesalahan: " + e.getMessage());
        }
        }
        
        
        
   
         
        
    }else{
        showAlert("Selection Error", "Tidak ada buku yang dipilih!");
        return;
    }  
        
        
    
    


}
  
     
       @FXML
private void deleteBuku() {
    if (selectedBuku == null) {
        showAlert("Selection Error", "Tidak ada buku yang dipilih!");
        return;
    }

    ButtonType result = showAlertConfirm("Konfirmasi Hapus", "Yakin ingin menghapus data?");

    if (result == ButtonType.OK) {
        try {
            BukuDAO.deleteBuku(selectedBuku.getKd_buku());
            loadDataBuku();
            clearFields();
            showAlertTrue("Berhasil", "Data berhasil dihapus!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Terjadi kesalahan: " + e.getMessage());
        }
    }
}





    @Override
    public void initialize(URL url, ResourceBundle rb) {
          initComboboxSorting();
          colKdBuku.setCellValueFactory(new PropertyValueFactory<>("kd_buku"));
        colJudul.setCellValueFactory(new PropertyValueFactory<>("judul"));
        colPengarang.setCellValueFactory(new PropertyValueFactory<>("pengarang"));
        colPenerbit.setCellValueFactory(new PropertyValueFactory<>("penerbit"));
        colTahunterbit.setCellValueFactory(new PropertyValueFactory<>("tahun_terbit"));
        colEdisi.setCellValueFactory(new PropertyValueFactory<>("edisi"));
          colKdKategori.setCellValueFactory(new PropertyValueFactory<>("kode_kategori"));
            colNamaKategori.setCellValueFactory(new PropertyValueFactory<>("nama_kategori"));
              colTgl.setCellValueFactory(new PropertyValueFactory<>("tanggal"));

              
          // INI WAJIB
        loadDataBuku();
        Tblbuku.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectBuku(newValue)
        );
        
        loadDataKategori();
//     Validasi Input hanya boleh angka     
    txtEdisi.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { 
            txtEdisi.setText(newValue.replaceAll("[^\\d]", "")); 
            showAlert("Error Input", "Hanya Boleh Angka!");
            }
        }); 
  
        txtTahunTerbit.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { 
            txtTahunTerbit.setText(newValue.replaceAll("[^\\d]", "")); 
            showAlert("Error Input", "Hanya Boleh Angka!");
            }else if(newValue.length() > 4){
             txtTahunTerbit.setText(newValue.substring(0, 4));
            }
        }); 
    
  
        
    }    
    }    
    

