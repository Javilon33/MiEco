package controlador;

import Utilidades.WebScrapingQueFondos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import modelo.ConsultaFondos;
import modelo.ConsultaMovimientosFondos;
import modelo.entidades.Fondo;
import modelo.entidades.Usuario;
import vista.Paneles.PanelFichaFondo;
import vista.Paneles.PanelFondos;
import vista.componentes.IconRendererEditor2;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorFondos {

    private final PanelFondos vista; // La vista donde se muestra el panel Fondos
    private final ConsultaFondos consultaFondos; // El modelo para obtener los datos de los Fondos de Inversion
    private final Usuario usuario; // Usuario actual para obtener sus datos
    private final NumberFormat formato; //Formato para mostrar los importes correctamente (2 decimales y puntos en los miles)
    private final ConsultaMovimientosFondos consultaMovimientos;

    public ControladorFondos(PanelFondos vista, ConsultaFondos consultaFondos, Usuario usuario, ConsultaMovimientosFondos consultaMovimientos) {
        this.vista = vista;
        this.consultaFondos = consultaFondos;
        this.usuario = usuario;
        this.consultaMovimientos = consultaMovimientos;

        // Configurar el formato para importes (una sola vez)
        this.formato = NumberFormat.getInstance(new Locale("es", "ES"));
        formato.setMaximumFractionDigits(2);
        formato.setMinimumFractionDigits(2);

        inicializarEventos(); // Inicializar eventos de la interfaz
        cargarFondos(); // Cargar los fondos en la tabla al inicio       
    }

    // Configura eventos básicos (clicks en botones y etiquetas)
    public void inicializarEventos() {

        // Evento para el botón y etiqueta de "Añadir fondo"
        vista.btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelAddFondo(); // Abre el panel para agregar fondos
            }
        });

        // Evento para el botón y etiqueta de "Eliminar fondo"
        vista.btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                eliminarFondoSeleccionado(); // Llama a eliminar fondo
            }
        });

        // Evento para el botón y etiqueta de "Modificar fondo"
        vista.btnModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelModificarFondo(); // Llama a modificar fondo
            }
        });
    }

    // Cargar y mostrar los fondos del usuario en la tabla
    public void cargarFondos() {

        //Trae las cuentas del usuario
        List<Fondo> fondos = consultaFondos.obtenerFondos(usuario.getCodigo());

        // Configura el modelo de la tabla para limpiar datos previos
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaFondos.getModel();
        modelo.setRowCount(0); // Limpiar las filas
        
        //Establece la suma total de los fondos
        double total = consultaFondos.obtenerSaldoTotal(usuario.getCodigo());
        vista.etiTotal.setText(formato.format(total)+ " €");

        // Rellena la tabla con los datos de las cuentas
        for (Fondo fondo : fondos) {
            Map<String, Object> datosFondo = WebScrapingQueFondos.obtenerDatosFondo(fondo.getIsin());
            String nombreFondo = (String) datosFondo.get("nombre");
            Object cotizacion = datosFondo.get("cotizacion");
            consultaFondos.actualizarCotizacion(fondo.getIdFondo(), (double)cotizacion); //Aprovecho la consulta para actualizar la cotizacion actual en la BD
            String categoriaVDOS = (String) datosFondo.get("categoriaVDOS");
            String divisa = (String) datosFondo.get("divisa");
            double valor = fondo.getParticipaciones() * fondo.getCotizacion();

            Object[] fila = {
                fondo.getIdFondo(),
                fondo.getNombre(),
                fondo.getIsin(),
                consultaFondos.obtenerNombreTipoFondo(fondo.getIdFondo()),
                cotizacion,
                formato.format(valor)+" "+fondo.getMoneda(),
                "Ver ficha" // Esto se verá como el botón de ficha
            };
            modelo.addRow(fila);
        }

        // Configura el icono en la columna de detalles para abrir el panel movimientos
        vista.tablaFondos.getColumnModel().getColumn(6).setCellRenderer(new IconRendererEditor2(vista.tablaFondos, this));
        vista.tablaFondos.getColumnModel().getColumn(6).setCellEditor(new IconRendererEditor2(vista.tablaFondos, this));

        // Elimina cualquier MouseListener previo en la columna de detalles
        for (MouseListener listener : vista.tablaFondos.getMouseListeners()) {
            vista.tablaFondos.removeMouseListener(listener);
        }

        // Añade el listener solo para la columna de detalles
        vista.tablaFondos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vista.tablaFondos.rowAtPoint(e.getPoint());
                int columna = vista.tablaFondos.columnAtPoint(e.getPoint());

                // Actúa solo si el clic fue en la columna de detalles (columna 5)
                if (columna == 6) {
                    mostrarPanelMovimientos(fila);
                } else {
                    // Permite la selección de filas si el clic no es en la columna de detalles
                    vista.tablaFondos.setRowSelectionInterval(fila, fila);
                }
            }
        });

        ajustarAnchoColumnas(); // Llamamos a este método para ajustar el ancho de las columnas

    }

    //Método para AÑADIR un fondo
    public void mostrarPanelAddFondo() {
        // Campos de entrada para el ISIN
        JTextField campoIsin = new JTextField(15);
        JButton botonBuscar = new JButton("Buscar");

        // Campos de texto para mostrar los datos del fondo (estos estarán deshabilitados inicialmente)
        JTextField campoNombre = new JTextField(15);
        JTextField campoCategoria = new JTextField(15);
        JTextField campoDivisa = new JTextField(15);
        JTextField campoCotizacion = new JTextField(15);

        // Deshabilitamos los campos de texto para que no se editen directamente
        campoNombre.setEditable(false);
        campoCategoria.setEditable(false);
        campoDivisa.setEditable(false);
        campoCotizacion.setEditable(false);

        // Panel para mostrar los campos
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("ISIN:"));
        panel.add(campoIsin);
        panel.add(botonBuscar);
        panel.add(new JLabel("Nombre del Fondo:"));
        panel.add(campoNombre);
        panel.add(new JLabel("Categoría:"));
        panel.add(campoCategoria);
        panel.add(new JLabel("Divisa:"));
        panel.add(campoDivisa);
        panel.add(new JLabel("Cotización:"));
        panel.add(campoCotizacion);

        // Acción del botón de búsqueda
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isin = campoIsin.getText().trim();
                if (isin.isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "Por favor, introduce un ISIN válido.");
                    return;
                }

                // Llama al método WebScrapingQueFondos para obtener los datos del fondo
                Map<String, Object> datosFondo = WebScrapingQueFondos.obtenerDatosFondo(isin);

                // Rellena los campos con los datos obtenidos
                campoNombre.setText((String) datosFondo.get("nombre"));
                campoCategoria.setText((String) datosFondo.get("categoriaVDOS"));
                campoDivisa.setText((String) datosFondo.get("divisa"));
                campoCotizacion.setText(datosFondo.get("cotizacion").toString());
            }
        });

        // Mostramos el panel en un cuadro de diálogo
        int resultado = JOptionPane.showConfirmDialog(vista, panel, "Añadir nuevo fondo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            // Aquí puedes añadir la lógica para guardar el fondo en la base de datos o realizar cualquier otra acción
            String isin = campoIsin.getText().trim();
            String nombre = campoNombre.getText().trim();
            String categoria = campoCategoria.getText().trim();
            String divisa = campoDivisa.getText().trim();

            // Valida que los campos no estén vacíos
            if (isin.isEmpty() || nombre.isEmpty() || categoria.isEmpty() || divisa.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios. Por favor, rellénalos.");
            } else {
                // Aquí iría la lógica para añadir el fondo a la base de datos
                boolean fondoInsertado = consultaFondos.addFondo(usuario.getCodigo(),nombre,categoria, isin, divisa);
                if (fondoInsertado) {
                    JOptionPane.showMessageDialog(vista, "Fondo añadido correctamente.");
                    // Actualiza la vista, si es necesario
                    cargarFondos();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al añadir el fondo. El ISIN ya existe.");
                }
            }
        }
    }
    
    // Elimina la cuenta seleccionada en la tabla
    public void eliminarFondoSeleccionado() {
        int filaSeleccionada = vista.tablaFondos.getSelectedRow(); // Obtiene la fila seleccionada

        if (filaSeleccionada == -1) { // Verifica que haya una selección
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona un fondo para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idFondo = (int) vista.tablaFondos.getValueAt(filaSeleccionada, 0); // ID de la cuenta seleccionada

        // Pregunta si el usuario realmente quiere eliminar la cuenta
        int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que quieres eliminar el fondo seleccionado?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            // Llama al modelo para eliminar la cuenta
            if (consultaFondos.eliminarFondo(idFondo)) {
                JOptionPane.showMessageDialog(vista, "El fondo ha sido eliminado correctamente.");

                // Recarga la tabla y actualiza el saldo total
                cargarFondos();
                
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar el fondo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Muestra un panel emergente para modificar la cuenta seleccionada
    public void mostrarPanelModificarFondo() {
        int filaSeleccionada = vista.tablaFondos.getSelectedRow();

        // Verifica que haya una cuenta seleccionada
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona un fondo para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idFondo = (int) vista.tablaFondos.getValueAt(filaSeleccionada, 0);

        // Obtén los datos de la cuenta a modificar
        Fondo fondo = consultaFondos.obtenerFondoPorId(idFondo);
        if (fondo == null) {
            JOptionPane.showMessageDialog(vista, "Error al obtener los datos del fondo seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Campos de entrada con los datos actuales del fondo
        JTextField campoNombre = new JTextField(fondo.getNombre(), 15);
        JTextField campoIsin = new JTextField(fondo.getIsin(), 15);
        JTextField campoTipo = new JTextField(consultaFondos.obtenerNombreTipoFondo(idFondo), 15);
        JTextField campoDivisa = new JTextField(String.valueOf(fondo.getMoneda()), 10);
        //Hacer que solo el nombre sea editable ya que el resto lo toma automaticamente de internet
        campoIsin.setEditable(false); 
        campoTipo.setEditable(false);
        campoDivisa.setEditable(false);

        // Añade los campos al panel
        JPanel panel = new JPanel();
        panel.add(new JLabel("Nombre:"));
        panel.add(campoNombre);
        panel.add(new JLabel("ISIN:"));
        panel.add(campoIsin);
        panel.add(new JLabel("Categoría:"));
        panel.add(campoTipo);
        panel.add(new JLabel("Divisa:"));
        panel.add(campoDivisa);

        // Abre un cuadro de diálogo para modificar el fondo
        int resultado = JOptionPane.showConfirmDialog(null, panel, "Modificar fondo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                // Recoge los datos modificados
                String nombre = campoNombre.getText();

                // Llama al modelo para modificar el nombre del fondo
                boolean fondoModificado = consultaFondos.modificarNombreFondo(idFondo, nombre);

                if (fondoModificado) {
                    JOptionPane.showMessageDialog(vista, "Fondo modificado correctamente.");
                    cargarFondos(); // Refresca la tabla con los cambios
                    
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al modificar el fondo. Inténtalo de nuevo.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, "Error en los datos, el nombre no puede estar vacío.");
            }
        }
    }

    //Método para ajustar el ancho de las columnas de la tabla manualmente
    private void ajustarAnchoColumnas() {
        JTable tabla = vista.tablaFondos;  // Suponiendo que 'tablaDepositos' es el nombre de la tabla

        // Obtener el modelo de columnas de la tabla
        TableColumnModel columnModel = tabla.getColumnModel();

        // Ajustar el ancho de cada columna (aquí puedes especificar el tamaño que desees para cada columna)
        columnModel.getColumn(0).setPreferredWidth(7);  // Columna 0: ID del fondo
        columnModel.getColumn(1).setPreferredWidth(180); // Columna 1: Nombre del fondo
        columnModel.getColumn(2).setPreferredWidth(100); // Columna 2: ISIN
        columnModel.getColumn(3).setPreferredWidth(120); // Columna 3: Tipo
        columnModel.getColumn(4).setPreferredWidth(60); // Columna 4: Cotizacion
        columnModel.getColumn(5).setPreferredWidth(60); // Columna 5: Valoración 
        columnModel.getColumn(6).setPreferredWidth(80); // Columna 5: Ficha (icono)
    }
    
    public void mostrarPanelMovimientos(int fila) {
        int idFondo = (int) vista.tablaFondos.getValueAt(fila, 0);

        // Crea el panel de movimientos y el controlador para manejarlo
        PanelFichaFondo panelFicha = new PanelFichaFondo(idFondo);
        new ControladorMovimientosFondo(panelFicha, consultaFondos, consultaMovimientos,idFondo, this);

        JDialog dialogoMovimientos = new JDialog();
        dialogoMovimientos.setTitle("Ficha de Fondo");
        dialogoMovimientos.setModal(true);
        dialogoMovimientos.getContentPane().add(panelFicha);
        dialogoMovimientos.pack();
        dialogoMovimientos.setLocationRelativeTo(vista);
        dialogoMovimientos.setVisible(true);
    }
}
