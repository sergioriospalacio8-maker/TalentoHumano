package vista;

import controlador.EmpleadoControlador;
import modelo.EmpleadoAdministrativo;
import modelo.EmpleadoBase;
import modelo.EmpleadoComercial;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class VentanaEmpleados extends JFrame {

    private final EmpleadoControlador controlador;

    // =========================================================
    // CAMPOS DEL FORMULARIO
    // =========================================================

    private final JTextField txtCedula =
            new JTextField();

    private final JTextField txtNombre =
            new JTextField();

    private final JTextField txtSalario =
            new JTextField();

    private final JTextField txtBonificacion =
            new JTextField();

    private final JComboBox<String> cmbTipo =
            new JComboBox<>(
                    EmpleadoControlador.TIPOS_EMPLEADO
            );

    // =========================================================
    // BOTONES
    // =========================================================

    private final JButton btnAgregar =
            new JButton("Agregar");

    private final JButton btnBuscar =
            new JButton("Buscar");

    private final JButton btnActualizar =
            new JButton("Actualizar");

    private final JButton btnEliminar =
            new JButton("Eliminar");

    private final JButton btnLimpiar =
            new JButton("Limpiar");

    private final JButton btnHistorial =
            new JButton("Historial");

    // BOTÓN DEL RETO BONUS

    private final JButton btnEstadisticas =
            new JButton("Estadísticas");

    // =========================================================
    // TABLA
    // =========================================================

    private DefaultTableModel datosTabla;

    private final JLabel lblResumen =
            new JLabel("Empleados: 0 | Total nómina: $ 0");

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public VentanaEmpleados(
            EmpleadoControlador controlador) {

        this.controlador = controlador;

        setTitle("Sistema de Talento Humano");

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setSize(900, 600);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        add(
                construirFormulario(),
                BorderLayout.NORTH
        );

        add(
                construirTabla(),
                BorderLayout.CENTER
        );

        add(
                lblResumen,
                BorderLayout.SOUTH
        );

        conectarEventos();

        refrescarTabla();
    }

    // =========================================================
    // FORMULARIO
    // =========================================================

    private JPanel construirFormulario() {

        JPanel campos =
                new JPanel(
                        new GridLayout(5, 2, 5, 5)
                );

        campos.setBorder(
                BorderFactory.createTitledBorder(
                        "Datos del empleado"
                )
        );

        campos.add(
                new JLabel("Cédula:")
        );

        campos.add(txtCedula);

        campos.add(
                new JLabel("Nombre:")
        );

        campos.add(txtNombre);

        campos.add(
                new JLabel("Salario base:")
        );

        campos.add(txtSalario);

        campos.add(
                new JLabel("Tipo:")
        );

        campos.add(cmbTipo);

        campos.add(
                new JLabel(
                        "Bonificación / Comisión %:"
                )
        );

        campos.add(txtBonificacion);

        // =====================================================
        // BOTONES
        // =====================================================

        JPanel botones =
                new JPanel(
                        new FlowLayout()
                );

        JButton[] listaBotones = {
                btnAgregar,
                btnBuscar,
                btnActualizar,
                btnEliminar,
                btnLimpiar,
                btnHistorial,
                btnEstadisticas
        };

        for (JButton boton : listaBotones) {
            botones.add(boton);
        }

        JPanel panel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        0,
                        10
                )
        );

        panel.add(
                campos,
                BorderLayout.CENTER
        );

        panel.add(
                botones,
                BorderLayout.SOUTH
        );

        // Al iniciar, el campo está deshabilitado
        txtBonificacion.setEnabled(false);

        return panel;
    }

    // =========================================================
    // MÉTODO PARA LEER CAMPOS
    // =========================================================

    private String texto(JTextField campo) {

        return campo.getText().trim();
    }

    // =========================================================
    // TIPO SELECCIONADO
    // =========================================================

    private String tipoSeleccionado() {

        return (String) cmbTipo.getSelectedItem();
    }

    // =========================================================
    // TABLA
    // =========================================================

    private JScrollPane construirTabla() {

        String[] columnas = {
                "Cédula",
                "Nombre",
                "Tipo",
                "Salario base",
                "Salario total"
        };

        datosTabla =
                new DefaultTableModel(
                        columnas,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int fila,
                            int columna) {

                        return false;
                    }
                };

        JTable tabla =
                new JTable(datosTabla);

        JScrollPane scroll =
                new JScrollPane(tabla);

        scroll.setBorder(
                BorderFactory.createTitledBorder(
                        "Empleados registrados"
                )
        );

        return scroll;
    }

    // =========================================================
    // ACTUALIZAR TABLA
    // =========================================================

    private void refrescarTabla() {

        datosTabla.setRowCount(0);

        for (
                EmpleadoBase empleado :
                controlador.obtenerEmpleados()
        ) {

            Object[] fila = {

                    empleado.getCedula(),

                    empleado.getNombre(),

                    empleado.getTipo(),

                    formatoPesos(
                            empleado.getSalarioBase()
                    ),

                    formatoPesos(
                            empleado.calcularSalarioTotal()
                    )
            };

            datosTabla.addRow(fila);
        }

        lblResumen.setText(
                "Empleados: "
                        + datosTabla.getRowCount()
                        + " | Total nómina: "
                        + formatoPesos(
                        controlador.calcularTotalNomina()
                )
        );
    }

    // =========================================================
    // FORMATO DE DINERO
    // =========================================================

    private String formatoPesos(double valor) {

        return String.format(
                "$ %,.0f",
                valor
        );
    }

    // =========================================================
    // EVENTOS
    // =========================================================

    private void conectarEventos() {

        // Cambio de tipo

        cmbTipo.addActionListener(e -> {

            String tipo =
                    tipoSeleccionado();

            boolean habilitar =
                    tipo.equals("Administrativo")
                            || tipo.equals("Comercial");

            txtBonificacion.setEnabled(
                    habilitar
            );

            if (!habilitar) {
                txtBonificacion.setText("");
            }
        });

        // Agregar

        btnAgregar.addActionListener(e ->
                mostrarResultado(
                        controlador.agregarEmpleado(
                                texto(txtCedula),
                                texto(txtNombre),
                                texto(txtSalario),
                                tipoSeleccionado(),
                                texto(txtBonificacion)
                        )
                )
        );

        // Actualizar

        btnActualizar.addActionListener(e ->
                mostrarResultado(
                        controlador.actualizarEmpleado(
                                texto(txtCedula),
                                texto(txtNombre),
                                texto(txtSalario),
                                tipoSeleccionado(),
                                texto(txtBonificacion)
                        )
                )
        );

        // Buscar

        btnBuscar.addActionListener(
                e -> buscar()
        );

        // Eliminar

        btnEliminar.addActionListener(
                e -> eliminar()
        );

        // Limpiar

        btnLimpiar.addActionListener(
                e -> limpiarFormulario()
        );

        // Historial

        btnHistorial.addActionListener(
                e -> mostrarHistorial()
        );

        // Estadísticas

        btnEstadisticas.addActionListener(
                e -> mostrarEstadisticas()
        );
    }

    // =========================================================
    // MOSTRAR RESULTADO
    // =========================================================

    private void mostrarResultado(
            String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje
        );

        refrescarTabla();
    }

    // =========================================================
    // BUSCAR
    // =========================================================

    private void buscar() {

        String cedula =
                texto(txtCedula);

        if (cedula.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Escribe una cédula para buscar."
            );

            return;
        }

        EmpleadoBase empleado =
                controlador.buscarEmpleado(
                        cedula
                );

        if (empleado == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se encontró ningún empleado "
                            + "con la cédula "
                            + cedula
                            + "."
            );

            return;
        }

        txtNombre.setText(
                empleado.getNombre()
        );

        txtSalario.setText(
                String.format(
                        "%.0f",
                        empleado.getSalarioBase()
                )
        );

        cmbTipo.setSelectedItem(
                empleado.getTipo()
        );

        // Administrativo

        if (
                empleado
                        instanceof EmpleadoAdministrativo
        ) {

            EmpleadoAdministrativo administrativo =
                    (EmpleadoAdministrativo) empleado;

            txtBonificacion.setText(
                    String.format(
                            "%.0f",
                            administrativo.getBonificacion()
                    )
            );
        }

        // Comercial

        else if (
                empleado
                        instanceof EmpleadoComercial
        ) {

            EmpleadoComercial comercial =
                    (EmpleadoComercial) empleado;

            txtBonificacion.setText(
                    String.format(
                            "%.2f",
                            comercial.getPorcentajeComision()
                    )
            );
        }
    }

    // =========================================================
    // ELIMINAR
    // =========================================================

    private void eliminar() {

        String cedula =
                texto(txtCedula);

        if (cedula.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Escribe una cédula para eliminar."
            );

            return;
        }

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Seguro que deseas eliminar "
                                + "al empleado con cédula "
                                + cedula
                                + "?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                respuesta
                        == JOptionPane.YES_OPTION
        ) {

            mostrarResultado(
                    controlador.eliminarEmpleado(
                            cedula
                    )
            );

            limpiarFormulario();
        }
    }

    // =========================================================
    // LIMPIAR
    // =========================================================

    private void limpiarFormulario() {

        txtCedula.setText("");

        txtNombre.setText("");

        txtSalario.setText("");

        txtBonificacion.setText("");

        cmbTipo.setSelectedIndex(0);

        txtCedula.requestFocus();
    }

    // =========================================================
    // HISTORIAL
    // =========================================================

    private void mostrarHistorial() {

        ArrayList<String> historial =
                controlador.obtenerHistorial();

        if (historial.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Aún no hay operaciones registradas."
            );

            return;
        }

        String texto = "";

        for (
                int i = 0;
                i < historial.size();
                i++
        ) {

            texto +=
                    (i + 1)
                            + ". "
                            + historial.get(i)
                            + "\n";
        }

        JOptionPane.showMessageDialog(
                this,
                texto,
                "Historial de operaciones",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // ESTADÍSTICAS
    // =========================================================

    private void mostrarEstadisticas() {

        HashMap<String, Integer> estadisticas =
                new HashMap<>();

        // Inicializamos los tres tipos

        estadisticas.put(
                "Operativo",
                0
        );

        estadisticas.put(
                "Administrativo",
                0
        );

        estadisticas.put(
                "Comercial",
                0
        );

        // Contar empleados

        for (
                EmpleadoBase empleado :
                controlador.obtenerEmpleados()
        ) {

            String tipo =
                    empleado.getTipo();

            estadisticas.put(
                    tipo,
                    estadisticas.get(tipo) + 1
            );
        }

        // Crear mensaje

        String texto =
                "ESTADÍSTICAS DE EMPLEADOS\n\n";

        texto +=
                "Operativos: "
                        + estadisticas.get("Operativo")
                        + "\n";

        texto +=
                "Administrativos: "
                        + estadisticas.get("Administrativo")
                        + "\n";

        texto +=
                "Comerciales: "
                        + estadisticas.get("Comercial")
                        + "\n";

        texto +=
                "\nTotal empleados: "
                        + controlador
                        .obtenerEmpleados()
                        .size();

        JOptionPane.showMessageDialog(
                this,
                texto,
                "Estadísticas",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
