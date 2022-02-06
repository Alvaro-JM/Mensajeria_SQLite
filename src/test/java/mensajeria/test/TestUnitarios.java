package mensajeria.test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.assertEquals;
import mensajeria.controlador.EmpresaControlador;
import mensajeria.modelo.Empresa;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Álvaro
 */
public class TestUnitarios {
    
    public TestUnitarios() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void test_leerBD_cargarListaEmpresaConDatos_enControlador_comparacionTamaniosLista() {
        try {
            EmpresaControlador instance = new EmpresaControlador();
            int sizeBefore = instance.getListaEmpresas().size();
            instance.leerEmpresa();
            int sizeAfter = instance.getListaEmpresas().size();

            assertEquals(0, sizeBefore);
            assertNotEquals(sizeBefore, sizeAfter);
        } catch (SQLException ex) {
            Logger.getLogger(TestUnitarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void test_insertarEmpresa_enBD_comparacionTamaniosTrasLectura() {
        try {
            EmpresaControlador instance = new EmpresaControlador();
            instance.leerEmpresa();
            int sizeBefore = instance.getListaEmpresas().size();
            
            instance.insertarEmpresa("test_empresa_thJkl980HAsse78V", "test_cif", "test_director", "test_web");
            
            instance.getListaEmpresas().clear();
            instance.leerEmpresa();
            int sizeAfter = instance.getListaEmpresas().size();
            
            instance.conectar();
            String sql = "DELETE FROM empresa WHERE nombre_empresa = 'test_empresa_thJkl980HAsse78V'";
            PreparedStatement pstmt = instance.getConn().prepareStatement(sql);
            pstmt.executeUpdate();
            instance.desconectar();
            
            assertEquals(sizeAfter, sizeBefore+1);
        } catch (SQLException ex) {
            Logger.getLogger(TestUnitarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   @Test
    public void test_modificarEmpresa_enBD_modificarNombreEmpresa_primerElemento_nombreAleatorio(){
        Random random = new Random();
        
        try {
            //Se carga la lista con las empresas de la BD
            EmpresaControlador instance = new EmpresaControlador();
            instance.leerEmpresa();
            
            //Genera nombre aleatorio
            String randomName = "";
            for (int i = 0; i < 10; i++) {
                randomName += (char) (random.nextInt(57) + 65);
            }
            
            //Accede al primer elemento y se almacena el nombre original
            Empresa empresa = instance.getListaEmpresas().get(0);
            String originalName = empresa.getNombre_empresa();
            
            //Modifica el nombre dejando los demás valores tal cual
            instance.modificarEmpresa(empresa.getId_empresa(), randomName, empresa.getCif(), empresa.getDirector(), empresa.getWeb());
            
            //Se limpia la lista y se vuelve a cargar desde la BD
            instance.getListaEmpresas().clear();
            instance.leerEmpresa();
            
            //Se accede de nuevo al primer elemento y se comprueba el nombre
            empresa = instance.getListaEmpresas().get(0);
            assertEquals(randomName, empresa.getNombre_empresa());
            
            //Modifica el nombre por el que tenía originalemente
            instance.modificarEmpresa(empresa.getId_empresa(), originalName, empresa.getCif(), empresa.getDirector(), empresa.getWeb());
            
        } catch (SQLException ex) {
            Logger.getLogger(TestUnitarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void test_eliminarEmpresa_borradoLogico_primerElemento(){
        
        try {
            //Se carga la lista 
            EmpresaControlador instance = new EmpresaControlador();
            instance.leerEmpresa();
            
            //Se accede al primer elemento,se pone a true, se almacena el id
            instance.getListaEmpresas().get(0).setActivo(true);
            int id_empresa = instance.getListaEmpresas().get(0).getId_empresa();
            
            //Se elimina dicho elemento
            instance.eliminarEmpresa(id_empresa);
            
            //Se vuelve a cargar la lista desde la BD y se comprueba
            instance.getListaEmpresas().clear();
            instance.leerEmpresa();
            boolean activo = instance.getListaEmpresas().get(0).isActivo();
            assertEquals(false, activo);
            
            //Se vuelve a poner activo
            instance.conectar();
            String sql = "UPDATE empresa SET activo = true WHERE id_empresa = " + id_empresa;
            PreparedStatement pstmt = instance.getConn().prepareStatement(sql);
            pstmt.executeUpdate();
            instance.desconectar();  
            
        } catch (SQLException ex) {
            Logger.getLogger(TestUnitarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}