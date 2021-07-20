package org.joseluna.bean;



public class ProductosHasPlatos {
    private int codigoPlatos;
    private int codigoProducto;

    public ProductosHasPlatos() {
    }

    public ProductosHasPlatos(int codigoPlatos, int codigoProducto) {
        this.codigoPlatos = codigoPlatos;
        this.codigoProducto = codigoProducto;
    }

    public int getCodigoPlatos() {
        return codigoPlatos;
    }

    public void setCodigoPlatos(int codigoPlatos) {
        this.codigoPlatos = codigoPlatos;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    

}
