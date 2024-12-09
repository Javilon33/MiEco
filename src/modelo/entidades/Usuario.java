package modelo.entidades;

import java.util.Date;

/**
 * Clase que representa a un usuario del sistema.
 * Almacena toda la información personal y de acceso del usuario.
 * 
 * @author Francisco Javier Gómez Gamero
 */
public class Usuario {

    private int codigo;
    private int rol;
    private String email;
    private String nombre;
    private String apellidos;
    private String telefono;
    private Date fecha;
    private String password;

    /**
     * Constructor completo para inicializar todos los datos de un usuario.
     * 
     * @param codigo Código único del usuario.
     * @param rol Indica el rol del usuario (0 para ADMIN, 1 para USUARIO, 2 para FAMILIAR)
     * @param email Email del usuario.
     * @param nombre Nombre del usuario.
     * @param apellidos Apellidos del usuario.
     * @param telefono Teléfono del usuario.
     * @param fecha Fecha de nacimiento del usuario.
     * @param password Contraseña encriptada del usuario.
     */
    public Usuario(int codigo, int rol, String email, String nombre, String apellidos, String telefono, Date fecha, String password) {
        this.codigo = codigo;
        this.rol = rol;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.fecha = fecha;
        this.password = password;
    }

    /**
     * Constructor simplificado, útil cuando solo se necesitan los datos básicos.
     * 
     * @param email Email del usuario.
     * @param nombre Nombre del usuario.
     * @param apellidos Apellidos del usuario.
     */
    public Usuario(String email, String nombre, String apellidos) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el código único del usuario.
     * 
     * @return El código del usuario.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Cambia el código único del usuario.
     * 
     * @param codigo Nuevo código del usuario.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Indica si el usuario es administrador.
     * 
     * @return 0 para ADMIN, 1 para USUARIO, 2 para FAMILIAR
     */
    public int getRol() {
        return rol;
    }

    /**
     * Cambia el rol del usuario.
     * 
     * @param rol 0 para ADMIN, 1 para USUARIO, 2 para FAMILIAR
     */
    public void setRol(int rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el email del usuario.
     * 
     * @return El email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Cambia el email del usuario.
     * 
     * @param email Nuevo email del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el nombre del usuario.
     * 
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre del usuario.
     * 
     * @param nombre Nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del usuario.
     * 
     * @return Los apellidos del usuario.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Cambia los apellidos del usuario.
     * 
     * @param apellidos Nuevos apellidos del usuario.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el teléfono del usuario.
     * 
     * @return El teléfono del usuario.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Cambia el teléfono del usuario.
     * 
     * @param telefono Nuevo teléfono del usuario.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la fecha de nacimiento del usuario.
     * 
     * @return La fecha de nacimiento del usuario.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Cambia la de nacimiento del usuario.
     * 
     * @param fecha Nueva Fecha de nacimiento del usuario.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la contraseña encriptada del usuario.
     * 
     * @return La contraseña encriptada del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Cambia la contraseña encriptada del usuario.
     * 
     * @param password Nueva contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
