/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDao;

import Conexion.Conexion;
import Modelo.Contactos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author david_alcazar
 */
public class ContactoDao {
    
    Conexion conectar = Conexion.getInstance();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean agregarContacto(Contactos contacto) {
        String sql = "INSERT INTO Contacto(nombreCon, ipUsuario, ipUsCont) VALUES(?, ?, ?)";
        boolean resultado = false;

        try (Connection con = conectar.connectDatabase();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, contacto.getNombreCon());
            ps.setString(2, contacto.getIpUsuario());
            ps.setString(3, contacto.getIpUsCont());

            int filasAfectadas = ps.executeUpdate();
            resultado = filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar el contacto: " + e.getMessage());
        }

        return resultado;
    }
    
    public ArrayList<Contactos> obtenerNombresContactos(String ipUsuario) {
       ArrayList<Contactos> nombresContactos = new ArrayList<>(); 
       String sql = "SELECT nombreContacto FROM VistaNombresContactos WHERE ipUsuarioPrincipal = ?";

       try (
           Connection con = conectar.connectDatabase(); // Conexión a la base de datos
           PreparedStatement ps = con.prepareStatement(sql) // Preparación de la consulta
       ) {
           ps.setString(1, ipUsuario);  // Asigna la IP al PreparedStatement
           try (ResultSet rs = ps.executeQuery()) {  // Ejecuta la consulta y gestiona el ResultSet
               while (rs.next()) {
                   Contactos contacto = new Contactos();
                   contacto.setNombreCon(rs.getString("nombreContacto"));
                   nombresContactos.add(contacto);
               }
           }
       } catch (SQLException e) {
           e.printStackTrace();  // Manejo de excepciones SQL
       }
       return nombresContactos;
    }
    
    public ArrayList<Contactos> buscarContactosPorNombre(String nombre, String ipUsuario) {
        ArrayList<Contactos> contactos = new ArrayList<>();
        String sql = "SELECT * FROM Contacto WHERE nombreCon LIKE ? AND ipUsuario = ?";  // Filtramos por nombre y ipUsuario

        try (
            Connection con = conectar.connectDatabase();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, "%" + nombre + "%");  // Añadimos "%" para permitir coincidencias parciales en el nombre
            ps.setString(2, ipUsuario);  // Filtramos por la IP del usuario en la aplicación

            try (ResultSet rs = ps.executeQuery()) {  
                while (rs.next()) {
                    Contactos contacto = new Contactos();
                    contacto.setNombreCon(rs.getString("nombreCon"));
                    contactos.add(contacto);  // Añadimos el contacto a la lista
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Manejo de excepciones SQL
        }

        return contactos;  // Retorna la lista de contactos encontrados (puede estar vacía)
    }



    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            // No cierres la conexión aquí, ya que queremos reutilizarla
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
