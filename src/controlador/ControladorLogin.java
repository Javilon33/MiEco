
package controlador;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class ControladorLogin {
    private final JFrame vista;
    private final JLabel exitTxt, loginBtnTxt;
    private final JPanel exitBtn, loginBtn;
    private final JTextField userTxt;
    private final JPasswordField passTxt;
    private int xMouse, yMouse;

    // Constructor que recibe los componentes necesarios de la vista
    public ControladorLogin(JFrame vista, JLabel exitTxt, JPanel exitBtn, JLabel loginBtnTxt, JPanel loginBtn, JTextField userTxt, JPasswordField passTxt) {
        this.vista = vista;
        this.exitTxt = exitTxt;
        this.exitBtn = exitBtn;
        this.loginBtnTxt = loginBtnTxt;
        this.loginBtn = loginBtn;
        this.userTxt = userTxt;
        this.passTxt = passTxt;

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

        // Evento del botón de salida
        exitTxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                exitBtn.setBackground(Color.red);
                exitTxt.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                exitBtn.setBackground(Color.white);
                exitTxt.setForeground(Color.black);
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro que quieres salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
                        if (opcion == JOptionPane.YES_OPTION) {
                            System.exit(0);
                        }                
            }
        });

        // Evento del botón de login
        loginBtnTxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                loginBtn.setBackground(new Color(0, 156, 223));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                loginBtn.setBackground(new Color(0, 134, 190));
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                JOptionPane.showMessageDialog(vista, "Intento de login con los datos:\nUsuario: " + userTxt.getText() +
                        "\nContraseña: " + String.valueOf(passTxt.getPassword()), "LOGIN", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Eventos para limpiar el texto de ayuda en el campo de usuario
        userTxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (userTxt.getText().equals("Ingrese su nombre de usuario")) {
                    userTxt.setText("");
                    userTxt.setForeground(Color.black);
                }
                if (String.valueOf(passTxt.getPassword()).isEmpty()) {
                    passTxt.setText("********");
                    passTxt.setForeground(Color.gray);
                }
            }
        });

        // Eventos para limpiar el texto de ayuda en el campo de contraseña
        passTxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (String.valueOf(passTxt.getPassword()).equals("********")) {
                    passTxt.setText("");
                    passTxt.setForeground(Color.black);
                }
                if (userTxt.getText().isEmpty()) {
                    userTxt.setText("Ingrese su nombre de usuario");
                    userTxt.setForeground(Color.gray);
                }
            }
        });
    }
}
