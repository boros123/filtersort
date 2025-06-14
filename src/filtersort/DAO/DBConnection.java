/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filtersort.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Lenovo
 */
public class DBConnection {
         private static final String URL="jdbc:mysql://localhost:3306/db_buku";
       private static final String USER="root";
       private static final String PASSWORD="";

//public static Connection getConnection() throws SQLException{
//    return DriverManager.getConnection(URL,USER,PASSWORD);
//}
public static Connection getConnection() throws SQLException{
return DriverManager.getConnection(URL,USER,PASSWORD);
}
}
