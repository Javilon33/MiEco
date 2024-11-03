package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.ConsultaPruebas;
import modelo.Conexion;
import vista.VistaPruebas;

public class ControladorPruebas {
    private VistaPruebas vista;
    private Conexion conexion;
    private ConsultaPruebas consulta;

    public ControladorPruebas(VistaPruebas vista) {
        this.vista = vista;
        this.conexion = new Conexion();
        this.consulta = new ConsultaPruebas(conexion.getConexion());
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarUsuarios();
            }
        });
    }

    private void listarUsuarios() {
        vista.tabla.setModel(consulta.obtenerUsuarios());
    }
}
