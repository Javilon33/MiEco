package controlador;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import vista.VistaLogin;
import vista.VistaRegistro;

public class ControladorRegistro {

    private final VistaRegistro vista;
    private final VistaLogin vistaLogin;
    private int xMouse, yMouse;

    // Constructor que recibe los componentes necesarios de la vista
    public ControladorRegistro(VistaRegistro vista, VistaLogin vistaLogin) {
        this.vista = vista;
        this.vistaLogin = vistaLogin; // Guardamos la referencia de VistaLogin
        inicializarEventos();
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

        // Evento del botón CERRAR
        vista.exitTxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                vista.exitBtn.setBackground(Color.red);
                vista.exitTxt.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                vista.exitBtn.setBackground(Color.white);
                vista.exitTxt.setForeground(Color.black);
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro que quieres salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    
                    // Oculta la ventana de Registro
                    vista.setVisible(false);
                    
                    // Muestra la ventana de Login
                    vistaLogin.setVisible(true);
                }
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
                    // Oculta la ventana de Registro
                    vista.setVisible(false);
                    
                    // Muestra la ventana de Login
                    vistaLogin.setVisible(true);
                }
            }
        });

        // Eventos para limpiar el texto de ayuda en el campo de usuario
        vista.emailTxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (vista.emailTxt.getText().equals("Ingrese su correo electrónico")) {
                    vista.emailTxt.setText("");
                    vista.emailTxt.setForeground(Color.black);
                }
                if (String.valueOf(vista.passTxt1.getPassword()).isEmpty()) {
                    vista.passTxt1.setText("********");
                    vista.passTxt1.setForeground(Color.gray);
                }
            }
        });

        // Eventos para limpiar el texto de ayuda en el campo de contraseña
        vista.passTxt1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (String.valueOf(vista.passTxt1.getPassword()).equals("********")) {
                    vista.passTxt1.setText("");
                    vista.passTxt1.setForeground(Color.black);
                }
                if (vista.emailTxt.getText().isEmpty()) {
                    vista.emailTxt.setText("Ingrese su correo electrónico");
                    vista.emailTxt.setForeground(Color.gray);
                }
            }
        });
    }
}
