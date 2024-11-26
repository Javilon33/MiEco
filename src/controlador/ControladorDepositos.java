package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultaDepositos;
import modelo.entidades.Deposito;
import modelo.entidades.Usuario;
import rojeru_san.componentes.RSDateChooser;
import vista.Paneles.PanelDepositos;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorDepositos {

    private final PanelDepositos vista; // La vista donde se muestran los depósitos
    private final ConsultaDepositos consultaDepositos; // El modelo para obtener los datos de los depósitos
    private final Usuario usuario; // Usuario actual para obtener sus datos

    //Formato para mostrar los importes correctamente (2 decimales y puntos en los miles)
    NumberFormat formato = NumberFormat.getInstance(new Locale("es", "ES"));

    public ControladorDepositos(PanelDepositos vista, ConsultaDepositos consultaDepositos, Usuario usuario) {
        this.vista = vista;
        this.consultaDepositos = consultaDepositos;
        this.usuario = usuario;
        inicializarEventos(); // Inicializar eventos de la interfaz
        try {
            cargarDepositos(); // Cargar los depositos en la tabla al inicio        
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(ControladorDepositos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ControladorDepositos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ControladorDepositos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Configura eventos básicos (clicks en botones y etiquetas)
    public void inicializarEventos() {

        // Evento para el botón y etiqueta de "Añadir depósito"
        vista.btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelAddDeposito(); // Abre el panel para agregar depósitos
            }
        });
        vista.etiAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelAddDeposito();
            }
        });

        // Evento para el botón y etiqueta de "Eliminar depósito"
        vista.btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                try {
                    eliminarDepositoSeleccionado(); // Llama a eliminar el depósito
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(ControladorDepositos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        vista.etiElminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                try {
                    eliminarDepositoSeleccionado(); // Llama a eliminar el depósito
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(ControladorDepositos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Evento para el botón y etiqueta de "Modificar depósito"
        vista.btnModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelModificarDeposito(); // Abre el panel para modificar depósito
            }
        });
        vista.etiModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelModificarDeposito(); // Abre el panel para modificar depósito
            }
        });

        //Formato de los importes 
        formato.setMaximumFractionDigits(2);
        formato.setMinimumFractionDigits(2);

    }

    public void cargarDepositos() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        //Trae las cuentas del usuario
        List<Deposito> depositos = consultaDepositos.obtenerDepositos(usuario.getCodigo());

        //Paneles de depositos invisibles por defecto al cargar
        vista.panelDeposito1.setVisible(false);
        vista.panelDeposito2.setVisible(false);
        vista.panelDeposito3.setVisible(false);
        vista.panelDeposito4.setVisible(false);
        vista.panelDeposito5.setVisible(false);
        vista.panelDeposito6.setVisible(false);
        vista.panelDeposito7.setVisible(false);
        vista.panelDeposito8.setVisible(false);
        

        //Formato de los importes 
        formato.setMaximumFractionDigits(2);
        formato.setMinimumFractionDigits(2);

        // Configura el modelo de la tabla para limpiar datos previos
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaDepositos.getModel();
        modelo.setRowCount(0); // Limpiar las filas

        // Rellena la tabla con los datos de los depósitos
        String finalizado = "";
        Date hoy = new Date(); // Fecha actual
        for (Deposito deposito : depositos) {
            //Marca el depósito cono vencido si ha pasado la fecha de vencimiento
            if (deposito.getFechaVencimiento().before(hoy)) {
                finalizado = "SI";
            } else {
                finalizado = "NO";
            }
            //Rellena la tabla
            Object[] fila = {
                deposito.getIdDeposito(),
                deposito.getNombre(),
                deposito.getBanco(),
                new SimpleDateFormat("dd-MM-yyyy").format(deposito.getFechaInicio()), //Fecha formateada a dd-MM-yyyy
                deposito.getMeses(),
                formato.format(deposito.getImporteInicial()) + " €", // Formatear a 2 decimales
                deposito.getInteresAnual() + "%",
                formato.format(deposito.getImporteFinal()) + " €", // Formatear a 2 decimales
                finalizado
            };
            modelo.addRow(fila);
        }

        //Activar paneles según los depósitos que haya y rellenamos con los datos de la tabla
        for (int i = 0; i < Math.min(depositos.size(), 8); i++) {
            // Mostrar el panel correspondiente
            JPanel panel = (JPanel) vista.getClass().getDeclaredField("panelDeposito" + (i + 1)).get(vista);            
            panel.setVisible(true);

            // Configurar etiquetas dinámicamente
            JLabel etiDeposito = (JLabel) vista.getClass().getDeclaredField("etiDeposito" + (i + 1)).get(vista);
            etiDeposito.setText("DEPÓSITO Nº " + vista.tablaDepositos.getModel().getValueAt(i, 0).toString());

            JLabel etiFecha = (JLabel) vista.getClass().getDeclaredField("etiFecha" + (i + 1)).get(vista);
            etiFecha.setText(vista.tablaDepositos.getModel().getValueAt(i, 3).toString());
                      
            JLabel etiImporteInicial = (JLabel) vista.getClass().getDeclaredField("etiImporteInicial" + (i + 1)).get(vista);
            etiImporteInicial.setText(vista.tablaDepositos.getModel().getValueAt(i, 5).toString());

            JLabel etiImporteFinal = (JLabel) vista.getClass().getDeclaredField("etiImporteFinal" + (i + 1)).get(vista);
            etiImporteFinal.setText(vista.tablaDepositos.getModel().getValueAt(i, 7).toString());

            // Calcular y mostrar el beneficio
            Deposito deposito = depositos.get(i); //Se extrae el depósito de la lista
            double beneficio = deposito.getImporteFinal() - deposito.getImporteInicial(); //Se calcula el beneficio neto
            JLabel etiBeneficio = (JLabel) vista.getClass().getDeclaredField("etiBeneficio" + (i + 1)).get(vista);
            etiBeneficio.setText(String.valueOf(formato.format(beneficio)) + " €"); //formateado a 2 decimales
            
            //Etiqueta Fecha Fin calculando la fecha
            String formatoFecha = "dd/MM/yyyy";
            SimpleDateFormat formatt = new SimpleDateFormat(formatoFecha);
            JLabel fechaFin = (JLabel) vista.getClass().getDeclaredField("fechaFin" + (i + 1)).get(vista);
            fechaFin.setText(formatt.format(deposito.getFechaVencimiento()));
            
            //Comprobar si está vencido para mostrar el panelVencido y marcar el depósito
            JPanel panelVencido = (JPanel) vista.getClass().getDeclaredField("panelVencido" + (i + 1)).get(vista);
            if(deposito.getFechaVencimiento().before(hoy)){
                panelVencido.setVisible(true);
            }else{
                panelVencido.setVisible(false);
            }
            
        }
    }

    //Muestra un panel emergente para AÑADIR un Deposito nuevo
    public void mostrarPanelAddDeposito() {
        // Campos de entrada para los datos del depósito
        JTextField campoNombre = new JTextField(20);
        JTextField campoBanco = new JTextField(15);
        RSDateChooser fechaInicio = new RSDateChooser();
        JTextField campoMeses = new JTextField(5);
        JTextField campoImporte = new JTextField(15);
        JTextField campoInteres = new JTextField(5);

        // Añade los campos al panel
        JPanel panel = new JPanel();
        panel.add(fechaInicio);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Nombre del depósito:"));
        panel.add(campoNombre);
        panel.add(new JLabel("Banco:"));
        panel.add(campoBanco);
        panel.add(new JLabel("Duración (meses):"));
        panel.add(campoMeses);
        panel.add(new JLabel("Importe:"));
        panel.add(campoImporte);
        panel.add(new JLabel("Interés anual:"));
        panel.add(campoInteres);

        // Abre un diálogo para añadir el depósito 
        int resultado = JOptionPane.showConfirmDialog(vista, panel, "Añadir nuevo depósito", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (fechaInicio.getDatoFecha() != null) {
            if (resultado == JOptionPane.OK_OPTION) {
                try {                    
                    //Obtener los datos
                    String formatoFecha = "yyyy/MM/dd";
                    SimpleDateFormat formatt = new SimpleDateFormat(formatoFecha);
                    String fecha = formatt.format(fechaInicio.getDatoFecha());
                    int meses = Integer.parseInt(campoMeses.getText());
                    String nombre = campoNombre.getText();
                    String banco = campoBanco.getText();
                    double importe = Double.parseDouble(campoImporte.getText());
                    double interes = Double.parseDouble(campoInteres.getText());

                    // Llama al modelo para añadir el depósito
                    boolean depositoInsertado = consultaDepositos.addDeposito(usuario.getCodigo(), nombre, banco, fecha, meses, importe, interes);

                    if (depositoInsertado) {
                        JOptionPane.showMessageDialog(vista, "Depósito añadido correctamente.");
                        cargarDepositos(); // Refresca la tabla
                    } else {
                        JOptionPane.showMessageDialog(vista, "Error al añadir el depósito. Inténtalo de nuevo.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(vista, "Error en los datos. Verifica los campos.");
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(ControladorDepositos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(vista, "No has introducido Fecha");
        }

    }
    
    //Muestra un panel emergente para MODIFICAR el Deposito seleccionado
    public void mostrarPanelModificarDeposito() {        
        int filaSeleccionada = vista.tablaDepositos.getSelectedRow(); // Obtiene la fila seleccionada

        if (filaSeleccionada == -1) { // Verifica que haya una selección
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona un depósito en la lista para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Recupera los datos del depósito seleccionado por idDeposito
        int idDeposito = (int) vista.tablaDepositos.getValueAt(filaSeleccionada, 0);
        Deposito deposito = consultaDepositos.obtenerDepositoPorId(idDeposito); 
        
        if (deposito == null) {
            JOptionPane.showMessageDialog(vista, "No se pudo recuperar la información del deposito.");
            return;
        }
        
        // Campos de entrada para los datos del depósito
        RSDateChooser campoFecha = new RSDateChooser();
        campoFecha.setDatoFecha(deposito.getFechaInicio());
        JTextField campoNombre = new JTextField(deposito.getNombre(),20);
        JTextField campoBanco = new JTextField(deposito.getBanco(),15);        
        JTextField campoMeses = new JTextField(String.valueOf(deposito.getMeses()),5);
        JTextField campoImporte = new JTextField(String.valueOf(deposito.getImporteInicial()), 15);
        JTextField campoInteres = new JTextField(String.valueOf(deposito.getInteresAnual()), 5);

        // Añade los campos al panel
        JPanel panel = new JPanel();        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Fecha:"));
        panel.add(campoFecha);
        panel.add(new JLabel("Nombre del depósito:"));
        panel.add(campoNombre);
        panel.add(new JLabel("Banco:"));
        panel.add(campoBanco);
        panel.add(new JLabel("Duración (meses):"));
        panel.add(campoMeses);
        panel.add(new JLabel("Importe:"));
        panel.add(campoImporte);
        panel.add(new JLabel("Interés anual:"));
        panel.add(campoInteres);
        
        // Mostrar diálogo
        int resultado = JOptionPane.showConfirmDialog(null, panel, "Modificar Depósito", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                // Validar y obtener datos
                String formatoFecha = "yyyy/MM/dd";
                SimpleDateFormat formatt = new SimpleDateFormat(formatoFecha);
                String fecha = formatt.format(campoFecha.getDatoFecha());  
                String nombre = campoNombre.getText();
                String banco = campoBanco.getText();
                int meses = Integer.parseInt(campoMeses.getText());
                double importe = Double.parseDouble(campoImporte.getText());
                double interes = Double.parseDouble(campoInteres.getText());
                
                // Actualizar el depóstito
                boolean actualizado = consultaDepositos.modificarDeposito(deposito.getIdDeposito(), nombre, banco, fecha, meses, importe, interes);

                if (actualizado) {
                    JOptionPane.showMessageDialog(vista, "Depósito modificado correctamente.");
                    cargarDepositos(); // Refresca la tabla
                    
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al modificar el depósito.");
                }
            } catch ( NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Error en los datos. Verifica los campos.");
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(ControladorDepositos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // ELIMINAR el depósito seleccionado en la tabla
    public void eliminarDepositoSeleccionado() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        int filaSeleccionada = vista.tablaDepositos.getSelectedRow(); // Obtiene la fila seleccionada

        if (filaSeleccionada == -1) { // Verifica que haya una selección
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona un depósito en la lista para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idDeposito = (int) vista.tablaDepositos.getValueAt(filaSeleccionada, 0); // ID del depósito seleccionado

        // Pregunta si el usuario realmente quiere eliminar el depósito
        int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que quieres eliminar el depósito seleccionado?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            // Llama al modelo para eliminar la cuenta
            if (consultaDepositos.eliminarDeposito(idDeposito)) {
                JOptionPane.showMessageDialog(vista, "El depósito ha sido eliminado correctamente.");

                // Recarga la tabla y actualiza los paneles
                cargarDepositos();

            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}
