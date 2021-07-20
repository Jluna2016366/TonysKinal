package org.joseluna.bean;

import java.sql.Date;


public class ServiciosHasEmpleados {
    
    private int codigoServicioshasEmpleados;
    private Date fechaEvento;
    private String horaEvento;
    private String lugarEvento;
    private int codigoServicio;
    private int codigoEmpleado;

    public ServiciosHasEmpleados() {
    }

    public ServiciosHasEmpleados(int codigoServicioshasEmpleados, Date fechaEvento, String horaEvento, String lugarEvento, int codigoServicio, int codigoEmpleado) {
        this.codigoServicioshasEmpleados = codigoServicioshasEmpleados;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.lugarEvento = lugarEvento;
        this.codigoServicio = codigoServicio;
        this.codigoEmpleado = codigoEmpleado;
    }

    public int getCodigoServicioshasEmpleados() {
        return codigoServicioshasEmpleados;
    }

    public void setCodigoServicioshasEmpleados(int codigoServicioshasEmpleados) {
        this.codigoServicioshasEmpleados = codigoServicioshasEmpleados;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

    public String getLugarEvento() {
        return lugarEvento;
    }

    public void setLugarEvento(String lugarEvento) {
        this.lugarEvento = lugarEvento;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

   
}
