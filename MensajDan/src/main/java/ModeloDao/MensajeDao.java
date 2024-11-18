
package ModeloDao;
import Conexion.Conexion;
import Modelo.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author david_alcazar
 */
public class MensajeDao {
    private final Conexion conexion = Conexion.getInstance();
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public boolean agregarMensaje(Mensaje mensaje) {
        String sql = "INSERT INTO Mensaje (idConv, ipUsuario, contenido, estado) VALUES (?, ?, ?, ?)";
        boolean resultado = false;

        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, mensaje.getIdConv());
            ps.setString(2, mensaje.getIpUsuario());
            ps.setString(3, mensaje.getContenido());
            ps.setString(4, mensaje.getEstado());

            int filasAfectadas = ps.executeUpdate();
            resultado = filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar mensaje: " + e.getMessage());
        } finally {
            closeResources();
        }

        return resultado;
    }
    
    public ArrayList<Mensaje> obtenerMensajesPorConversacion(int idConv) {
        String sql = "SELECT * FROM Mensaje WHERE idConv = ? ORDER BY fechaEnvio ASC";
        ArrayList<Mensaje> mensajes = new ArrayList<>();

        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idConv);
            rs = ps.executeQuery();

            while (rs.next()) {
                Mensaje mensaje = new Mensaje();
                mensaje.setIdMnj(rs.getInt("idMnj"));
                mensaje.setIdConv(rs.getInt("idConv"));
                mensaje.setIpUsuario(rs.getString("ipUsuario"));
                mensaje.setContenido(rs.getString("contenido"));
                mensaje.setFechaEnvio(rs.getString("fechaEnvio"));
                mensaje.setEstado(rs.getString("estado"));

                mensajes.add(mensaje);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener mensajes: " + e.getMessage());
        } finally {
            closeResources();
        }

        return mensajes;
    }
    
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}
