package org.joseluna.bean;

import java.util.Date;


public class Presupuesto {
   private int codigoPresupuesto;
   private Date fechaSolicitud;
   private double cantidadPresupuesto;
   private int codigoEmpresas;

    public Presupuesto() {
    }

    public Presupuesto(int codigoPresupuesto, Date fechaSolicitud, double cantidadPresupuesto, int codigoEmpresas) {
        this.codigoPresupuesto = codigoPresupuesto;
        this.fechaSolicitud = fechaSolicitud;
        this.cantidadPresupuesto = cantidadPresupuesto;
        this.codigoEmpresas = codigoEmpresas;
    }

    public int getCodigoPresupuesto() {
        return codigoPresupuesto;
    }

    public void setCodigoPresupuesto(int codigoPresupuesto) {
        this.codigoPresupuesto = codigoPresupuesto;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public double getCantidadPresupuesto() {
        return cantidadPresupuesto;
    }

    public void setCantidadPresupuesto(double cantidadPresupuesto) {
        this.cantidadPresupuesto = cantidadPresupuesto;
    }

    public int getCodigoEmpresas() {
        return codigoEmpresas;
    }

    public void setCodigoEmpresas(int codigoEmpresas) {
        this.codigoEmpresas = codigoEmpresas;
    }

}
