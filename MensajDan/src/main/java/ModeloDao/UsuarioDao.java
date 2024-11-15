/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDao;

import Conexion.Conexion;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author david_alcazar
 */
public class UsuarioDao {
    
    // Instancia única de la conexión a través del Singleton
    private final Conexion conexion = Conexion.getInstance();
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public boolean ingresar(String ipUsuario, String contraseña) {
        String sql = "SELECT COUNT(*) FROM Usuario WHERE ipUsuario = ? AND contraseña = ?";
        boolean existe = false;

        try {
            con = conexion.getConnection(); // Usar la conexión centralizada
            ps = con.prepareStatement(sql);
            ps.setString(1, ipUsuario);
            ps.setString(2, contraseña);
            rs = ps.executeQuery();

            if (rs.next()) {
                existe = rs.getInt(1) > 0; // Verificar si existe el usuario
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar usuario: " + e.getMessage());
        } finally {
            closeResources(); // Liberar recursos
        }

        return existe;
    }

    public boolean agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuario(ipUsuario, nombre, contraseña, estado) VALUES(?, ?, ?, ?)";
        boolean resultado = false;

        try {
            con = conexion.getConnection(); // Obtener conexión
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getIpUsuario());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getContraseña());
            ps.setString(4, usuario.getEstado());

            int filasAfectadas = ps.executeUpdate();
            resultado = filasAfectadas > 0; // Verificar si se insertó correctamente
        } catch (SQLException e) {
            System.err.println("Error al agregar usuario: " + e.getMessage());
        } finally {
            closeResources(); // Cerrar recursos
        }

        return resultado;
    }
    
    public Usuario obtenerUsuarioPorIp(String ipUsuario) {
        String sql = "SELECT * FROM Usuario WHERE ipUsuario = ?";
        Usuario usuario = null; // Objeto usuario que será retornado

        try {
            con = conexion.getConnection(); // Obtener conexión
            ps = con.prepareStatement(sql);
            ps.setString(1, ipUsuario);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Crear el objeto Usuario y asignar los valores desde la consulta
                usuario = new Usuario();
                usuario.setIpUsuario(rs.getString("ipUsuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setInformacion(rs.getString("informacion"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener información del usuario: " + e.getMessage());
        } finally {
            closeResources(); // Liberar recursos
        }
        return usuario; // Retornar el objeto Usuario o null si no se encontró
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
