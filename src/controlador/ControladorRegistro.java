package controlador;

import Utilidades.Seguridad;
import Utilidades.TextPrompt;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import javax.swing.*;
import modelo.ConsultaRegistro;
import vista.VistaLogin;
import vista.VistaRegistro;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorRegistro {

    private final VistaRegistro vista;
    private final VistaLogin vistaLogin;
    private final ConsultaRegistro consultaRegistro = new ConsultaRegistro(); // Instancia de ConsultaRegistro
    private int xMouse, yMouse;

    // Constructor que recibe los componentes necesarios de la vista
    public ControladorRegistro(VistaRegistro vista, VistaLogin vistaLogin) {
        this.vista = vista;
        this.vistaLogin = vistaLogin; // Guardamos la referencia de VistaLogin
        inicializarEventos();

        //Inicio de los "placeholder" en los campos usando la clase TextPrompt 
        TextPrompt phEmail = new TextPrompt("Introduzca su email", vista.emailTxt);
        TextPrompt phNombre = new TextPrompt("Nombre", vista.nombreTxt);
        TextPrompt phApellidos = new TextPrompt("Apellidos", vista.apellidosTxt);
        TextPrompt phTelefono = new TextPrompt("Nº de teléfono", vista.telefonoTxt);
        TextPrompt phPass1 = new TextPrompt("Contraseña", vista.passTxt1);
        TextPrompt phPass2 = new TextPrompt("Repita contraseña", vista.passTxt2);

    }

    private void inicializarEventos() {
        // Eventos para mover la ventana
        vista.header.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                xMouse = evt.getX();
                yMouse = evt.getY();
            }
        });

        vista.header.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                int x = evt.getXOnScreen();
                int y = evt.getYOnScreen();
                vista.setLocation(x - xMouse, y - yMouse);
            }
        });

        // Evento del botón GUARDAR
        vista.guardarBtnTxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                vista.guardarBtnTxt.setBackground(new Color(0, 156, 223));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                vista.guardarBtnTxt.setBackground(new Color(0, 134, 190));
            }

            @Override
            public void mouseClicked(MouseEvent evt) {

                String fechaFormateada = "";
                String password1 = String.valueOf(vista.passTxt1.getPassword());
                String password2 = String.valueOf(vista.passTxt2.getPassword());

                //Comprobamos todos los datos del registro
                //Comprueba que el campo emailTxt tenga el formato correcto
                String email = vista.emailTxt.getText();
                String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
                Pattern pattern = Pattern.compile(emailRegex);

                if (!pattern.matcher(email).matches()) {
                    JOptionPane.showMessageDialog(vista, "Formato de email incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                } //Comprueba que el resto de campos no estén vacíos    
                else if (vista.nombreTxt.getText().isBlank()) {
                    JOptionPane.showMessageDialog(vista, "El campo NOMBRE no puede estar vacío ", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (vista.apellidosTxt.getText().isBlank()) {
                    JOptionPane.showMessageDialog(vista, "El campo APELLIDOS no puede estar vacío ", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (vista.telefonoTxt.getText().isBlank()) {
                    JOptionPane.showMessageDialog(vista, "El campo TELEFONO no puede estar vacío ", "Error", JOptionPane.ERROR_MESSAGE);
                } //Comprueba la fecha           
                else if (vista.fechaNac.getDatoFecha() == null) {
                    JOptionPane.showMessageDialog(vista, "Selecciona una FECHA de nacimiento válida", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (vista.passTxt1.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(vista, "El campo CONTRASEÑA no puede estar vacío ", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (password1.equals(password2)) {
                    //FORMATO A LA FECHA    
                    String formatoFecha = "yyyy/MM/dd";
                    Date fechaDate = vista.fechaNac.getDatoFecha();
                    SimpleDateFormat formato = new SimpleDateFormat(formatoFecha);
                    fechaFormateada = formato.format(fechaDate);
                    
                    //Encriptación de la contraseña
                    String passwordOriginal = String.valueOf(vista.passTxt1.getPassword());
                    String passwordEncriptada = Seguridad.encriptarSHA256(passwordOriginal);
                    
                    // Obtiene los datos del formulario                    
                    //String email = vista.emailTxt.getText();
                    String nombre = vista.nombreTxt.getText();
                    String apellidos = vista.apellidosTxt.getText();
                    String telefono = vista.telefonoTxt.getText();
                    String fecha = fechaFormateada;
                    String password = passwordEncriptada;

                    // Intenta registrar el usuario
                    if (consultaRegistro.registrarUsuario(email, nombre, apellidos, telefono, fecha, password)) {
                        JOptionPane.showMessageDialog(vista, "Usuario registrado correctamente", "Registro", JOptionPane.INFORMATION_MESSAGE);
                        // Cerrar o limpiar la vista si es necesario
                        cerrarRegistro();
                        
                    } else {
                        JOptionPane.showMessageDialog(vista, "Error al registrar usuario. El email \"" + email + "\" ya está registrado", "Registro", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(vista, "Las CONTRASEÑAS introducidas no coinciden", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Evento del botón CANCELAR
        vista.cancelarBtnTxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                vista.cancelarBtn.setBackground(new Color(0, 156, 223));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                vista.cancelarBtn.setBackground(new Color(0, 134, 190));
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro que quieres cancelar? los datos no serán guardados", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    cerrarRegistro();
                }
            }
        });

    }
    
    //Método para cerrar la ventana
    private void cerrarRegistro(){
        // Obtener la posición actual de VistaRegistro
                    int x = vista.getLocationOnScreen().x;
                    int y = vista.getLocationOnScreen().y;

                    // Ocultar la ventana de Registro
                    vista.setVisible(false);

                    // Establecer la posición de VistaLogin en la misma posición que VistaRegistro
                    vistaLogin.setLocation(x, y);
                    vistaLogin.setVisible(true);
    }
    
}
