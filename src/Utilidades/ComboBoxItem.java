package Utilidades;

/**
 *
 * @author Francisco Javier Gomez Gamero
 */
public class ComboBoxItem {
    private int id;
    private String descripcion;

    public ComboBoxItem(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion; // Muestra solo el texto en el ComboBox
    }
}