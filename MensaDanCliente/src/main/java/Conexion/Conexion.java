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
 private static Conexion instancia; // Instancia única de la clase
    private Connection connection;     // Conexión a la base de datos
    private static final String URL = "jdbc:postgresql://localhost:5432/mensadan";
    private static final String USER = "servidor";
    private static final String PASSWORD = "servidor";

    // Constructor privado para evitar instanciación directa
    private Conexion() {
        try {
            Class.forName("org.postgresql.Driver"); // Cargar el driver
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection.isValid(5000)) { // Verificar si la conexión es válida
                System.out.println("Conexión establecida correctamente.");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    /**
     * Método para obtener la instancia única de la clase.
     * @return Instancia de Conexion
     */
    public static synchronized Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    /**
     * Método para obtener la conexión actual.
     * Si la conexión está cerrada, intenta reabrirla.
     * @return Objeto Connection
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException ex) {
            System.err.println("Error al reconectar con la base de datos: " + ex.getMessage());
        }
        return connection;
    }

    /**
     * Método para cerrar la conexión al finalizar la aplicación.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException ex) {
            System.err.println("Error al cerrar la conexión: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        // Prueba de la clase Conexion
        Conexion conexion = Conexion.getInstance();
        Connection conn = conexion.getConnection();
        if (conn != null) {
            System.out.println("Conexión exitosa a la base de datos.");
            conexion.closeConnection(); // Cerrar conexión al terminar
        } else {
            System.err.println("Error al establecer la conexión.");
        }
    }
}
