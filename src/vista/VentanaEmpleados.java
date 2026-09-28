package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaEmpleados extends JFrame {

    private final EmpleadoControlador controlador;

    private final JTextField txtCedula = new JTextField();
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtSalario = new JTextField();
    private final JTextField textBonificacion = new JTextField();
    private final JComboBox<String> cmbTipo = new JComboBox<>(EmpleadoControlador.TIPOS_EMPLEADO);

    private final JButton btnAgregar = new JButton("Agregar");
    private final JButton btnBuscar = new JButton("Buscar");
    private final JButton btnActualizar = new JButton("Actualizar");
    private final JButton btnEliminar = new JButton("Eliminar");
    private final JButton btnLimpiar = new JButton("Limpiar");
    private final JButton btnHistorial = new JButton("Historial");
    private final JButton btnEstadisticas = new JButton("Estadisticas");

    private DefaultTableModel datosTabla;
    private final JLabel lblResumen = new JLabel("Empleados: 0 | Total nomina: $ 0");

    public VentanaEmpleados(EmpleadoControlador controlador){

        this.controlador = controlador;

        setTitle("Sistema de Talento Humano");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(construirFormulario(), BorderLayout.NORTH);

        add(construirTabla(), BorderLayout.CENTER);

        add(lblResumen, BorderLayout.SOUTH);

        conectarEventos();
        refrescarTabla();
    }

    private JPanel construirFormulario(){

        JPanel campos = new JPanel(new GridLayout(5,2,5,5));

        campos.setBorder(BorderFactory.createTitledBorder("Datos del empleado")
        );

        campos.add(new JLabel("Cedula:"));

        campos.add(txtCedula);

        campos.add(new JLabel("Nombre")
        );

        campos.add(txtNombre);
        campos.add(new JLabel("Salario base:"));

        campos.add(txtSalario);
        campos.add(new JLabel("Tipo:")
        );

        campos.add(cmbTipo);
        campos.add(new JLabel("Bonificacion / Comision %:")
        );

        campos.add(textBonificacion);

    }
}

