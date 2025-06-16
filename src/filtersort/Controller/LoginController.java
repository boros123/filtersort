/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package filtersort.Controller;

import filtersort.DAO.UserDAO;
import filtersort.Main;
import filtersort.Model.Session;
import filtersort.Model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    @FXML
    private TextField Username;
    @FXML
    private PasswordField Password;  
    @FXML
    private Button btnLogin;
    
    
    @FXML
    private void handleButtonLoginAction(ActionEvent event) throws Exception{
    checkLogin();
    }
    
    private void checkLogin() throws Exception {
    String username = Username.getText().trim();
    String password = Password.getText().trim();

    if (username.isEmpty() || password.isEmpty()) {
        showAlert("Login Error", "Isi Username dan Password!");
        return;
    }

    try {
        // Ambil data user dari database menggunakan UserDAO
        User user = new UserDAO().GetAccount(username, password);

        if (user != null) {
            // Jika login sukses, simpan data user ke Session Singleton
            Session session = Session.getInstance();
            session.setUsername(user.getUsername());
            session.setPassword(user.getPassword());
            session.setFullname(user.getFullname());
            session.setRole(user.getRole());

            showInfo("Login Success", "Login berhasil");

            // Pindah ke Dashboard
            Main main = new Main();
            main.changeScene("/filtersort/View/Main.fxml","Dashboard");

        } else {
            showAlert("Login Error", "Username atau Password Salah!");
        }

    } catch (Exception e) {
        e.printStackTrace();
        showAlert("Login Error", "Username atau Password Salah!");
    }
    
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
