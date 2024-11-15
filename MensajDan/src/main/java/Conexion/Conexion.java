/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author david_alcazar
 */
public class Conexion {
private static Conexion instancia;
    private Connection connection = null;

    private Conexion() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5434/mensadan",
                    "servidor", "servidor");
            boolean valid = connection.isValid(50000);
            System.out.println(valid ? "TEST OK" : "TEST FAIL");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error al conectar con la base de datos: " + ex);
        }
    }

    public static Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection connectDatabase() {
        try {
            // Verifica si la conexión está cerrada y vuelve a abrirla si es necesario
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5434/mensadan",
                        "servidor", "servidor");
            }
        } catch (SQLException ex) {
            System.out.println("Error al reconectar con la base de datos: " + ex);
        }
        return connection;
    }

    // Método para cerrar la conexión al finalizar la aplicación
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada correctamente");
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar la conexión: " + ex);
        }
    }
    
    public static void main(String[] args) {
        Conexion conexion = Conexion.getInstance();
        Connection conn = conexion.connectDatabase();
        if (conn != null) {
            System.out.println("Conexión exitosa a la base de datos");
            // Llama a closeConnection() al finalizar el programa para cerrar la conexión
            conexion.closeConnection();
        } else {
            System.out.println("Error en la conexión a la base de datos");
        }
    }
}
