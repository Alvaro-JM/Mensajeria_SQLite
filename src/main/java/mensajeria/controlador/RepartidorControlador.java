package mensajeria.controlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mensajeria.modelo.Repartidor;
/**
 *
 * @author Álvaro
 */
public class RepartidorControlador extends Conexion{
    
    private List<Repartidor> listaRepartidores;

    public RepartidorControlador() throws SQLException {
        listaRepartidores = new ArrayList<>();
        leerRepartidor();
    }
    
    private void leerRepartidor() throws SQLException {
        String sql = "SELECT id_repartidor, dni, nombre_repartidor, telefono_repartidor, antiguedad, oficina, activo FROM repartidor";

        this.conectar();
        Statement stmt = this.getConn().createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id_repartidor = rs.getInt("id_repartidor");
            String dni = rs.getString("dni");
            String nombre_repartidor = rs.getString("nombre_repartidor");
            String telefono_repartidor = rs.getString("telefono_repartidor");
            int antiguedad = rs.getInt("antiguedad");
            int oficina = rs.getInt("oficina");
            boolean activo = rs.getBoolean("activo");
            listaRepartidores.add(new Repartidor(id_repartidor, dni, nombre_repartidor, telefono_repartidor, antiguedad, oficina, activo));
        }
        this.desconectar();
    }
    
    public void insertarRepartidor(String dni, String nombre_repartidor, String telefono_repartidor, int antiguedad, int oficina) throws SQLException {
        String sql = "INSERT INTO repartidor(dni, nombre_repartidor, telefono_repartidor, antiguedad, oficina) VALUES(?,?,?,?,?)";

        this.conectar();
        PreparedStatement pstmt = this.getConn().prepareStatement(sql);
        
        pstmt.setString(1, dni);
        pstmt.setString(2, nombre_repartidor);
        pstmt.setString(3, telefono_repartidor);
        pstmt.setInt(4, antiguedad);
        pstmt.setInt(5, oficina);
        pstmt.executeUpdate();
        
        this.desconectar();
    }
    
    public void modificarRepartidor(int id_repartidor, String dni, String nombre_repartidor, String telefono_repartidor, int antiguedad, int oficina) throws SQLException {
        String sql = "UPDATE repartidor SET dni = ?, "
                + "nombre_repartidor = ?, "
                + "telefono_repartidor = ?, "
                + "antiguedad = ?, "
                + "oficina = ? "
                + "WHERE id_repartidor = ?";

        this.conectar();
        PreparedStatement pstmt = this.getConn().prepareStatement(sql);

        pstmt.setString(1, dni);
        pstmt.setString(2, nombre_repartidor);
        pstmt.setString(3, telefono_repartidor);
        pstmt.setInt(4, antiguedad);
        pstmt.setInt(5, oficina);
        pstmt.setInt(6, id_repartidor);
        pstmt.executeUpdate();

        this.desconectar();
    }
    
    public void eliminarRepartidor(int id_repartidor) throws SQLException {
        String sql = "UPDATE repartidor SET activo = false "
                + "WHERE id_repartidor = ?";

        this.conectar();
        PreparedStatement pstmt = this.getConn().prepareStatement(sql);

        pstmt.setInt(1, id_repartidor);
        pstmt.executeUpdate();

        this.desconectar();
    }

    public List<Repartidor> getListaRepartidores() {
        return listaRepartidores;
    }
    
}
