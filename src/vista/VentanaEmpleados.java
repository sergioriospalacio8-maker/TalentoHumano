package vista;

import javax.swing.*;

public class VentanaEmpleados extends JFrame {

    private final EmpleadoControlador controlador;

    private final JTextField txtCedula = new JTextField();
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtSalario = new JTextField();
    private final JTextField textBonificacion = new JTextField();
    private final JComboBox<String> cmbTipo = new JComboBox<>(EmpleadoControlador.TIPOS_EMPLEADO);


}

