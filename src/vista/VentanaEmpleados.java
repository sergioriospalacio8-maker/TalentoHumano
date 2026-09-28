package vista;

import modelo.EmpleadoAdministrativo;
import modelo.EmpleadoBase;
import modelo.EmpleadoComercial;

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

        JPanel botones = new JPanel(new FlowLayout());

        JButton[] listaBotones = {
                btnAgregar,
                btnBuscar,
                btnActualizar,
                btnEliminar,
                btnLimpiar,
                btnHistorial,
                btnEstadisticas
        };

        for (JButton boton : listaBotones){
            botones.add(boton);
        }

        JPanel panel = new JPanel(new BorderLayout(10,10));

        panel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10)
        );

        panel.add(campos, BorderLayout.CENTER);

        panel.add(botones, BorderLayout.SOUTH);

        textBonificacion.setEnabled(false);
        return panel;
    }

    private String texto(JTextField campo){
        return campo.getText().trim();
    }

    private String tipoSeleccionado(){
        return (String) cmbTipo.getSelectedItem();
    }

    private JScrollPane construirTabla(){

        String[] columnas = {"Cedula", "Nombre", "Tipo", "Salario base", "Salario total"};

        datosTabla = new DefaultTableModel(columnas, 0){

            @Override
            public boolean isCellEditable(int fila, int columna){
                return false;
            }
        };

        JTable tabla = new JTable(datosTabla);

        JScrollPane scroll = new JScrollPane(tabla);

        scroll.setBorder(BorderFactory.createTitledBorder("Empleados registrados"));
        return scroll;
    }
}

private void refrescarTabla(){

    datosTabla.setRowwCount(0);

    for (EmpleadoBase empleado : controlador.obtenerEmpleados()){

        Object[] fila = {empleado.getCedula(), empleado.getNombre(), empleado.getTipo(), formatoPesos(empleado.getSalarioBase()), formatoPesos(empleado.calcularSalarioTotal())};

        datosTabla.addRow(fila);
    }

    lblResumen.setText("Empleados: " + datosTabla.getRowCount() + " | Total nomina:" + formatoPesos(controlador.calcularTotalNomina()));

}

private String formatoPesos(double valor){
    return String.format("$ %, .0f", valor);
}

private void conectarEventos() {

    cmbTipo.addActionListener(e -> {
        boolean necesitaValor = tipoSeleccionado().equals("Administrativo") || tipoSeleccionado().equals("Comercial");

        txtBonificacion.setEnbled(necesitaValor);

        if (!necesitaValor) {
            txtBonificacion.setText("");
        }
    });

    btnAgregar.addActionListener(e -> mostrarResultado(controlador.agregarEmpleado(texto(txtCedula), texto(txtNombre), texto(txtSalario), tipoSeleccionado(), texto(txtBonificacion))));

    btnActualizar.addAcionListener(e -> mostrarResultado(controlador.actualizarEmpleado(texto(txtCedula), texto(txtNombre), texto(txtSalario), tipoSeleccionado(), texto(txtBonificacion))));

    btnBuscar.addActionListener(e -> buscar());
    btnEliminar.addActionListener(e -> eliminar());
    btnLimpiar.addActionListener(e -> mostrarHistorial());
}

private void mostrarResultado(String mensaje){
    JOptionPane.showMessageDialog(this,mensaje);

    refrescarTabla();
}

private void buscar(){

    String cedula = texto(txtCedula);

    if (cedula.isEmpty()){
        JOptionPane.showMessageDialog(this,"Escribe una cedula para buscar.");
        return;
    }

    EmpleadoBase empleado = controlador.buscarEmpleado(cedula);

    if (empleado == null){

        JOptionPane.showMessageDialog(this,"No se encontro ningun empleado con la cedula" + cedula + ".");
        return;
    }

    txtNombre.setText( empleado.getNombre());

    txtSalario.setText(String.format("%.0f", empleado.getSalarioBase()));

    cmbTipo.setSelectedItem(empleado.getTipo());

    if (empleado instanceof EmpleadoAdministrativo){

        EmpleadoAdministrativo administrativo = (EmpleadoAdministrativo) empleado;

        txtBonificacion.setText(String.format("%.0f", administrativo.getBonificacion()));
    }

    if (empleado instanceof EmpleadoComercial){

        EmpleadoComercial comercial = (EmpleadoComercial) empleado;

        txtBonificacion.setText(String.format("%.0f", comercial.getPorcentajeComision()));
    }
}