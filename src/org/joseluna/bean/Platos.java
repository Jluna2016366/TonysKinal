package org.joseluna.bean;

public class Platos {

private int codigoPlatos;
private String cantidad;
private String nombrePlato;
private String descripcionPlato;
private double precioPlato;
private int CodigoTipoPlatos;

    public Platos() {
    }

    public Platos(int codigoPlatos, String cantidad, String nombrePlato, String descripcionPlato, double precioPlato, int CodigoTipoPlatos) {
        this.codigoPlatos = codigoPlatos;
        this.cantidad = cantidad;
        this.nombrePlato = nombrePlato;
        this.descripcionPlato = descripcionPlato;
        this.precioPlato = precioPlato;
        this.CodigoTipoPlatos = CodigoTipoPlatos;
    }

    public int getCodigoPlatos() {
        return codigoPlatos;
    }

    public void setCodigoPlatos(int codigoPlatos) {
        this.codigoPlatos = codigoPlatos;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public String getDescripcionPlato() {
        return descripcionPlato;
    }

    public void setDescripcionPlato(String descripcionPlato) {
        this.descripcionPlato = descripcionPlato;
    }

    public double getPrecioPlato() {
        return precioPlato;
    }

    public void setPrecioPlato(double precioPlato) {
        this.precioPlato = precioPlato;
    }

    public int getCodigoTipoPlatos() {
        return CodigoTipoPlatos;
    }

    public void setCodigoTipoPlatos(int CodigoTipoPlatos) {
        this.CodigoTipoPlatos = CodigoTipoPlatos;
    }
    
    public String  toString(){
        return getCodigoPlatos()+ "  " + getNombrePlato();
    } 
}
