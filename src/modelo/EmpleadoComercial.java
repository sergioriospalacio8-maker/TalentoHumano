package modelo;

public class EmpleadoComercial extends EmpleadoBase{

    private double porcentajeComision;

    public EmpleadoComercial(String cedula, String nombre, double salarioBase, double porcentajeComision){

        super(cedula, nombre, salarioBase);
        this.porcentajeComision = porcentajeComision;
    }

    public double getPorcentajeComision() {
        return porcentajeComision;
    }

    @Override
    public double calcularSalarioTotal(){

        double comision = super.calcularSalarioTotal() * porcentajeComision / 100;
        return super.calcularSalarioTotal() + comision;
    }

    @Override
    public String getTipo(){
        return "Comercial";
    }
}



