
package modelo;

/**
 *
 * @author Francisco javier Gómez Gamero
 */
public class Subcategoria {
    private int idSubcategoria;
    private int idCategoria; // Referencia a la categoría padre
    private String nombre;
    private String tipoGasto; // 'Fijo' o 'Variable'

    // Constructor
    public Subcategoria(int idSubcategoria, int idCategoria, String nombre, String tipoGasto) {
        this.idSubcategoria = idSubcategoria;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.tipoGasto = tipoGasto;
    }

    // Getters y Setters
    public int getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(int idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

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

    public String getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(String tipoGasto) {
        this.tipoGasto = tipoGasto;
    }

    @Override
    public String toString() {
        return nombre; // Devuelve solo el nombre para la visualización en combobox o lista
    }
}

