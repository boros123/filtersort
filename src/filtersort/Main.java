/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package filtersort;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Lenovo
 */
public class Main extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/filtersort/View/Login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
public static void changeScene(String fxmlFile, String title) throws Exception {
    Parent newRoot = FXMLLoader.load(Main.class.getResource(fxmlFile));

    // mengambil ukuran root container pada file fxml
    double width = newRoot.prefWidth(-1);  // -1 nilai preferensi
    double height = newRoot.prefHeight(-1); // -1 nilai preferensi

    primaryStage.getScene().setRoot(newRoot);
    primaryStage.setWidth(width);
    primaryStage.setHeight(height);
    primaryStage.setTitle(title);
}
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
