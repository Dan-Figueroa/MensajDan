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

       // Usamos try-with-resources para garantizar que los recursos se cierren adecuadamente
       try (
           Connection con = conectar.connectDatabase(); // Usamos try-with-resources para Connection
           PreparedStatement ps = con.prepareStatement(sql) // Preparas la sentencia
       ) {
           ps.setString(1, ipUsuario);  // Asignamos la IP al PreparedStatement
           try (ResultSet rs = ps.executeQuery()) {  // Ejecutamos la consulta y gestionamos el ResultSet
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



    // Método para obtener todos los contactos de un usuario
    public ResultSet obtenerContactos(String ipUsuario) {
        String sql = "SELECT * FROM Contacto WHERE ipUsuario = ?";
        ResultSet rs = null;
        
        try {
            con = conectar.connectDatabase();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ipUsuario);
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return rs;
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
