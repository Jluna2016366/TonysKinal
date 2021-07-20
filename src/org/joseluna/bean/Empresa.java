
package org.joseluna.bean;


public class Empresa {
    private int codigoEmpresas;  
    private String nombreEmpresa ;
    private String direccion;
    private String telefono;

    public Empresa() {
    }

    public Empresa(int codigoEmpresas, String nombreEmpresa, String direccion, String telefono) {
        this.codigoEmpresas = codigoEmpresas;
        this.nombreEmpresa = nombreEmpresa;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getCodigoEmpresas() {
        return codigoEmpresas;
    }

    public void setCodigoEmpresas(int codigoEmpresas) {
        this.codigoEmpresas = codigoEmpresas;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String  toString(){
        return getCodigoEmpresas()+ "  " + getNombreEmpresa();
    }     

    public String getCodigoEmpresa() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

