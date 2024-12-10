package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import modelo.ConsultaFondos;
import modelo.ConsultaMovimientosFondos;
import modelo.entidades.MovimientoFondo;
import rojeru_san.componentes.RSDateChooser;
import vista.Paneles.PanelFichaFondo;

/**
 *
 * @author Francisco Javier Gomez Gamero
 */
public class ControladorMovimientosFondo {

    private final PanelFichaFondo vista; // Vista del panel FichaFondo
    private final ConsultaFondos consultaFondos; // ControladorCuentas para obtener los fondos
    private final ConsultaMovimientosFondos consultaMovimientos; // Modelo para manejar movimientos del fondo
    private final int idFondo; // ID del fondo seleccionado
    private final ControladorFondos controladorFondos; // Referencia al ControladorFondos
    private final NumberFormat formato; //Formato para mostrar los importes correctamente (2 decimales y puntos en los miles)

    public ControladorMovimientosFondo(PanelFichaFondo vista, ConsultaFondos consultaFondos, ConsultaMovimientosFondos consultaMovimientos, int idFondo, ControladorFondos controladorFondos) {
        this.vista = vista;
        this.consultaFondos = consultaFondos;
        this.consultaMovimientos = consultaMovimientos;
        this.idFondo = idFondo;
        this.controladorFondos = controladorFondos;

        // Configurar el formato para importes (una sola vez)
        this.formato = NumberFormat.getInstance(new Locale("es", "ES"));
        formato.setMaximumFractionDigits(2);
        formato.setMinimumFractionDigits(2);

        inicializarEventos();  // Inicializar eventos de la interfaz
        cargarMovimientos(); // Cargar los movimientos al inicio
    }

