package controlador;

import Utilidades.ComboBoxItem;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultaCuentas;
import modelo.ConsultaMovimientos;
import modelo.entidades.Movimiento;
import rojeru_san.componentes.RSDateChooser;
import vista.Paneles.PanelMovimientosCuenta;

public class ControladorMovimientosCuenta {

    private final PanelMovimientosCuenta vista; // Vista del panel MovimientosCuenta
    private final ConsultaCuentas consultaCuentas; // ControladorCuentas para obtener las cuentas
    private final ConsultaMovimientos consultaMovimientos; // Modelo para manejar movimientos
    private final int idCuenta; // ID de la cuenta seleccionada
    private final ControladorCuentas controladorCuentas; // Referencia al ControladorCuentas
    private final NumberFormat formato; //Formato para mostrar los importes correctamente (2 decimales y puntos en los miles)
    
    

    public ControladorMovimientosCuenta(PanelMovimientosCuenta vista, ConsultaCuentas consultaCuentas, int idCuenta, ConsultaMovimientos consultaMovimientos, ControladorCuentas controladorCuentas) {
        this.vista = vista;
        this.consultaCuentas = consultaCuentas;
        this.idCuenta = idCuenta;
        this.consultaMovimientos = consultaMovimientos;
        this.controladorCuentas = controladorCuentas;
        
        // Configurar el formato para importes (una sola vez)
        this.formato = NumberFormat.getInstance(new Locale("es", "ES"));
        formato.setMaximumFractionDigits(2);
        formato.setMinimumFractionDigits(2);
        
        inicializarEventos();  // Inicializar eventos de la interfaz
        cargarMovimientos(); // Cargar los movimientos al inicio
        
        
        
    }

