package controlador;

import Utilidades.Seguridad;
import Utilidades.TextPrompt;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;
import javax.swing.*;
import modelo.ConsultaLogin;
import modelo.entidades.Usuario;
import vista.VistaLogin;
import vista.VistaPrincipal;
import vista.VistaRegistro;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorLogin {

    private final VistaLogin vista; //Instancia de la vistaLogin
    private final ConsultaLogin consultaLogin = new ConsultaLogin(); //Instancia de ConsultaLogin
    private int xMouse, yMouse;

    public ControladorLogin(VistaLogin vista) {
        this.vista = vista;
        inicializarEventos();

        //Inicio de los "placeholder" en los campos usando la clase TextPrompt 
        TextPrompt phEmail = new TextPrompt("Introduzca su email", vista.emailTxt);
        TextPrompt phPass = new TextPrompt("Contraseña", vista.passTxt);

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

        // Evento del botón de salida
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
                    System.exit(0);
                }
            }
        });

        // Evento del botón ENTRAR
        vista.loginBtnTxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                vista.loginBtn.setBackground(new Color(0, 156, 223));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                vista.loginBtn.setBackground(new Color(0, 134, 190));
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                
                //Para poder entrar del tiron (BORRAR DESPUES)
                Usuario usuarioLogueado = consultaLogin.obtenerUsuario("admin@mieco.com", "8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92");
                        //Una vez logueado crea y muestra la ventana PRINCIPAL
                        VistaPrincipal vistaPrincipal = new VistaPrincipal();
                        ControladorPrincipal controladorPrincipal = new ControladorPrincipal(vistaPrincipal, usuarioLogueado);
                        vistaPrincipal.setVisible(true);
                        vista.dispose(); //Cierra la ventana de Login
                /*        
                //Comprueba que el campo emailTxt tenga el formato correcto
                String email = vista.emailTxt.getText();
                String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
                Pattern pattern = Pattern.compile(emailRegex);

                if (!pattern.matcher(email).matches()) {
                    JOptionPane.showMessageDialog(vista, "Formato de email incorrecto", "Error", JOptionPane.ERROR_MESSAGE);

                } else if (vista.passTxt.getPassword().length == 0) {
                    //Comprueba que el campo passTxt no esté vacío
                    JOptionPane.showMessageDialog(vista, "Tiene que introducir una contraseña", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Obtiene la contraseña del usuario y la encripta para compararla con la BD
                    String passwordIngresada = String.valueOf(vista.passTxt.getPassword());
                    String passwordEncriptadaIngresada = Seguridad.encriptarSHA256(passwordIngresada);

                    // Verifica las credenciales mediante ConsultaLogin
                    if (consultaLogin.validarUsuario(email, passwordEncriptadaIngresada)) {
                        JOptionPane.showMessageDialog(vista, "Login correcto. Bienvenido " + email, "LOGIN", JOptionPane.INFORMATION_MESSAGE);
                        // Obtener el usuario logueado desde la base de datos
                        Usuario usuarioLogueado = consultaLogin.obtenerUsuario(email, passwordEncriptadaIngresada);
                        //Una vez logueado crea y muestra la ventana PRINCIPAL
                        VistaPrincipal vistaPrincipal = new VistaPrincipal();
                        ControladorPrincipal controladorPrincipal = new ControladorPrincipal(vistaPrincipal, usuarioLogueado);
                        vistaPrincipal.setVisible(true);
                        vista.dispose(); //Cierra la ventana de Login

                    } else {
                        JOptionPane.showMessageDialog(vista, "Email o contraseña incorrecta.", "LOGIN", JOptionPane.ERROR_MESSAGE);
                    }

                }
                */
            }
        });

        // Evento del botón REGISTRARSE
        vista.registroBtnTxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                vista.registroBtn.setBackground(new Color(0, 156, 223));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                vista.registroBtn.setBackground(new Color(0, 134, 190));
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                // Obtener la posición actual de VistaLogin
                int x = vista.getLocationOnScreen().x;
                int y = vista.getLocationOnScreen().y;

                // Crear y mostrar la ventana de Registro
                VistaRegistro vistaRegistro = new VistaRegistro();
                ControladorRegistro controladorRegistro = new ControladorRegistro(vistaRegistro, vista);

                // Establecer la posición de VistaRegistro en la misma posición que VistaLogin
                vistaRegistro.setLocation(x, y);
                vistaRegistro.setVisible(true);
                vista.setVisible(false); //Oculta la ventana de Login
                limpiar(); //Limpia los campos
            }
        });

    }

    //Método para limpiar los campos
    private void limpiar() {
        vista.emailTxt.setText("");
        vista.passTxt.setText("");

    }
}
