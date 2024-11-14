
package modelo;

/**
 *
 * @author Francisco javier Gómez Gamero
 */
public class Categoria {
    private int idCategoria;
    private String nombre;
    private String tipo; // 'Ingreso' o 'Pago'

    // Constructor
    public Categoria(int idCategoria, String nombre, String tipo) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    // Getters y Setters
    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return nombre; // Devuelve solo el nombre para la visualización en combobox o lista
    }
}

