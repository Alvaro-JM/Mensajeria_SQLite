package mensajeria.modelo;

/**
 *
 * @author Álvaro
 */
public class Empresa{
    private final int id_empresa;
    private String nombre_empresa;
    private String cif;
    private String director;
    private String web;
    private boolean activo;

    public Empresa(int id_empresa, String nombre_empresa, String cif, String director,
            String web, boolean activo) {
        this.id_empresa = id_empresa;
        this.nombre_empresa = nombre_empresa;
        this.cif = cif;
        this.director = director;
        this.web = web;
        this.activo = activo;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
  
    
}
