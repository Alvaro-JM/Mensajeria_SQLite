package mensajeria.controlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mensajeria.modelo.Empresa;

/**
 *
 * @author Álvaro
 */
public class EmpresaControlador extends Conexion{
    
    private List<Empresa> listaEmpresas;

    public EmpresaControlador() {
        listaEmpresas = new ArrayList<>();
    }
    
    public void leerEmpresa() throws SQLException {
        String sql = "SELECT id_empresa, nombre_empresa, cif, director, web, activo FROM empresa";

        this.conectar();
        Statement stmt = this.getConn().createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id_empresa = rs.getInt("id_empresa");
            String nombre_empresa = rs.getString("nombre_empresa");
            String cif = rs.getString("cif");
            String director = rs.getString("director");
            String web = rs.getString("web");
            boolean activo = rs.getBoolean("activo");
            listaEmpresas.add(new Empresa(id_empresa, nombre_empresa, cif, director, web, activo));
        }
        this.desconectar();
    }
        
    public void insertarEmpresa(String nombre_empresa, String cif, String director, String web) throws SQLException {
        String sql = "INSERT INTO empresa(nombre_empresa, cif, director, web) VALUES(?,?,?,?)";

        this.conectar();
        PreparedStatement pstmt = this.getConn().prepareStatement(sql);
        
        pstmt.setString(1, nombre_empresa);
        pstmt.setString(2, cif);
        pstmt.setString(3, director);
        pstmt.setString(4, web);
        pstmt.executeUpdate();
        
        this.desconectar();
    }
    
    public void modificarEmpresa(int id_empresa, String nombre_empresa, String cif, String director, String web) throws SQLException {
        String sql = "UPDATE empresa SET nombre_empresa = ?, "
                + "cif = ?, "
                + "director = ?, "
                + "web = ? "
                + "WHERE id_empresa = ?";

        this.conectar();
        PreparedStatement pstmt = this.getConn().prepareStatement(sql);

        pstmt.setString(1, nombre_empresa);
        pstmt.setString(2, cif);
        pstmt.setString(3, director);
        pstmt.setString(4, web);
        pstmt.setInt(5, id_empresa);
        pstmt.executeUpdate();

        this.desconectar();
    }
    
    public void eliminarEmpresa(int id_empresa) throws SQLException {
        String sql = "UPDATE empresa SET activo = false "
                + "WHERE id_empresa = ?";

        this.conectar();
        PreparedStatement pstmt = this.getConn().prepareStatement(sql);

        pstmt.setInt(1, id_empresa);
        pstmt.executeUpdate();

        this.desconectar();
    }
    
    public List<Empresa> getListaEmpresas() {
        return listaEmpresas;
    }
    
}
