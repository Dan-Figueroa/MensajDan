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
    
    private final Conexion conexion = Conexion.getInstance();    // Instancia única de la conexión a través del Singleton
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    

    public boolean agregarContacto(Contactos contacto) {
        String sql = "INSERT INTO Contacto(nombreCon, ipUsuario, ipUsCont) VALUES(?, ?, ?)";
        boolean resultado = false;

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, contacto.getNombreCon());
            ps.setString(2, contacto.getIpUsuario());
            ps.setString(3, contacto.getIpUsCont());

            int filasAfectadas = ps.executeUpdate();
            resultado = filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar el contacto: " + e.getMessage());
        } finally{
            closeResources();
        }

        return resultado;
    }

    public ArrayList<Contactos> obtenerNombresContactos(String ipUsuario) {
        ArrayList<Contactos> nombresContactos = new ArrayList<>();
        String sql = "SELECT nombreContacto FROM VistaNombresContactos WHERE ipUsuarioPrincipal = ?";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ipUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Contactos contacto = new Contactos();
                    contacto.setNombreCon(rs.getString("nombreContacto"));
                    nombresContactos.add(contacto);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener nombres de contactos: " + e.getMessage());
        } finally{
            closeResources();
        }

        return nombresContactos;
    }

    public ArrayList<Contactos> buscarContactosPorNombre(String nombre, String ipUsuario) {
        ArrayList<Contactos> contactos = new ArrayList<>();
        String sql = "SELECT * FROM Contacto WHERE nombreCon LIKE ? AND ipUsuario = ?";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");
            ps.setString(2, ipUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Contactos contacto = new Contactos();
                    contacto.setNombreCon(rs.getString("nombreCon"));
                    contacto.setIpUsuario(rs.getString("ipUsuario"));
                    contacto.setIpUsCont(rs.getString("ipUsCont"));
                    contactos.add(contacto);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar contactos por nombre: " + e.getMessage());
        }finally{
            closeResources();
        }

        return contactos;
    }
    
    public String obtenerIpContactoPorNombre(String nombre, String ipUsuario) {
        String sql = "SELECT ipUsCont FROM Contacto WHERE nombreCon = ? AND ipUsuario = ?";
        String ipContacto = null;

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, ipUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ipContacto = rs.getString("ipUsCont");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener la IP del contacto por nombre: " + e.getMessage());
        }finally{
            closeResources();
        }

        return ipContacto;
    }
    
    public Contactos obtenerInformacionContacto(String ipUsuario, String ipContacto) {
        Contactos contacto = null;
        // Consulta SQL ajustada para usar los nombres correctos de las columnas en la vista
        String sql = "SELECT nombre_registrado, informacion_contacto, ip_contacto " +
                     "FROM VistaInformacionContacto WHERE ip_contacto = ? AND ipUsuario = ?";

        try (Connection con = conexion.getConnection();  // Conexión a la base de datos
             PreparedStatement ps = con.prepareStatement(sql)) {  // Preparar la consulta

            ps.setString(1, ipContacto);  // Establece la IP del contacto
            ps.setString(2, ipUsuario);   // Establece la IP del usuario logueado

            try (ResultSet rs = ps.executeQuery()) {  // Ejecutar la consulta
                if (rs.next()) {  // Si hay resultados
                    contacto = new Contactos();
                    contacto.setNombreCon(rs.getString("nombre_registrado"));  // Asigna el nombre
                    contacto.setSetInformacionContacto(rs.getString("informacion_contacto"));  // Asigna la información pública
                    contacto.setIpUsCont(rs.getString("ip_contacto"));  // Asigna la IP del contacto
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener la información del contacto: " + e.getMessage());
        }finally{
            closeResources();
        }

        return contacto;  // Retorna el contacto con la información
    }

        public boolean actualizarNombreContacto(String ipUsuario, String ipContacto, String nuevoNombre) {
        String sql = "UPDATE Contacto SET nombreCon = ? WHERE ipUsuario = ? AND ipUsCont = ?";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoNombre);  // Establece el nuevo nombre del contacto
            ps.setString(2, ipUsuario);  // Establece la IP del usuario logueado
            ps.setString(3, ipContacto);  // Establece la IP del contacto específico

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar el nombre del contacto: " + e.getMessage());
        }finally{
            closeResources();
        }

        return false; 
    }


    private void closeResources() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            // No cerramos la conexión aquí para permitir reutilización por el Singleton.
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}
