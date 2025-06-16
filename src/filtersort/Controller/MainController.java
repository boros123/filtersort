/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package filtersort.Controller;

import filtersort.DAO.BukuDAO;
import filtersort.DAO.KategoriDAO;
import filtersort.Main;
import filtersort.Model.Buku;
import filtersort.Model.Kategori;
import filtersort.Model.Session;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
   

//    atribut fxml 
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
    private Button btnSaveData;
    @FXML
    private Button btnLogout;
     
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
    @FXML
    private TextArea txtPesan;
     @FXML
    private Label NamaFile;
    private File currentFile=null;
    
    @FXML
    private Label NamaUser;
    
// ACTION BUTTON   
//  Action dari filter button serbaguna untuk sorting filtering dan searching
@FXML
private void handleBtnFilter(ActionEvent event) {
    // Mengosongkan data buku sebelum memuat yang baru
    // Reset semua filter dulu
    
    
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
    clearFilterFields();
    loadDataBuku();
}

//    dialog pilih file
    @FXML
private void handleButtonOpen() {
    Stage stage = (Stage) btnOpen.getScene().getWindow();

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Pilih File Teks");
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("File Teks", "*.csv")
    );

    // Tampilkan dialog open file
    File selectedFile = fileChooser.showOpenDialog(stage);

    // Jika ada file yang dipilih
    if (selectedFile != null) {
        try {
            BufferedReader reader = new BufferedReader(
                new FileReader(selectedFile)
            );

            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            txtPesan.setText(content.toString());
            currentFile = selectedFile;
            NamaFile.setText("File dipilih: " + selectedFile.getName());

        } catch (IOException e) {
            e.printStackTrace();
            // Dalam aplikasi nyata, tampilkan dialog error
        }
    }
}
    
//    /    import data
@FXML
private void handleButtonSaveData(ActionEvent event) throws IOException {
    // Cek dulu kalau belum ada file yang dipilih
    if (currentFile == null) {
        showAlert("Import CSV", "Belum ada file dipilih.");
        return;
    }

    BufferedReader reader = new BufferedReader(
        new FileReader(currentFile)
    );

    String line;
    int count = 0;
    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");

        // Pastikan data minimal 8 kolom sesuai kebutuhan database
        if (parts.length >= 8) {
            Buku buku = new Buku();
             buku.setKd_buku(parts[0]);
             buku.setKode_kategori(parts[1]);
             buku.setJudul(parts[2]);
             buku.setPengarang(parts[3]);
             buku.setPenerbit(parts[4]);
             buku.setTahun_terbit(parts[5].trim());
             buku.setEdisi(parts[6]);
             buku.setTanggal(parts[7]);

            // Simpan ke database
            BukuDAO.addBuku(buku);
            count++;
        }
    }

    reader.close();
    loadDataBuku();
    clearFieldsArea();
    showAlertTrue("Import CSV", "Berhasil import " + count + " data ke database.");
}

    //    export data dari database
    @FXML
private void handleButtonSave(ActionEvent event) throws IOException {
    ObservableList<Buku> bukuList = Tblbuku.getItems();

    // Cek kalau tabel kosong
    if (bukuList.isEmpty()) {
        showAlertTrue("Export CSV", "Tidak ada data untuk diekspor.");
        return;
    }

    // Mulai tulis ke file CSV
    BufferedWriter writer = new BufferedWriter(
        new FileWriter("storage/data_buku.csv")
    );

    for (Buku buku : bukuList) {
        writer.write(
            buku.getKd_buku() + "," +
            buku.getKode_kategori() + "," +
            buku.getJudul() + "," +
            buku.getNama_kategori() + "," +
            buku.getPengarang() + "," +
            buku.getPenerbit() + "," +
            buku.getTahun_terbit() + "," +
            buku.getEdisi() + "," +
            buku.getTanggal() + "\n"
        );
    }

    writer.close();

    // Tampilkan notifikasi sukses
    showAlertTrue("Export CSV", "Data berhasil diekspor ke storage/data_buku.csv");
}
    @FXML
