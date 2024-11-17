package Utilidades;

import java.util.Objects;

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

    // Sobrescribir equals y hashCode para comparar por id y descripcion
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ComboBoxItem that = (ComboBoxItem) obj;
        return id == that.id && descripcion.equals(that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descripcion);
    }
}
