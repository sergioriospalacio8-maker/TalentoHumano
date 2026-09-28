import javax.swing.*;

public class Main {

    public static void main(String[] args){

        SwingUtilities.invokeLater(() -> {

            EmpleadoControlador controlador = new EmpleadoControlador();

            VentanaEmpleado ventana = new VentanaEmpleados(controlador);

            ventana.setVisible(true);
        });
    }
}