private void handleLogout(ActionEvent event) {
    Session.getInstance().setUsername(null);
    Session.getInstance().setFullname(null);
    Session.getInstance().setPassword(null);
    Session.getInstance().setRole(null);

    try {
        Main.changeScene("/filtersort/View/Login.fxml","Login");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
// LOAD DATA KE TABLE, COMBO BOX DSB      
//    Untuk Load data buku dan kategori yang di pilih dan untuk menjalankan  query dari action yang dilakukan
         
    @FXML
    private Buku selectedBuku;
    @FXML
    private BukuDAO BukuDAO; 
    @FXML
    private Kategori selectedKategori;
      @FXML
   private KategoriDAO KategoriDAO; 

    @FXML
    private void loadDataBuku() {
        BukuDAO = new BukuDAO(); 
        ObservableList<Buku> bukuList = FXCollections.observableArrayList(BukuDAO.getBuku());
        Tblbuku.setItems(bukuList);

    }
    
    //    Untuk Load kategori buku kecombo box 
   @FXML
    private void loadDataKategori() {     
    KategoriDAO = new KategoriDAO(); 
    ObservableList<Kategori> kategoriList = FXCollections.observableArrayList(KategoriDAO.getKategori());
    comboxKategori.setItems(kategoriList); 
    comboxFilterKategori.setItems(kategoriList);  
    
      comboxFilterKategori.setValue(null);
    comboxFilterKategori.setPromptText("Kategori");
    
       comboxSorting.setValue(null);
    comboxSorting.setPromptText("Urutkan");
    }
    
    // Mengisi combobox dengan opsi-opsi sorting
    private void initComboboxSorting() {
    comboxSorting.setItems(
        FXCollections.observableArrayList(                 
            "Judul A-Z",          // Sorting judul A-Z
            "Judul Z-A",          // Sorting judul Z-A
            "Pengadaan Terbaru",  // Sorting pengadaan terbaru
            "Pengadaan Lama"      // Sorting pengadaan terlama
        )
    );

 
    comboxSorting.getSelectionModel().selectFirst();
}

    
//ISI OPSI YANG DI PILIH DAN DATA YANG DI PILIH
//    untuk mengisi  opsti sorting yg di pilih
public String getSelectedComboboxSorting() {
    String sortingOption = comboxSorting.getSelectionModel().getSelectedItem();
    return sortingOption;
}
    
//    untuk mengambil kode kategori yang di pilih di combox
    @FXML
    public String selectedKategori(){
    Kategori selectedKategori = comboxFilterKategori.getSelectionModel().getSelectedItem();
    if (selectedKategori != null) {
          return selectedKategori.getKode_kategori();
    }
    return null;
    }
//    untuk mengisi field dari data buku yang di pilih di table view
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
    
    
//UNTUK RESET COMBOBOX,FORM DSB    
// untuk reset hasil yang telah di pilih setelah filter berhasil di lakukan
 @FXML
private void clearFilterFields() {
    txtCariJudul.clear();
    dpcDari.setValue(null);
    dpcSampai.setValue(null);

 comboxFilterKategori.getSelectionModel().clearSelection();
comboxFilterKategori.setValue(null); 
comboxFilterKategori.setPromptText("Kategori");
 comboxSorting.getSelectionModel().clearSelection();
comboxSorting.setValue(null);
comboxSorting.setPromptText("Urutkan");    
}
@FXML
private void clearFieldsArea() {
    txtPesan.clear();
    currentFile = null;
    NamaFile.setText("");
}
//untuk clear field setelah data berhasil di tambahkan
        private void clearFields() {
        String kodeBaru = BukuDAO.generateKodeBuku();
        txtKodebuku.setText(kodeBaru);;
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
    
 

//CRUD
//    tambah buku beserta validasi nya
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
    
    
    if (kdbuku.isEmpty() || judul.isEmpty() || pengarang.isEmpty() || penerbit.isEmpty() 
        || tahun_terbit.isEmpty() || edisi.isEmpty() || tanggalPengadaan == null || selectedKategori == null) {
        showAlert("Input Error", "Semua field harus diisi!");
        return;
    }
    
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
    }  
}
     
//Ypdate buku beserta validasi nya  
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
   
//Hapus buku beserta validasi nya  
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
        
        if (Session.getInstance().getUsername() == null) {
        try {
            Main.changeScene("/filtersort/View/Login.fxml","Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else {
        //        Ambil nama yang sedang login
  String nama = Session.getInstance().getFullname();
    // Tampilkan ke label
    NamaUser.setText(nama + " !");
    };
        
//    Generate kode buku di text field
    String kodeBaru = BukuDAO.generateKodeBuku();
    txtKodebuku.setText(kodeBaru);
//    Menampilkan opsi sorting yang sudah di set
    initComboboxSorting();
    
//   Set kolom untuk menampilkan data dari database ke UI
    colKdBuku.setCellValueFactory(new PropertyValueFactory<>("kd_buku"));
    colJudul.setCellValueFactory(new PropertyValueFactory<>("judul"));
    colKdKategori.setCellValueFactory(new PropertyValueFactory<>("kode_kategori"));
    colNamaKategori.setCellValueFactory(new PropertyValueFactory<>("nama_kategori"));
    colPengarang.setCellValueFactory(new PropertyValueFactory<>("pengarang"));
    colPenerbit.setCellValueFactory(new PropertyValueFactory<>("penerbit"));
    colTahunterbit.setCellValueFactory(new PropertyValueFactory<>("tahun_terbit"));
    colEdisi.setCellValueFactory(new PropertyValueFactory<>("edisi"));
    colTgl.setCellValueFactory(new PropertyValueFactory<>("tanggal"));

              
          // Load data atau menampilkan data setelah diset
        loadDataBuku();
        Tblbuku.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectBuku(newValue)
        );
        
//        load data kategori dari database untuk combox,ataupun nama kategori di tabel ui berdasarkan relasinya
        loadDataKategori();
        
//     Validasi Input hanya boleh angka     
    txtEdisi.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { 
            txtEdisi.setText(newValue.replaceAll("[^\\d]", "")); 
            showAlert("Error Input", "Hanya Boleh Angka!");
            }
        }); 
//  validasi tahun harus 4 digit dan harus angka
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
    

