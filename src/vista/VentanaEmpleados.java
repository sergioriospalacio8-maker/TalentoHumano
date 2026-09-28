package vista;

import javax.swing.*;

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


}

