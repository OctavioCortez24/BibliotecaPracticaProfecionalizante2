package Biblioteca;

import javax.swing.*;
import java.sql.*;

public class Conexion {

    private static Connection conn;
    private Conexion() {

    }

    static Connection getInstance() throws SQLException{
        if (conn == null) {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bibliotecas", "root", "1234");
        }
        return conn;
    }


    public static void cerrarConexion () throws SQLException{
        try {
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
            conn.close();
        }finally {
            conn.close();
        }

    }

}


