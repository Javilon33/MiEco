package modelo.entidades;

/**
 * Clase que representa un tipo de fondo de inversión. Aquí se guardan los datos
 * básicos de un tipo de fondo, como su identificador y nombre.
 *
 * @author Francisco Javier Gómez Gamero
 */
public class TipoFondo {

    private int idTipoFondo;
    private String nombre;

    /**
     * Constructor para crear un tipo de fondo con todos sus datos.
     *
     * @param idTipoFondo ID único del tipo de fondo.
     * @param nombre Nombre del tipo de fondo.
     */
    public TipoFondo(int idTipoFondo, String nombre) {
        this.idTipoFondo = idTipoFondo;
        this.nombre = nombre;
    }

    /**
     * Obtiene el ID del tipo de fondo.
     *
     * @return El ID del tipo de fondo.
     */
    public int getIdTipoFondo() {
        return idTipoFondo;
    }

    /**
     * Cambia el ID del tipo de fondo.
     *
     * @param idTipoFondo Nuevo ID del tipo de fondo.
     */
    public void setIdTipoFondo(int idTipoFondo) {
        this.idTipoFondo = idTipoFondo;
    }

    /**
     * Obtiene el nombre del tipo de fondo.
     *
     * @return El nombre del tipo de fondo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre del tipo de fondo.
     *
     * @param nombre Nuevo nombre del tipo de fondo.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
