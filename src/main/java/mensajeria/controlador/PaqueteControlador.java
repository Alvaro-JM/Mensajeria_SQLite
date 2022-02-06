package mensajeria.controlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mensajeria.modelo.Paquete;

/**
 *
 * @author Álvaro
 */
public class PaqueteControlador extends Conexion{
    
    private List<Paquete> listaPaquetes;

    public PaqueteControlador() throws SQLException {
        listaPaquetes = new ArrayList<>();
        leerPaquete();
    }
    
    private void leerPaquete() throws SQLException {
        String sql = "SELECT id_paquete, fecha_entrega, direccion_destino, telefono_destino, direccion_origen, telefono_origen, repartidor, activo FROM paquete";

        this.conectar();
        Statement stmt = this.getConn().createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id_paquete = rs.getInt("id_paquete");
            Date fecha_entrega = rs.getDate("fecha_entrega");
            String direccion_destino = rs.getString("direccion_destino");
            String telefono_destino = rs.getString("telefono_destino");
            String direccion_origen = rs.getString("direccion_origen");
            String telefono_origen = rs.getString("telefono_origen");
            int repartidor = rs.getInt("repartidor");
            boolean activo = rs.getBoolean("activo");
            listaPaquetes.add(new Paquete(id_paquete, fecha_entrega, direccion_destino, telefono_destino, direccion_origen, telefono_origen, repartidor, activo));
        }
        this.desconectar();
    }
    
    public void insertarPaquete(Date fecha_entrega, String direccion_destino, String telefono_destino, String direccion_origen, String telefono_origen, int repartidor) throws SQLException {
        String sql = "INSERT INTO paquete(fecha_entrega, direccion_destino, telefono_destino, direccion_origen, telefono_origen, repartidor) VALUES(?,?,?,?,?,?)";

        this.conectar();
        PreparedStatement pstmt = this.getConn().prepareStatement(sql);
        
        pstmt.setDate(1, new java.sql.Date(fecha_entrega.getTime()));
        pstmt.setString(2, direccion_destino);
        pstmt.setString(3, telefono_destino);
        pstmt.setString(4, direccion_origen);
        pstmt.setString(5, telefono_origen);
        pstmt.setInt(6, repartidor);
        pstmt.executeUpdate();
        
        this.desconectar();
    }
    
    public void modificarPaquete(int id_paquete, Date fecha_entrega, String direccion_destino, String telefono_destino, String direccion_origen, String telefono_origen, int repartidor) throws SQLException {
        String sql = "UPDATE paquete SET fecha_entrega = ?, "
                + "direccion_destino = ?, "
                + "telefono_destino = ?, "
                + "direccion_origen = ?, "
                + "telefono_origen = ?, "
                + "repartidor = ? "
                + "WHERE id_paquete = ?";

        this.conectar();
        PreparedStatement pstmt = this.getConn().prepareStatement(sql);

        pstmt.setDate(1, new java.sql.Date(fecha_entrega.getTime()));
        pstmt.setString(2, direccion_destino);
        pstmt.setString(3, telefono_destino);
        pstmt.setString(4, direccion_origen);
        pstmt.setString(5, telefono_origen);
        pstmt.setInt(6, repartidor);
        pstmt.setInt(7, id_paquete);
        pstmt.executeUpdate();

        this.desconectar();
    }
    
    public void eliminarPaquete(int id_paquete) throws SQLException {
        String sql = "UPDATE paquete SET activo = false "
                + "WHERE int id_paquete = ?";

        this.conectar();
        PreparedStatement pstmt = this.getConn().prepareStatement(sql);

        pstmt.setInt(1, id_paquete);
        pstmt.executeUpdate();

        this.desconectar();
    }

    public List<Paquete> getListaPaquetes() {
        return listaPaquetes;
    }
    
}
