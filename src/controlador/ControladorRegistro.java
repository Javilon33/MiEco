package controlador;

import Utilidades.TextPrompt;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import vista.VistaLogin;
import vista.VistaRegistro;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorRegistro {

    private final VistaRegistro vista;
    private final VistaLogin vistaLogin;
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
        TextPrompt phFecha = new TextPrompt("Fecha de nacimiento", vista.fechaTxt);
        TextPrompt phPass1 = new TextPrompt("Contraseña", vista.passTxt1);
        TextPrompt phPass2 = new TextPrompt("Repita contraseña", vista.passTxt2);
        
    }

    private void inicializarEventos() {
        // Eventos para mover la ventana
        vista.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                xMouse = evt.getX();
                yMouse = evt.getY();
            }
        });

        vista.addMouseMotionListener(new MouseAdapter() {
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
                JOptionPane.showMessageDialog(vista, "Intento de guardar con los datos:\nEmail: " + vista.emailTxt.getText()
                        + "\nNombre: " + String.valueOf(vista.nombreTxt.getText()) + "\nApellidos: " + String.valueOf(vista.apellidosTxt.getText())
                        + "\nFecha: " + String.valueOf(vista.fechaTxt.getText()) + "\nContraseña: " + String.valueOf(vista.passTxt1.getPassword()), "GUARDAR", JOptionPane.INFORMATION_MESSAGE);
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
        });

        
    }
}