    // Método para inicializar la vista con el nombre de la cuenta
    private void inicializarEventos() {
        // Obtener los datos del fondo
        String nombreFondo = consultaFondos.obtenerFondoPorId(idFondo).getNombre();
        String isin = consultaFondos.obtenerFondoPorId(idFondo).getIsin();
        String tipo = consultaFondos.obtenerNombreTipoFondo(idFondo);
        String moneda = consultaFondos.obtenerFondoPorId(idFondo).getMoneda();
        Integer riesgo = consultaFondos.obtenerFondoPorId(idFondo).getRiesgo();
        Double valor = consultaFondos.obtenerFondoPorId(idFondo).getCotizacion();
        Double participaciones = consultaFondos.obtenerFondoPorId(idFondo).getParticipaciones();
        Double saldo = participaciones * valor;
        // Establecer el texto del título con el nombre del banco y la cuenta
        vista.etiFondo.setText(nombreFondo);
        vista.etiIsin.setText(isin);
        vista.etiTipo.setText(tipo);
        vista.etiMoneda.setText(moneda);
        vista.etiRiesgo.setText(riesgo.toString());
        vista.etiValor.setText(String.valueOf(valor) + " " + moneda);
        vista.etiParticipaciones.setText(String.valueOf(participaciones));
        vista.etiSaldo.setText(formato.format(saldo) + " " + moneda);

        // Evento para el botón y etiqueta de "Añadir movimiento"
        vista.btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelAddMovimiento();
            }
        });

        // Evento para el botón y etiqueta de "Eliminar movimiento"
        vista.btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                eliminarMovimientoSeleccionado(); // Llama a eliminar movimiento
            }
        });

        // Evento para el botón y etiqueta de "Modificar movimiento"
        vista.btnModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //mostrarPanelModificarMovimiento(); // Llama a modificar movimiento
            }
        });

    }

    // Método para cargar los movimientos del fondo y mostrarlos en la tabla
    private void cargarMovimientos() {

        // Obtener los movimientos de la cuenta desde el controlador
        List<MovimientoFondo> movimientos = consultaMovimientos.obtenerMovimientos(idFondo);

        // Obtener el saldo de la cuenta
        double saldo = 0;//

        // Obtener el modelo de la tabla y limpiar cualquier dato existente
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaMovimientos.getModel();
        modelo.setRowCount(0); // Limpiar las filas de la tabla

        // Añadir los movimientos a la tabla
        for (MovimientoFondo movimiento : movimientos) {
            // Ajustar el saldo actual basado en el tipo de movimiento
            saldo += movimiento.getParticipaciones();

            // Crear un array con los datos del movimiento para cada fila de la tabla
            Object[] fila = {
                movimiento.getIdMovimientoFondo(), // Incluir el id_movimiento (oculto)
                new SimpleDateFormat("dd-MM-yyyy").format(movimiento.getFecha()), //Fecha formateada a dd-MM-yyyy
                movimiento.getTipoMovimiento(),
                movimiento.getParticipaciones(),
                movimiento.getValorLiquidativo(),
                formato.format(movimiento.getImporte()),
                movimiento.getNotas(),
                formato.format(saldo),};
            // Añadir la fila al modelo de la tabla
            modelo.addRow(fila);
        }
        // Oculta la columna del id_movimiento
        vista.tablaMovimientos.getColumnModel().getColumn(0).setMinWidth(0);
        vista.tablaMovimientos.getColumnModel().getColumn(0).setMaxWidth(0);
        vista.tablaMovimientos.getColumnModel().getColumn(0).setPreferredWidth(0);

        ajustarAnchoColumnas(); // Llamamos a este método para ajustar el ancho de las columnas

    }

    //Método para AÑADIR MOVIMIENTOS
    public void mostrarPanelAddMovimiento() {

        // Campos de entrada para los datos
        RSDateChooser campoFecha = new RSDateChooser();
        JComboBox<String> cbTipo = new JComboBox<>();
        JTextField campoNotas = new JTextField(20);
        JTextField campoParticipaciones = new JTextField(10);
        JTextField campoCotizacion = new JTextField(10);

        // Carga inicial de tipos       
        cbTipo.setModel(new DefaultComboBoxModel<>(new String[]{"Compra", "Venta"}));

        // Panel con los componentes
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(campoFecha);
        panel.add(new JLabel("Tipo:"));
        panel.add(cbTipo);
        panel.add(new JLabel("Nº Participaciones:"));
        panel.add(campoParticipaciones);
        panel.add(new JLabel("Cotización actual:"));
        panel.add(campoCotizacion);
        panel.add(new JLabel("Notas:"));
        panel.add(campoNotas);

        // Diálogo para añadir movimiento
        int resultado = JOptionPane.showConfirmDialog(null, panel, "Añadir nuevo movimiento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            if (campoFecha.getDatoFecha() != null) {
                try {
                    // Formatear la fecha
                    String formatoFecha = "yyyy/MM/dd";
                    SimpleDateFormat formato = new SimpleDateFormat(formatoFecha);
                    String fecha = formato.format(campoFecha.getDatoFecha());

                    // Obtener tipo seleccionado
                    int tipoSeleccionado = cbTipo.getSelectedIndex();
                    String tipo = tipoSeleccionado == 0 ? "Compra" : "Venta";

                    String notas = campoNotas.getText();
                    // Verificar si el movimiento es un pago, y si es así, convertir el importe a negativo

                    // Crear un NumberFormat basado en la configuración regional
                    NumberFormat formatoNumero = NumberFormat.getInstance(Locale.getDefault());

                    // Analizar el texto de las participaciones con NumberFormat
                    Number numero = formatoNumero.parse(campoParticipaciones.getText());
                    double participaciones = numero.doubleValue();

                    // Analizar el texto de la cotizacion con NumberFormat
                    Number numero2 = formatoNumero.parse(campoCotizacion.getText());
                    double cotizacion = numero2.doubleValue();

                    //Convierte las participaciones en negativo si es una venta (tipo 2)
                    if ("Venta".equals(tipo)) {
                        participaciones = -participaciones;
                    }

                    double importe = cotizacion * participaciones;

                    // Llama al modelo para añadir el movimiento
                    boolean movimientoInsertado = consultaMovimientos.addMovimiento(idFondo, 1, tipo, fecha, importe, participaciones, cotizacion, notas);

                    if (movimientoInsertado) {
                        JOptionPane.showMessageDialog(vista, "Movimiento añadido correctamente.");
                        cargarMovimientos(); // Refresca la tabla
                        inicializarEventos(); // Actualiza los datos
                        controladorFondos.cargarFondos();

                    } else {
                        JOptionPane.showMessageDialog(vista, "Error al añadir el movimiento. Inténtalo de nuevo.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(vista, "Error en los datos. Verifica los campos.");
                } catch (ParseException ex) {
                    Logger.getLogger(ControladorMovimientosFondo.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(vista, "No has introducido Fecha");
            }
        }

    }

    // ELIMINAR MOVIMIENTO seleccionado en la tabla
    public void eliminarMovimientoSeleccionado() {
        int filaSeleccionada = vista.tablaMovimientos.getSelectedRow(); // Obtiene la fila seleccionada

        if (filaSeleccionada == -1) { // Verifica que haya una selección
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona el movimiento que quieres eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idMovimiento = (int) vista.tablaMovimientos.getValueAt(filaSeleccionada, 0); // ID del movimiento selecionado             

        int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que quieres eliminar el movimiento seleccionado?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            // Intenta eliminar el movimiento
            if (consultaMovimientos.eliminarMovimiento(idMovimiento)) {
                JOptionPane.showMessageDialog(vista, "El movimiento ha sido eliminado correctamente.");
                cargarMovimientos(); // Refresca la tabla de movimientos
                inicializarEventos(); // Actualiza los datos
                controladorFondos.cargarFondos(); // Refresca la tabla de fondos
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar el movimiento.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    //Método para ajustar el ancho de las columnas de la tabla manualmente
    private void ajustarAnchoColumnas() {
        JTable tabla = vista.tablaMovimientos;  // Suponiendo que 'tablaDepositos' es el nombre de la tabla

        // Obtener el modelo de columnas de la tabla
        TableColumnModel columnModel = tabla.getColumnModel();

        // Ajustar el ancho de cada columna (aquí puedes especificar el tamaño que desees para cada columna)
        columnModel.getColumn(0).setPreferredWidth(0);  // Columna 0: ID Movimiento (no se muestra)
        columnModel.getColumn(1).setPreferredWidth(100);  // Columna 0: Fecha
        columnModel.getColumn(2).setPreferredWidth(100); // Columna 1: Tipo
        columnModel.getColumn(3).setPreferredWidth(120); // Columna 2: Participaciones
        columnModel.getColumn(4).setPreferredWidth(100); // Columna 3: Precio
        columnModel.getColumn(5).setPreferredWidth(100); // Columna 4: Importe
        columnModel.getColumn(6).setPreferredWidth(200); // Columna 5: Notas 
        columnModel.getColumn(7).setPreferredWidth(140); // Columna 5: Total

    }

}
