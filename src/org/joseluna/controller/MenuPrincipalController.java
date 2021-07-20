package org.joseluna.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.joseluna.system.Principal;

public class MenuPrincipalController implements Initializable{
  private Principal escenarioPrincipal ;

  
  @Override 
    public void initialize(URL location, ResourceBundle rusources) {
  
    }
  
    public Principal getEscenarioPrincipal (){
        return escenarioPrincipal;
    
   }
    
    public void  setEscenarioPrincipal (Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
        
    }
    public void ventanaProgramador(){
        escenarioPrincipal.ventanaProgramador();
        
    }
    
     public void ventanaEmpresa(){
       escenarioPrincipal.ventanaEmpresa();
  
   }
     
    public void ventaPresupuesto(){
       escenarioPrincipal.ventanaPresupuesto();
   }
    
    public void ventanaTipoEmpelado(){
        escenarioPrincipal.ventanaTipoEmpleado();
    }
    
    public void ventanaEmpleado(){
        escenarioPrincipal.ventanaEmpleado();
    }
    
    public void ventanaTipoPlato(){
        escenarioPrincipal.ventanaTipoPlato();
    }
    
    public void ventanaPlatos(){
        escenarioPrincipal.ventanaPlatos();
    }
    
    public void ventanaServicios(){
        escenarioPrincipal.ventanaServicios();
    }
    
    public void ventanaProductos(){
        escenarioPrincipal.ventanaProductos();
    }
    
    public void ventanaServiciosHasPlatos(){
        escenarioPrincipal.ventanaServiciosHasPlatos();
    }
    public void ventanaServiciosHasEmpleados(){
        escenarioPrincipal.ventanaServiciosHasEmpleados();
    }
    
    public void ventanaProductosHasPlatos(){
        escenarioPrincipal.ventanaProductosHasPlatos();
    }
}