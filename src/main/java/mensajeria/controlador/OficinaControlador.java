package mensajeria.controlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mensajeria.modelo.Oficina;

/**
 *
 * @author Álvaro
 */
public class OficinaControlador extends Conexion{
    
    private List<Oficina> listaOficinas;

    public OficinaControlador() throws SQLException {
        listaOficinas = new ArrayList<>();
        leerOficina();
    }
    
    private void leerOficina() throws SQLException {
        String sql = "SELECT id_oficina, direccion_oficina, telefono_oficina, email, encargado, empresa, activo FROM oficina";

        this.conectar();
        Statement stmt = this.getConn().createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id_oficina = rs.getInt("id_oficina");
            String direccion_oficina = rs.getString("direccion_oficina");
            String telefono_oficina = rs.getString("telefono_oficina");
            String email = rs.getString("email");
            String encargado = rs.getString("encargado");
            int empresa = rs.getInt("empresa");
            boolean activo = rs.getBoolean("activo");
            listaOficinas.add(new Oficina(id_oficina, direccion_oficina, telefono_oficina, email, encargado, empresa, activo));
        }
        this.desconectar();
    }
    
    public void insertarOficina(String direccion_oficina, String telefono_oficina, String email, String encargado, int empresa) throws SQLException {
        String sql = "INSERT INTO oficina(direccion_oficina, telefono_oficina, email, encargado, empresa) VALUES(?,?,?,?,?)";

        this.conectar();
        PreparedStatement pstmt = this.getConn().prepareStatement(sql);
        
        pstmt.setString(1, direccion_oficina);
        pstmt.setString(2, telefono_oficina);
        pstmt.setString(3, email);
        pstmt.setString(4, encargado);
        pstmt.setInt(5, empresa);
        pstmt.executeUpdate();
        
        this.desconectar();
    }
    
    public void modificarOficina(int id_oficina, String direccion_oficina, String telefono_oficina, String email, String encargado, int empresa) throws SQLException {
        String sql = "UPDATE oficina SET direccion_oficina = ?, "
                + "telefono_oficina = ?, "
                + "email = ?, "
                + "encargado = ?, "
                + "empresa = ? "
                + "WHERE id_oficina = ?";

        this.conectar();
        PreparedStatement pstmt = this.getConn().prepareStatement(sql);

        pstmt.setString(1, direccion_oficina);
        pstmt.setString(2, telefono_oficina);
        pstmt.setString(3, email);
        pstmt.setString(4, encargado);
        pstmt.setInt(5, empresa);
        pstmt.setInt(6, id_oficina);
        pstmt.executeUpdate();

        this.desconectar();
    }
    
    public void eliminarOficina(int id_oficina) throws SQLException {
        String sql = "UPDATE oficina SET activo = false "
                + "WHERE id_oficina = ?";

        this.conectar();
        PreparedStatement pstmt = this.getConn().prepareStatement(sql);

        pstmt.setInt(1, id_oficina);
        pstmt.executeUpdate();

        this.desconectar();
    }
    
    public List<Oficina> getListaOficinas() {
        return listaOficinas;
    }
    
}
