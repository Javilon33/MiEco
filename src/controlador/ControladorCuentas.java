package controlador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import modelo.ConsultaCuentas;
import modelo.ConsultaMovimientos;
import modelo.entidades.Cuenta;
import modelo.entidades.Gasto;
import modelo.entidades.Usuario;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import vista.Paneles.PanelCuentas;
import vista.Paneles.PanelMovimientosCuenta;
import vista.componentes.IconRendererEditor;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorCuentas {

    private final PanelCuentas vista; // La vista donde se muestran las cuentas
    private final ConsultaCuentas consultaCuentas; // El modelo para obtener los datos de las cuentas
    private final Usuario usuario; // Usuario actual para obtener sus datos
    private final ConsultaMovimientos consultaMovimientos;
    private final NumberFormat formato; //Formato para mostrar los importes correctamente (2 decimales y puntos en los miles)
    
    

    // Constructor para inicializar la vista, el modelo y el usuario
    public ControladorCuentas(PanelCuentas vista, ConsultaCuentas consultaCuentas, Usuario usuario, ConsultaMovimientos consultaMovimientos) {
        this.vista = vista;
        this.consultaCuentas = consultaCuentas;
        this.usuario = usuario;
        this.consultaMovimientos = consultaMovimientos;
        
        // Configurar el formato para importes (una sola vez)
        this.formato = NumberFormat.getInstance(new Locale("es", "ES"));
        formato.setMaximumFractionDigits(2);
        formato.setMinimumFractionDigits(2);
        
        inicializarEventos(); // Inicializar eventos de la interfaz
        cargarCuentas(); // Cargar las cuentas en la tabla al inicio        
    }

    // Configura eventos básicos (clicks en botones y etiquetas)
    public void inicializarEventos() {
        
        // Evento para el botón y etiqueta de "Añadir cuenta"
        vista.btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelAddCuenta(); // Abre el panel para agregar cuentas
            }
        });        

        // Evento para el botón y etiqueta de "Eliminar cuenta"
        vista.btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                eliminarCuentaSeleccionada(); // Llama a eliminar cuenta
            }
        });        

        // Evento para el botón y etiqueta de "Modificar cuenta"
        vista.btnModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelModificarCuenta(); // Llama a modificar cuenta
            }
        });        
                        
    }

    // Cargar y mostrar las cuentas del usuario en la tabla
    public void cargarCuentas() {
        //Trae las cuentas del usuario
        List<Cuenta> cuentas = consultaCuentas.obtenerCuentas(usuario.getCodigo());
        //Muestra el saldo actual de todas las cuentas (con 2 decimales)
        double saldoTotal = consultaCuentas.obtenerSaldoTotal(usuario.getCodigo());
        vista.etiSaldoTotal.setText(formato.format(saldoTotal)+ " €");
        //Muestra los ingresos totales del año en curso (con 2 decimales)
        double ingresosTotales = consultaCuentas.obtenerSumaIngresos(usuario.getCodigo());
        vista.sumaIngresos.setText(formato.format(ingresosTotales)+ " €");
        vista.etiIngresosTotales.setText("Ingresos Totales (" + LocalDate.now().getYear() + ")");
        //Muestra los pagos totales del año en curso (con 2 decimales)
        double gastosTotales = consultaCuentas.obtenerSumaGastos(usuario.getCodigo());
        vista.sumaPagos.setText(formato.format(gastosTotales)+ " €");
        vista.etiPagosTotales.setText("Gastos Totales (" + LocalDate.now().getYear() + ")");
        

        // Configura el modelo de la tabla para limpiar datos previos
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaCuentas.getModel();
        modelo.setRowCount(0); // Limpiar las filas

        // Rellena la tabla con los datos de las cuentas
        for (Cuenta cuenta : cuentas) {
            Object[] fila = {
                cuenta.getIdCuenta(),
                cuenta.getAlias(),
                cuenta.getIban(),
                cuenta.getBanco(),
                formato.format(cuenta.getSaldo()), // Formatear saldo a 2 decimales
                "Ver detalles" // Esto se verá como el botón de detalles
            };
            modelo.addRow(fila);
        }

        // Configura el icono en la columna de detalles para abrir el panel movimientos
        vista.tablaCuentas.getColumnModel().getColumn(5).setCellRenderer(new IconRendererEditor(vista.tablaCuentas, this));
        vista.tablaCuentas.getColumnModel().getColumn(5).setCellEditor(new IconRendererEditor(vista.tablaCuentas, this));

        // Elimina cualquier MouseListener previo en la columna de detalles
        for (MouseListener listener : vista.tablaCuentas.getMouseListeners()) {
            vista.tablaCuentas.removeMouseListener(listener);
        }

        // Añade el listener solo para la columna de detalles
        vista.tablaCuentas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vista.tablaCuentas.rowAtPoint(e.getPoint());
                int columna = vista.tablaCuentas.columnAtPoint(e.getPoint());

                // Actúa solo si el clic fue en la columna de detalles (columna 5)
                if (columna == 5) {
                    mostrarPanelMovimientos(fila);
                } else {
                    // Permite la selección de filas si el clic no es en la columna de detalles
                    vista.tablaCuentas.setRowSelectionInterval(fila, fila);
                }
            }
        });
        
        ajustarAnchoColumnas(); // Llamamos a este método para ajustar el ancho de las columnas

        //Inicia o actualiza los gráficos por si ha habido cambios
        graficoGastos(usuario.getCodigo());
        graficoIngresosGastos(usuario.getCodigo());
    }

    // Muestra un panel emergente para añadir una cuenta nueva
    public void mostrarPanelAddCuenta() {
        // Campos de entrada para el alias, IBAN, banco y saldo
        JTextField campoAlias = new JTextField(15);
        JTextField campoIban = new JTextField(15);
        JTextField campoBanco = new JTextField(15);

        // Añade los campos al panel
        JPanel panel = new JPanel();
        panel.add(new JLabel("Alias:"));
        panel.add(campoAlias);
        panel.add(new JLabel("IBAN:"));
        panel.add(campoIban);
        panel.add(new JLabel("Banco:"));
        panel.add(campoBanco);

        boolean datosValidos = false;

        while (!datosValidos) {
            // Abre un diálogo para añadir la cuenta 
            int resultado = JOptionPane.showConfirmDialog(vista, panel, "Añadir nueva cuenta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (resultado == JOptionPane.OK_OPTION) {
                // Recoge y valida los datos introducidos 
                String alias = campoAlias.getText().trim();
                String iban = campoIban.getText().trim();
                String banco = campoBanco.getText().trim();

                // Comprueba que los campos no estén vacíos 
                if (alias.isEmpty() || iban.isEmpty() || banco.isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios. Por favor, rellénalos.");
                } else {
                    try {
                        // Llama al modelo para añadir la cuenta 
                        boolean cuentaInsertada = consultaCuentas.addCuenta(usuario.getCodigo(), alias, iban, banco);
                        // Actualiza la etiqueta de saldo total 
                        vista.etiSaldoTotal.setText(formato.format(consultaCuentas.obtenerSaldoTotal(usuario.getCodigo())) + " €");
                        if (cuentaInsertada) {
                            JOptionPane.showMessageDialog(vista, "Cuenta añadida correctamente.");
                            // Refresca la tabla con la nueva cuenta 
                            cargarCuentas();
                            // Salir del bucle 
                            datosValidos = true;
                        } else {
                            JOptionPane.showMessageDialog(vista, "Error al añadir la cuenta. Inténtalo de nuevo.");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(vista, "Error en los datos. Verifica los campos de banco y saldo.");
                    }
                }
            } else {
                datosValidos = true;
            }
        }
    }

    // Elimina la cuenta seleccionada en la tabla
    public void eliminarCuentaSeleccionada() {
        int filaSeleccionada = vista.tablaCuentas.getSelectedRow(); // Obtiene la fila seleccionada

        if (filaSeleccionada == -1) { // Verifica que haya una selección
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona una cuenta para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCuenta = (int) vista.tablaCuentas.getValueAt(filaSeleccionada, 0); // ID de la cuenta seleccionada

        // Pregunta si el usuario realmente quiere eliminar la cuenta
        int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que quieres eliminar la cuenta seleccionada?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            // Llama al modelo para eliminar la cuenta
            if (consultaCuentas.eliminarCuenta(idCuenta)) {
                JOptionPane.showMessageDialog(vista, "La cuenta ha sido eliminada correctamente.");

                // Recarga la tabla y actualiza el saldo total
                cargarCuentas();
                //Muestra el saldo actual de todas las cuentas (con 2 decimales)
                double saldoTotal = consultaCuentas.obtenerSaldoTotal(usuario.getCodigo());
                vista.etiSaldoTotal.setText(formato.format( saldoTotal) + " €");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Muestra un panel emergente para modificar la cuenta seleccionada
    public void mostrarPanelModificarCuenta() {
        int filaSeleccionada = vista.tablaCuentas.getSelectedRow();

        // Verifica que haya una cuenta seleccionada
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona una cuenta para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCuenta = (int) vista.tablaCuentas.getValueAt(filaSeleccionada, 0);

        // Obtén los datos de la cuenta a modificar
        Cuenta cuenta = consultaCuentas.obtenerCuentaPorId(idCuenta);
        if (cuenta == null) {
            JOptionPane.showMessageDialog(vista, "Error al obtener los datos de la cuenta seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Campos de entrada con los datos actuales de la cuenta
        JTextField campoAlias = new JTextField(cuenta.getAlias(), 15);
        JTextField campoIban = new JTextField(cuenta.getIban(), 15);
        JTextField campoBanco = new JTextField(cuenta.getBanco(), 15);
        JTextField campoSaldo = new JTextField(String.valueOf(cuenta.getSaldo()), 10);
        campoSaldo.setEditable(false); // Hacer que el saldo no sea editable

        // Añade los campos al panel
        JPanel panel = new JPanel();
        panel.add(new JLabel("Alias:"));
        panel.add(campoAlias);
        panel.add(new JLabel("IBAN:"));
        panel.add(campoIban);
        panel.add(new JLabel("Banco:"));
        panel.add(campoBanco);
        panel.add(new JLabel("Saldo actual:"));
        panel.add(campoSaldo);

        // Abre un cuadro de diálogo para modificar la cuenta
        int resultado = JOptionPane.showConfirmDialog(null, panel, "Modificar cuenta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                // Recoge los datos modificados
                String alias = campoAlias.getText();
                String iban = campoIban.getText();
                String banco = campoBanco.getText();
                double saldoActual = Double.parseDouble(campoSaldo.getText());

                // Llama al modelo para modificar la cuenta
                boolean cuentaModificada = consultaCuentas.modificarCuenta(idCuenta, alias, iban, banco, saldoActual);

                if (cuentaModificada) {
                    JOptionPane.showMessageDialog(vista, "Cuenta modificada correctamente.");
                    cargarCuentas(); // Refresca la tabla con los cambios
                    //Muestra el saldo actual de todas las cuentas (con 2 decimales)
                    double saldoTotal = consultaCuentas.obtenerSaldoTotal(usuario.getCodigo());
                    vista.etiSaldoTotal.setText(formato.format(saldoTotal)+" €");
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al modificar la cuenta. Inténtalo de nuevo.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, "Error en los datos. Verifica los campos de banco y saldo.");
            }
        }
    }

    public void mostrarPanelMovimientos(int fila) {
        int idCuenta = (int) vista.tablaCuentas.getValueAt(fila, 0);

        // Crea el panel de movimientos y el controlador para manejarlo
        PanelMovimientosCuenta panelMovimientos = new PanelMovimientosCuenta(idCuenta);
        new ControladorMovimientosCuenta(panelMovimientos, consultaCuentas, idCuenta, consultaMovimientos, this);

        JDialog dialogoMovimientos = new JDialog();
        dialogoMovimientos.setTitle("Movimientos de la Cuenta");
        dialogoMovimientos.setModal(true);
        dialogoMovimientos.getContentPane().add(panelMovimientos);
        dialogoMovimientos.pack();
        dialogoMovimientos.setLocationRelativeTo(vista);
        dialogoMovimientos.setVisible(true);
    }

    // Método para mostrar un gráfico de tarta con el desglose de los GASTOS por tipo en el año actual
    public void graficoGastos(int idUsuario) {
        // Sacamos todos los gastos del usuario en forma de un array
        Gasto[] gastos = consultaCuentas.obtenerTodosLosGastos(idUsuario).toArray(new Gasto[0]);

        // Crea el conjunto de datos para el gráfico (una tarta necesita datos en este formato)
        DefaultPieDataset datos = new DefaultPieDataset();
        for (Gasto gasto : gastos) {
            // Añadimos cada gasto al conjunto, usando la descripción como etiqueta y el total como valor
            datos.setValue(gasto.getDescripcion(), gasto.getTotal());
        }

        // Crea el gráfico de tarta con el título y los datos
        JFreeChart grafico_circular = ChartFactory.createPieChart(
                "Resumen de Gastos (" + LocalDate.now().getYear() + ")", // Título del gráfico con el año actual
                datos, // Los datos que usamos para la tarta
                false, // Sin descripciones
                true, // Permitimos herramientas como descripciones emergentes
                false // No necesitamos URLs (no es un gráfico web)
        );

        // Cambia la fuente del título para que sea más estilosa (Roboto, tamaño 18)
        Font fuenteTitulo = new Font("Roboto", Font.PLAIN, 18);
        TextTitle titulo = new TextTitle("Desglose de Gastos (" + LocalDate.now().getYear() + ")", fuenteTitulo);
        grafico_circular.setTitle(titulo);

        // Ajusta el fondo interno del gráfico a blanco (para que no salga gris)
        PiePlot plot = (PiePlot) grafico_circular.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        // Configura las etiquetas que aparecerán en la tarta:
        // - {0}: Nombre de la categoría
        // - {1}: Valor numérico con formato
        // - {2}: Porcentaje del total
        plot.setLabelFont(new Font("Roboto", Font.PLAIN, 8)); // Fuente de las etiquetas
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}: {1}€ ({2})", // Formato de las etiquetas
                NumberFormat.getNumberInstance(), // Para mostrar los valores bien formateados
                NumberFormat.getPercentInstance() // Para mostrar los porcentajes bien
        ));

        // Crea un panel que contendrá el gráfico (para interactuar con él)
        ChartPanel panel = new ChartPanel(grafico_circular);
        panel.setMouseWheelEnabled(true); // Permite hacer zoom con la rueda del ratón        
        panel.setPreferredSize(new Dimension(400, 295)); // Tamaño del gráfico

        // Añade un evento para ampliar el gráfico al hacer clic
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                mostrarGraficoAmpliado(grafico_circular);
            }
        });

        // Actualiza el panel principal
        vista.panelGastos.removeAll();
        vista.panelGastos.add(panel, BorderLayout.NORTH);
        vista.panelIngresos.revalidate();
        vista.panelGastos.repaint();
    }
    
    // Método para mostrar un gráfico de barras comparando INGRESOS/GASTOS en el año actual
    public void graficoIngresosGastos(int idUsuario) {
        // Obtener los datos para el gráfico
        double sumaIngresos = consultaCuentas.obtenerSumaIngresos(idUsuario);
        double sumaGastos = consultaCuentas.obtenerSumaGastos(idUsuario);
        sumaGastos = -sumaGastos;//Convirte el importe a positivo para que no salga la barra invertida en el gráfico 
        
        // Crear el conjunto de datos para el gráfico de barras
        DefaultCategoryDataset datos = new DefaultCategoryDataset();
        datos.addValue(sumaIngresos, "Ingresos", "");
        datos.addValue(sumaGastos, "Gastos", "");       

        // Crear el gráfico de barras
        JFreeChart graficoBarras = ChartFactory.createBarChart(
                "Comparativa de Ingresos y Gastos", // Título del gráfico
                "", // Etiqueta del eje X
                "Cantidad (€)", // Etiqueta del eje Y
                datos, // Conjunto de datos
                PlotOrientation.VERTICAL, // Orientación del gráfico
                false, // No queremos leyenda
                true, // Habilitar herramientas (como descripciones emergentes)
                false // No necesitamos URLs
        );

        // Cambiar la fuente del título
        Font fuenteTitulo = new Font("Roboto", Font.PLAIN, 18);
        graficoBarras.setTitle(new TextTitle("Ingresos y Gastos ("+ LocalDate.now().getYear() + ")", fuenteTitulo));

        // Cambiar el fondo del gráfico a blanco
        CategoryPlot plot = graficoBarras.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
                
        // Configurar el renderizador para personalizar el ancho y color de las barras
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(60, 179, 113)); // Verde para ingresos
        renderer.setSeriesPaint(1, new Color(220, 20, 60)); // Rojo para pagos
        renderer.setMaximumBarWidth(0.3); // Ancho máximo de las barras 
        renderer.setItemMargin(0.0); // Eliminar espacio entre barras del mismo grupo
        renderer.setShadowVisible(true); //Muestra la sombra de las barras

        // Ajustar la fuente del eje X y del eje Y
        plot.getDomainAxis().setLabelFont(new Font("Roboto", Font.PLAIN, 12));
        plot.getRangeAxis().setLabelFont(new Font("Roboto", Font.PLAIN, 12));

        // Crear el panel para mostrar el gráfico
        ChartPanel panel = new ChartPanel(graficoBarras);
        panel.setPreferredSize(new Dimension(190, 290));
        
        
        
        // Añade un evento para ampliar el gráfico al hacer clic
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                mostrarGraficoAmpliado(graficoBarras);
            }
        });

        // Mostrar el gráfico en el panel correspondiente de la vista
        vista.panelIngresos.removeAll();
        vista.panelIngresos.add(panel, BorderLayout.CENTER);
        vista.panelIngresos.revalidate();
        vista.panelIngresos.repaint();
    }
    
    // Método para mostrar el gráfico ampliado en un diálogo
    private void mostrarGraficoAmpliado(JFreeChart grafico) {
        // Creamos un JDialog para mostrar el gráfico ampliado
        JDialog dialogo = new JDialog();
        dialogo.setTitle("Gráfico Ampliado");
        dialogo.setModal(true); // Bloquea la ventana principal hasta que se cierre
        dialogo.setSize(800, 600); // Tamaño del diálogo ampliado
        dialogo.setLocationRelativeTo(null); // Centrado en la pantalla

        // Creamos un ChartPanel para el gráfico ampliado
        ChartPanel panelAmpliado = new ChartPanel(grafico);
        panelAmpliado.setMouseWheelEnabled(true); // Permite el zoom
        dialogo.add(panelAmpliado);

        // Mostramos el diálogo
        dialogo.setVisible(true);
    }
    
    //Método para ajustar el ancho de las columnas de la tabla manualmente
    private void ajustarAnchoColumnas() {
        JTable tabla = vista.tablaCuentas;  // Suponiendo que 'tablaDepositos' es el nombre de la tabla

        // Obtener el modelo de columnas de la tabla
        TableColumnModel columnModel = tabla.getColumnModel();

        // Ajustar el ancho de cada columna (aquí puedes especificar el tamaño que desees para cada columna)
        columnModel.getColumn(0).setPreferredWidth(20);  // Columna 0: ID de la cuenta
        columnModel.getColumn(1).setPreferredWidth(150); // Columna 1: Alias de la cuenta
        columnModel.getColumn(2).setPreferredWidth(180); // Columna 2: IBAN
        columnModel.getColumn(3).setPreferredWidth(130); // Columna 3: Banco
        columnModel.getColumn(4).setPreferredWidth(80); // Columna 4: Saldo
        columnModel.getColumn(5).setPreferredWidth(90); // Columna 5: Movimientos (icono)
    }

}
