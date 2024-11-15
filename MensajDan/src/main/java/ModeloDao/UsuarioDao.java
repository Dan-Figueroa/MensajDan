/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDao;

import Conexion.Conexion;
import Modelo.Usuario;
import com.sun.jdi.connect.spi.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author david_alcazar
 */
public class UsuarioDao {
    
    Conexion conectar = Conexion.getInstance();
    java.sql.Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean ingresar(String ipUsuario, String contraseña) {
        String sql = "SELECT COUNT(*) FROM Usuario WHERE ipUsuario = ? AND contraseña = ?";
        boolean existe = false;
        try {
            con = conectar.connectDatabase();  // Establecer la conexión con la base de datos
            ps = con.prepareStatement(sql);  // Preparar la consulta SQL
            ps.setString(1, ipUsuario);     // Establecer el valor para ipUsuario
            ps.setString(2, contraseña);    // Establecer el valor para contraseña
            rs = ps.executeQuery();         // Ejecutar la consulta

            if (rs.next()) {
                int count = rs.getInt(1);   // Obtener el resultado de la consulta
                existe = count > 0;         // Si el contador es mayor que 0, el usuario existe
            }
        } catch (Exception e) {
            e.printStackTrace();  // Manejo de errores
        } finally {
            closeResources();     // Asegúrate de cerrar los recursos (conexión, ResultSet, etc.)
        }
        return existe;  // Devuelve true si el usuario existe, false si no
    }

    
    public boolean agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuario(ipUsuario, nombre, contraseña, estado) VALUES(?, ?, ?, ?)";
        boolean resultado = false;

        try {
            con = conectar.connectDatabase();  // Conexión a la base de datos
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getIpUsuario());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getContraseña());
            ps.setString(4, usuario.getEstado());

            int filasAfectadas = ps.executeUpdate();
            resultado = filasAfectadas > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(); // Cierra los recursos utilizados
        }
        return resultado;
    }

    // Método para cerrar recursos
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
