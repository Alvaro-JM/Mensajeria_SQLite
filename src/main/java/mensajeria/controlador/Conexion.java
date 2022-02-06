package mensajeria.controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Álvaro
 */
public class Conexion {

    private final String URL = "jdbc:sqlite:mensajeria.db";
    private Connection conn;

    public Connection getConn() {
        return conn;
    }

    public void conectar() throws SQLException {
        conn = DriverManager.getConnection(URL);
    }
    
    public void desconectar() throws SQLException{
        conn.close();
    }
    
}
