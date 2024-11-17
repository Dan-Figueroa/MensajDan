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
    
    // Instancia única de la conexión a través del Singleton
    private final Conexion conexion = Conexion.getInstance();    // Instancia única de la conexión a través del Singleton
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    

    /**
     * Agregar un nuevo contacto a la base de datos.
     *
     * @param contacto Objeto Contactos con la información del nuevo contacto.
     * @return true si el contacto fue agregado correctamente, false en caso contrario.
     */
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
        }

        return resultado;
    }

    /**
     * Obtener una lista de los nombres de contactos asociados a un usuario.
     *
     * @param ipUsuario Dirección IP del usuario principal.
     * @return Lista de objetos Contactos con los nombres de los contactos.
     */
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
        }

        return nombresContactos;
    }

    /**
     * Buscar contactos por nombre y dirección IP del usuario.
     *
     * @param nombre Nombre parcial o completo del contacto.
     * @param ipUsuario Dirección IP del usuario asociado.
     * @return Lista de contactos que coinciden con los criterios de búsqueda.
     */
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
        }

        return contactos;
    }
    
    /**
 * Obtener la dirección IP de un contacto dado su nombre.
 *
 * @param nombre Nombre del contacto.
 * @param ipUsuario Dirección IP del usuario asociado.
 * @return La dirección IP del contacto si existe, o null si no se encuentra.
 */
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
        }

        return contacto;  // Retorna el contacto con la información
    }



    
    
    /**
     * Método privado para cerrar los recursos de base de datos.
     */
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
