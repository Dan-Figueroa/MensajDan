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
    private final Conexion conexion = Conexion.getInstance();

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
}
