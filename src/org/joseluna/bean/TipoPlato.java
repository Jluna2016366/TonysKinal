package org.joseluna.bean;

public class TipoPlato {
    private int codigoTipoPlatos;
    private String descripcionTipoPlatos;

    public TipoPlato() {
    }

    public TipoPlato(int codigoTipoPlatos, String descripcionTipoPlatos) {
        this.codigoTipoPlatos = codigoTipoPlatos;
        this.descripcionTipoPlatos = descripcionTipoPlatos;
    }

    public int getCodigoTipoPlatos() {
        return codigoTipoPlatos;
    }

    public void setCodigoTipoPlatos(int codigoTipoPlatos) {
        this.codigoTipoPlatos = codigoTipoPlatos;
    }

    public String getDescripcionTipoPlatos() {
        return descripcionTipoPlatos;
    }

    public void setDescripcionTipoPlatos(String descripcionTipoPlatos) {
        this.descripcionTipoPlatos = descripcionTipoPlatos;
    }
    
    public String  toString(){
        return getCodigoTipoPlatos()+ "  " +getDescripcionTipoPlatos ();
    } 
    
}