    // Método para inicializar la vista con el nombre de la cuenta
    private void inicializarEventos() {
        // Obtener el nombre de la cuenta
        String nombreCuenta = consultaCuentas.obtenerCuentaPorId(idCuenta).getAlias();
        //Obtener el nombre del banco
        String banco = consultaCuentas.obtenerCuentaPorId(idCuenta).getBanco();
        // Establecer el texto del título con el nombre del banco y la cuenta
        vista.etiBanco.setText(banco);
        vista.etiNombreCuenta.setText(nombreCuenta);

        // Evento para el botón y etiqueta de "Añadir movimiento"
        vista.btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //mostrarPanel();
                mostrarPanelAddMovimiento();
            }
        });
        vista.etiAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //mostrarPanel();
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
        vista.etiElminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                eliminarMovimientoSeleccionado();
            }
        });

        // Evento para el botón y etiqueta de "Modificar movimiento"
        vista.btnModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelModificarMovimiento(); // Llama a modificar movimiento
            }
        });
        vista.etiModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelModificarMovimiento();
            }
        });
    }

    // Método para cargar los movimientos de la cuenta y mostrarlos en la tabla
    private void cargarMovimientos() {
        // Obtener los movimientos de la cuenta desde el controlador
        List<Movimiento> movimientos = consultaMovimientos.obtenerMovimientos(idCuenta);

        // Obtener el saldo de la cuenta
        double saldo=0;//

        // Obtener el modelo de la tabla y limpiar cualquier dato existente
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaMovimientos.getModel();
        modelo.setRowCount(0); // Limpiar las filas de la tabla

        // Añadir los movimientos a la tabla
        for (Movimiento movimiento : movimientos) {
            // Ajustar el saldo actual basado en el tipo de movimiento
            saldo += movimiento.getImporte();

            // Obtener los nombres de Tipo Movimiento, subtipo y gasto
            String nombreTipo = consultaMovimientos.obtenerNombreTipoMovimiento(movimiento.getId_movimiento());
            String nombreSubtipo = consultaMovimientos.obtenerNombreSubtipoMovimiento(movimiento.getId_movimiento());
            String nombreGasto = movimiento.getTipoGasto() != null ? consultaMovimientos.obtenerNombreGasto(movimiento.getId_movimiento()) : ""; // Dejar vacío si no hay tipo Gasto

            // Crear un array con los datos del movimiento para cada fila de la tabla
            Object[] fila = {
                movimiento.getId_movimiento(), // Incluir el id_movimiento (oculto)
                new SimpleDateFormat("dd-MM-yyyy").format(movimiento.getFecha()), //Fecha formateada a dd-MM-yyyy
                nombreTipo,
                nombreSubtipo,
                nombreGasto,
                movimiento.getNotas(),
                formato.format(movimiento.getImporte()),
                formato.format(saldo),                
            };
            // Añadir la fila al modelo de la tabla
            modelo.addRow(fila);
        }

        // Oculta la columna del id_movimiento
        vista.tablaMovimientos.getColumnModel().getColumn(0).setMinWidth(0);
        vista.tablaMovimientos.getColumnModel().getColumn(0).setMaxWidth(0);
        vista.tablaMovimientos.getColumnModel().getColumn(0).setPreferredWidth(0);

        // Actualizar el saldo en la base de datos
        if (!consultaCuentas.actualizarSaldoCuenta(idCuenta, saldo)) {
            System.err.println("Error al actualizar el saldo de la cuenta.");
        }
    }

    //Método para AÑADIR MOVIMIENTOS
    public void mostrarPanelAddMovimiento() {
        // Campos de entrada para los datos
        RSDateChooser campoFecha = new RSDateChooser();
        JComboBox<ComboBoxItem> cbTipo = new JComboBox<>();
        JTextField campoNotas = new JTextField(20);
        JTextField campoImporte = new JTextField(10);
        JComboBox<ComboBoxItem> cbCategoria = new JComboBox<>();
        JComboBox<ComboBoxItem> cbGasto = new JComboBox<>();

        // Carga inicial de tipos
        Map<Integer, String> listaTipo = consultaMovimientos.listaTipo();
        cbTipo.setModel(new DefaultComboBoxModel<>(listaTipo.entrySet().stream()
                .map(entry -> new ComboBoxItem(entry.getKey(), entry.getValue()))
                .toArray(ComboBoxItem[]::new)));

        // Selecciona automáticamente el primer tipo y carga las categorías
        if (cbTipo.getItemCount() > 0) {
            cbTipo.setSelectedIndex(0); // Selecciona el primer elemento
            ComboBoxItem tipoSeleccionado = (ComboBoxItem) cbTipo.getSelectedItem();
            if (tipoSeleccionado != null) {
                Map<Integer, String> nuevasCategorias = consultaMovimientos.listaSubtipos(tipoSeleccionado.getId());
                cbCategoria.setModel(new DefaultComboBoxModel<>(nuevasCategorias.entrySet().stream()
                        .map(entry -> new ComboBoxItem(entry.getKey(), entry.getValue()))
                        .toArray(ComboBoxItem[]::new)));
            }
        }

        // Listener para actualizar categorías al cambiar tipo
        cbTipo.addActionListener(e -> {
            ComboBoxItem tipoSeleccionado = (ComboBoxItem) cbTipo.getSelectedItem();
            if (tipoSeleccionado != null) {
                Map<Integer, String> nuevasCategorias = consultaMovimientos.listaSubtipos(tipoSeleccionado.getId());
                cbCategoria.setModel(new DefaultComboBoxModel<>(nuevasCategorias.entrySet().stream()
                        .map(entry -> new ComboBoxItem(entry.getKey(), entry.getValue()))
                        .toArray(ComboBoxItem[]::new)));

                ComboBoxItem categoriaSeleccionada = (ComboBoxItem) cbCategoria.getSelectedItem();
                if (categoriaSeleccionada.getId() == 9) {
                    Map<Integer, String> listaGasto = consultaMovimientos.listaGastos();
                    cbGasto.setModel(new DefaultComboBoxModel<>(listaGasto.entrySet().stream()
                            .map(entry -> new ComboBoxItem(entry.getKey(), entry.getValue()))
                            .toArray(ComboBoxItem[]::new)));
                } else {
                    cbGasto.setModel(new DefaultComboBoxModel<>(new ComboBoxItem[0]));
                }
            }

        });

        // Listener para actualizar gastos según tipo y categoría
        cbCategoria.addActionListener(e -> {
            //ComboBoxItem tipoSeleccionado = (ComboBoxItem) cbTipo.getSelectedItem();
            ComboBoxItem categoriaSeleccionada = (ComboBoxItem) cbCategoria.getSelectedItem();

            if (categoriaSeleccionada.getId() == 9) {
                Map<Integer, String> listaGasto = consultaMovimientos.listaGastos();
                cbGasto.setModel(new DefaultComboBoxModel<>(listaGasto.entrySet().stream()
                        .map(entry -> new ComboBoxItem(entry.getKey(), entry.getValue()))
                        .toArray(ComboBoxItem[]::new)));
            } else {
                cbGasto.setModel(new DefaultComboBoxModel<>(new ComboBoxItem[0]));
            }
        });

        // Panel con los componentes
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(campoFecha);
        panel.add(new JLabel("Tipo:"));
        panel.add(cbTipo);
        panel.add(new JLabel("Categoría:"));
        panel.add(cbCategoria);
        panel.add(new JLabel("Tipo de Gasto:"));
        panel.add(cbGasto);
        panel.add(new JLabel("Notas:"));
        panel.add(campoNotas);
        panel.add(new JLabel("Importe:"));
        panel.add(campoImporte);

        // Diálogo para añadir movimiento
        int resultado = JOptionPane.showConfirmDialog(null, panel, "Añadir nuevo movimiento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            if (campoFecha.getDatoFecha() != null) {
                try {
                    // Formatear la fecha
                    String formatoFecha = "yyyy/MM/dd";
                    SimpleDateFormat formato = new SimpleDateFormat(formatoFecha);
                    String fecha = formato.format(campoFecha.getDatoFecha());

                    // Obtener datos seleccionados
                    ComboBoxItem tipoSeleccionado = (ComboBoxItem) cbTipo.getSelectedItem();
                    ComboBoxItem categoriaSeleccionada = (ComboBoxItem) cbCategoria.getSelectedItem();
                    ComboBoxItem gastoSeleccionado = (ComboBoxItem) cbGasto.getSelectedItem();

                    int tipo = tipoSeleccionado != null ? tipoSeleccionado.getId() : 0;
                    int categoria = categoriaSeleccionada != null ? categoriaSeleccionada.getId() : 0;
                    int gasto = gastoSeleccionado != null ? gastoSeleccionado.getId() : 0;

                    String notas = campoNotas.getText();
                    // Verificar si el movimiento es un pago, y si es así, convertir el importe a negativo

                    // Crear un NumberFormat basado en la configuración regional
                     NumberFormat formatoNumero = NumberFormat.getInstance(Locale.getDefault());

                    // Analizar el texto del importe con NumberFormat
                    Number numero = formatoNumero.parse(campoImporte.getText());
                    double importe = numero.doubleValue();
                    
                    //Convierte el importe en negativo si es un pago (tipo 2)
                    
                    if (tipo == 2) {
                        importe = -importe;
                    }

                    // Llama al modelo para añadir el movimiento
                    boolean movimientoInsertado = consultaMovimientos.addMovimiento(idCuenta, fecha, tipo, categoria, gasto, notas, importe);

                    if (movimientoInsertado) {
                        JOptionPane.showMessageDialog(vista, "Movimiento añadido correctamente.");
                        cargarMovimientos(); // Refresca la tabla
                        controladorCuentas.cargarCuentas(); // Refresca cuentas
                    } else {
                        JOptionPane.showMessageDialog(vista, "Error al añadir el movimiento. Inténtalo de nuevo.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(vista, "Error en los datos. Verifica los campos.");
                } catch (ParseException ex) {
                    Logger.getLogger(ControladorMovimientosCuenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(vista, "No has introducido Fecha");
            }
        }
    }

    //Método para MODIFICAR MOVIMIENTOS
    public void mostrarPanelModificarMovimiento() {
        // Obtiene el movimiento seleccionado
        int filaSeleccionada = vista.tablaMovimientos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona un movimiento para modificar.");
            return;
        }

        // Recupera los datos del movimiento seleccionado
        int idMovimiento = (int) vista.tablaMovimientos.getValueAt(filaSeleccionada, 0);
        Movimiento movimiento = consultaMovimientos.obtenerMovimientoPorId(idMovimiento);

        if (movimiento == null) {
            JOptionPane.showMessageDialog(vista, "No se pudo recuperar la información del movimiento.");
            return;
        }

        // Componentes del formulario
        RSDateChooser campoFecha = new RSDateChooser();
        campoFecha.setDatoFecha(movimiento.getFecha());

        JComboBox<ComboBoxItem> cbTipo = new JComboBox<>();
        JComboBox<ComboBoxItem> cbCategoria = new JComboBox<>();
        JComboBox<ComboBoxItem> cbGasto = new JComboBox<>();
        JTextField campoNotas = new JTextField(movimiento.getNotas(), 20);
        JTextField campoImporte = new JTextField(String.valueOf(Math.abs(movimiento.getImporte())), 10); //He tenido que usar Math para que el importe siempre lo traiga en positivo

        // Cargar tipos
        Map<Integer, String> listaTipo = consultaMovimientos.listaTipo();
        DefaultComboBoxModel<ComboBoxItem> modeloTipo = new DefaultComboBoxModel<>(
                listaTipo.entrySet().stream()
                        .map(entry -> new ComboBoxItem(entry.getKey(), entry.getValue()))
                        .toArray(ComboBoxItem[]::new)
        );
        cbTipo.setModel(modeloTipo);

        // Seleccionar el tipo del movimiento si existe
        ComboBoxItem tipoItem = new ComboBoxItem(movimiento.getTipo(), listaTipo.get(movimiento.getTipo()));
        if (modeloTipo.getIndexOf(tipoItem) != -1) {
            cbTipo.setSelectedItem(tipoItem);
        }

        // Cargar categorías iniciales
        Map<Integer, String> listaCategoria = consultaMovimientos.listaSubtipos(movimiento.getTipo());
        DefaultComboBoxModel<ComboBoxItem> modeloCategoria = new DefaultComboBoxModel<>(
                listaCategoria.entrySet().stream()
                        .map(entry -> new ComboBoxItem(entry.getKey(), entry.getValue()))
                        .toArray(ComboBoxItem[]::new)
        );
        cbCategoria.setModel(modeloCategoria);

        // Seleccionar la categoría del movimiento si existe
        ComboBoxItem categoriaItem = new ComboBoxItem(movimiento.getSubtipo(), listaCategoria.get(movimiento.getSubtipo()));
        if (modeloCategoria.getIndexOf(categoriaItem) != -1) {
            cbCategoria.setSelectedItem(categoriaItem);
        }

        // Cargar gastos si es necesario
        if (movimiento.getSubtipo() == 9) {
            Map<Integer, String> listaGasto = consultaMovimientos.listaGastos();
            DefaultComboBoxModel<ComboBoxItem> modeloGasto = new DefaultComboBoxModel<>(
                    listaGasto.entrySet().stream()
                            .map(entry -> new ComboBoxItem(entry.getKey(), entry.getValue()))
                            .toArray(ComboBoxItem[]::new)
            );
            cbGasto.setModel(modeloGasto);

            // Seleccionar el gasto si existe
            ComboBoxItem gastoItem = new ComboBoxItem(movimiento.getTipoGasto(), listaGasto.get(movimiento.getTipoGasto()));
            if (modeloGasto.getIndexOf(gastoItem) != -1) {
                cbGasto.setSelectedItem(gastoItem);
            }
        }

        // Listener para actualizar categorías al cambiar tipo
        cbTipo.addActionListener(e -> {
            ComboBoxItem tipoSeleccionado = (ComboBoxItem) cbTipo.getSelectedItem();
            if (tipoSeleccionado != null) {
                Map<Integer, String> nuevasCategorias = consultaMovimientos.listaSubtipos(tipoSeleccionado.getId());
                cbCategoria.setModel(new DefaultComboBoxModel<>(nuevasCategorias.entrySet().stream()
                        .map(entry -> new ComboBoxItem(entry.getKey(), entry.getValue()))
                        .toArray(ComboBoxItem[]::new)));
            }
        });

        // Listener para actualizar gastos al cambiar categoría
        cbCategoria.addActionListener(e -> {
            ComboBoxItem categoriaSeleccionada = (ComboBoxItem) cbCategoria.getSelectedItem();
            if (categoriaSeleccionada != null && categoriaSeleccionada.getId() == 9) {
                Map<Integer, String> listaGasto = consultaMovimientos.listaGastos();
                cbGasto.setModel(new DefaultComboBoxModel<>(listaGasto.entrySet().stream()
                        .map(entry -> new ComboBoxItem(entry.getKey(), entry.getValue()))
                        .toArray(ComboBoxItem[]::new)));
            } else {
                cbGasto.setModel(new DefaultComboBoxModel<>(new ComboBoxItem[0]));
            }
        });

        // Panel de entrada
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Fecha:"));
        panel.add(campoFecha);
        panel.add(new JLabel("Tipo:"));
        panel.add(cbTipo);
        panel.add(new JLabel("Categoría:"));
        panel.add(cbCategoria);
        panel.add(new JLabel("Tipo de Gasto:"));
        panel.add(cbGasto);
        panel.add(new JLabel("Notas:"));
        panel.add(campoNotas);
        panel.add(new JLabel("Importe:"));
        panel.add(campoImporte);

        // Mostrar diálogo
        int resultado = JOptionPane.showConfirmDialog(null, panel, "Modificar Movimiento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                // Validar y obtener datos
                String fecha = new SimpleDateFormat("yyyy/MM/dd").format(campoFecha.getDatoFecha());
                ComboBoxItem tipoSeleccionado = (ComboBoxItem) cbTipo.getSelectedItem();
                ComboBoxItem categoriaSeleccionada = (ComboBoxItem) cbCategoria.getSelectedItem();
                ComboBoxItem gastoSeleccionado = (ComboBoxItem) cbGasto.getSelectedItem();
                
                if(categoriaSeleccionada.getId() == 9 && gastoSeleccionado == null){
                    JOptionPane.showMessageDialog(vista, "No Has seleccionado el tipo de gasto");
                    return;
                }

                int tipo = tipoSeleccionado != null ? tipoSeleccionado.getId() : 0;
                int categoria = categoriaSeleccionada != null ? categoriaSeleccionada.getId() : 0;
                int gasto = gastoSeleccionado != null ? gastoSeleccionado.getId() : 0;
                String notas = campoNotas.getText();

                // Normalizar el importe para que acepte comas
                String importeTexto = campoImporte.getText().replace(",", ".");
                double importe = Double.parseDouble(importeTexto);

                //Convierte el importe en negativo si es un pago (tipo 2)                
                if (tipo == 2) {
                    importe = -importe;
                }

                // Actualizar el movimiento
                boolean actualizado = consultaMovimientos.modificarMovimiento(idMovimiento, fecha, tipo, categoria, gasto, notas, importe);

                if (actualizado) {
                    JOptionPane.showMessageDialog(vista, "Movimiento modificado correctamente.");
                    cargarMovimientos(); // Refresca la tabla
                    controladorCuentas.cargarCuentas(); // Refresca cuentas
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al modificar el movimiento.");
                }
            } catch (HeadlessException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Error en los datos. Verifica los campos.");
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

        // Pregunta si el usuario realmente quiere eliminar la cuenta
        int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que quieres eliminar el movimiento seleccionado?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            // Llama al modelo para eliminar la cuenta
            if (consultaMovimientos.eliminarMovimiento(idMovimiento)) {
                JOptionPane.showMessageDialog(vista, "El movimiento ha sido eliminado correctamente.");

                // Recarga la tabla y actualiza el saldo total
                cargarMovimientos(); // Refresca la tabla sin el movimiento elimnado
                controladorCuentas.cargarCuentas(); // Refresca la tabla de cuentas sin el movimiento elimnado                

            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
