package modelo;

public class EmpleadoBase {

    private final String cedula;
    private String nombre;
    private double salarioBase;

    public EmpleadoBase(String cedula, String nombre,double salarioBase){
        this.cedula = cedula;
        this.nombre = nombre;
        setSalarioBase(salarioBase);
    }
}
