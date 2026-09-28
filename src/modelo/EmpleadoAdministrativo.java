package modelo;

public class EmpleadoAdministrativo extends EmpleadoBase {

    private double bonificacion;

    public EmpleadoAdministrativo(String cedula, String nombre, double salarioBase, double bonificacion){

        super(cedula, nombre, salarioBase);
        this.bonificacion = bonificacion;

    }
}
