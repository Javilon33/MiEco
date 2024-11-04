package modelo;

import java.util.Date;

/**
 *
 * @author Francisco Javier Gómez gamero
 */
public class Usuario {

    private int codigo;        // Código único del usuario
    private int admin;         // Indicador si es administrador (1 para sí, 0 para no)
    private String email;      // Email del usuario
    private String nombre;     // Nombre del usuario
    private String apellidos;  // Apellidos del usuario
    private String telefono;   // Teléfono del usuario
    private Date fecha;      // Fecha de registro o nacimiento
    private String password;   // Contraseña encriptada

    // Constructor completo
    public Usuario(int codigo, int admin, String email, String nombre, String apellidos, String telefono, Date fecha, String password) {
        this.codigo = codigo;
        this.admin = admin;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.fecha = fecha;
        this.password = password;
    }

    // Constructor simplificado para casos donde no necesites todos los datos
    public Usuario(String email, String nombre, String apellidos) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    // Getters y Setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Método para representar el objeto en formato de cadena
    @Override
    public String toString() {
        return "Usuario{"
                + "codigo=" + codigo
                + ", admin=" + admin
                + ", email='" + email + '\''
                + ", nombre='" + nombre + '\''
                + ", apellidos='" + apellidos + '\''
                + ", telefono='" + telefono + '\''
                + ", fecha='" + fecha + '\''
                + ", password='" + password + '\''
                + '}';
    }
}
