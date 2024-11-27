package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private final NumberFormat formato; //Formato para mostrar los importes correctamente (2 decimales y puntos en los miles)
  
    public ControladorDepositos(PanelDepositos vista, ConsultaDepositos consultaDepositos, Usuario usuario) {
        this.vista = vista;
        this.consultaDepositos = consultaDepositos;
        this.usuario = usuario;
        
        // Configurar el formato para importes (una sola vez)
        this.formato = NumberFormat.getInstance(new Locale("es", "ES"));
        formato.setMaximumFractionDigits(2);
        formato.setMinimumFractionDigits(2);
                
        inicializarEventos(); // Inicializar eventos de la interfaz
        cargarDepositos(); // Cargar los depositos en la tabla al inicio          
    }

    // Configura eventos básicos (clicks en botones y etiquetas)
    public void inicializarEventos() {
        
        //Muestra el saldo actual de todos los depósitos (con 2 decimales)
        double saldoTotal = consultaDepositos.obtenerTotalDepositos(usuario.getCodigo());
        vista.etiTotal.setText(formato.format(+saldoTotal)+ " €");
        

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

    }
    
    //Método para cargar los depósitos desde la BD
    public void cargarDepositos() {
        
        //Muestra el saldo actual de todos los depósitos (con 2 decimales)
        double saldoTotal = consultaDepositos.obtenerTotalDepositos(usuario.getCodigo());
        vista.etiTotal.setText(formato.format(+saldoTotal)+ " €");
        
        try {
            List<Deposito> depositos = consultaDepositos.obtenerDepositos(usuario.getCodigo());
            configurarPaneles(depositos);
            rellenarTablaDepositos(depositos);
        } catch (Exception ex) {
            Logger.getLogger(ControladorDepositos.class.getName()).log(Level.SEVERE, "Error al cargar depósitos", ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar los depósitos. Inténtelo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Activa los paneles según los depósitos que haya
    private void configurarPaneles(List<Deposito> depositos) throws NoSuchFieldException, IllegalAccessException {
        Date hoy = new Date();
        for (int i = 0; i < 8; i++) {
            JPanel panel = (JPanel) vista.getClass().getDeclaredField("panelDeposito" + (i + 1)).get(vista);
            panel.setVisible(i < depositos.size());

            if (i < depositos.size()) {
                Deposito deposito = depositos.get(i);
                actualizarEtiquetasPanel(panel, deposito, hoy, i + 1);
            }
        }
        //He tenido que incluir revalidate y repaint para hacer un refresco después de cada cambio ya que el panel vencido salía mal
        vista.revalidate();
        vista.repaint();
    }
    
    //Rellena las etiquetas de los paneles activos
    private void actualizarEtiquetasPanel(JPanel panel, Deposito deposito, Date hoy, int indice) throws NoSuchFieldException, IllegalAccessException {
        JLabel etiDeposito = (JLabel) vista.getClass().getDeclaredField("etiDeposito" + indice).get(vista);
        etiDeposito.setText("DEPÓSITO Nº " + deposito.getIdDeposito());

        JLabel etiFecha = (JLabel) vista.getClass().getDeclaredField("etiFecha" + indice).get(vista);
        etiFecha.setText(new SimpleDateFormat("dd-MM-yyyy").format(deposito.getFechaInicio()));

        JLabel etiImporteInicial = (JLabel) vista.getClass().getDeclaredField("etiImporteInicial" + indice).get(vista);
        etiImporteInicial.setText(formato.format(deposito.getImporteInicial()) + " €");

        JLabel etiImporteFinal = (JLabel) vista.getClass().getDeclaredField("etiImporteFinal" + indice).get(vista);
        etiImporteFinal.setText(formato.format(deposito.getImporteFinal()) + " €");

        double beneficio = deposito.getImporteFinal() - deposito.getImporteInicial();
        JLabel etiBeneficio = (JLabel) vista.getClass().getDeclaredField("etiBeneficio" + indice).get(vista);
        etiBeneficio.setText(formato.format(beneficio) + " €");

        JLabel fechaFin = (JLabel) vista.getClass().getDeclaredField("fechaFin" + indice).get(vista);
        fechaFin.setText(new SimpleDateFormat("dd-MM-yyyy").format(deposito.getFechaVencimiento()));

        JPanel panelVencido = (JPanel) vista.getClass().getDeclaredField("panelVencido" + indice).get(vista);
        panelVencido.setVisible(deposito.getFechaVencimiento().before(hoy));
    }
    
    //Rellena la tabla con los depósitos
    private void rellenarTablaDepositos(List<Deposito> depositos) {
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaDepositos.getModel();
        modelo.setRowCount(0);

        for (Deposito deposito : depositos) {
            Object[] fila = {
                deposito.getIdDeposito(),
                deposito.getNombre(),
                deposito.getBanco(),
                new SimpleDateFormat("dd-MM-yyyy").format(deposito.getFechaInicio()),
                deposito.getMeses(),
                //String.format("%.2f", deposito.getImporteInicial())+ " €", // Formatear saldo a 2 decimales
                formato.format(deposito.getImporteInicial()) + " €",
                //String.format("%.2f", deposito.getInteresAnual())+ " %", // Formatear saldo a 2 decimales
                formato.format(deposito.getInteresAnual()) + "%",
                //String.format("%.2f", deposito.getImporteFinal())+ " €", // Formatear saldo a 2 decimales
                formato.format(deposito.getImporteFinal()) + " €",                
                deposito.getFechaVencimiento().before(new Date()) ? "SI" : "NO"
            };
            modelo.addRow(fila);
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
                    String fecha = new SimpleDateFormat("yyyy/MM/dd").format(fechaInicio.getDatoFecha());
                    int meses = Integer.parseInt(campoMeses.getText());
                    String nombre = campoNombre.getText();
                    String banco = campoBanco.getText();
                    
                    // Crear un NumberFormat basado en la configuración regional
                     NumberFormat formatoNumero = NumberFormat.getInstance(Locale.getDefault());
                    // Analizar el texto del importe con NumberFormat
                    Number numero = formatoNumero.parse(campoImporte.getText());
                    double importe = numero.doubleValue();
                    // Analizar el texto del importe con NumberFormat
                    Number numero2 = formatoNumero.parse(campoInteres.getText());
                    double interes = numero2.doubleValue();

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
                } catch (IllegalArgumentException | ParseException ex) {
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
                // Normalizar el importe para que acepte comas
                String importeTexto = campoImporte.getText().replace(",", ".");
                double importe = Double.parseDouble(importeTexto);
                // Normalizar el importe para que acepte comas
                String interesTexto = campoInteres.getText().replace(",", ".");
                double interes = Double.parseDouble(interesTexto);
                
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
            } catch (IllegalArgumentException ex) {
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
