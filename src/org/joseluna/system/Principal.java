package org.joseluna.system;



import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.joseluna.controller.DatosPersonalesController;
import org.joseluna.controller.EmpleadosController;
import org.joseluna.controller.EmpresaController;
import org.joseluna.controller.MenuPrincipalController;
import org.joseluna.controller.PlatosController;
import org.joseluna.controller.PresupuestoController;
import org.joseluna.controller.ProductosController;
import org.joseluna.controller.ProductosHasPlatosController;
import org.joseluna.controller.ServiciosController;
import org.joseluna.controller.ServiciosHasEmpleadosController;
import org.joseluna.controller.ServiciosHasPlatosController;
import org.joseluna.controller.TipoEmpleadoController;
import org.joseluna.controller.TipoPlatoController;



public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/joseluna/view/";
    private Stage escenarioPrincipal; 
    private Scene escena;
   

    @Override
    
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Tony's Kinal App");
        escenarioPrincipal.getIcons().add(new Image("/org/joseluna/image/Icono.png"));       
        //Parent root = FXMLLoader.load(getClass().getResource("/org/joseluna/view/MenuPrincipalView.fxml"));
        //Scene escena = new Scene (root);
        //escenarioPrincipal.setScene(escena);
        menuPrincipal();
        escenarioPrincipal.show();   
    }
    
  
    public void menuPrincipal(){
        try {
            MenuPrincipalController menuPrincipal =(MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",370,370);
            menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    public void ventanaProgramador(){
        try {
            DatosPersonalesController datosPersonales  = (DatosPersonalesController)cambiarEscena("DatosProgramadorView.fxml",600,400);
            datosPersonales.setEscenarioPrincipal(this);
        }catch (Exception a){
            a.printStackTrace();
        }
    }
    public void ventanaEmpresa(){
        try{
        EmpresaController datosEmpresa=(EmpresaController)cambiarEscena("EmpresasView.fxml",600,400);
         datosEmpresa.setEscenarioPrincipal(this);
        }catch(Exception c){
            c.printStackTrace();
        }
    }
    public void ventanaPresupuesto(){
        try{
        PresupuestoController presupuesto = (PresupuestoController)cambiarEscena("PresupuestoView.fxml",629,505);
        presupuesto.setEscenarioPrincipal(this);
        }catch(Exception c){
            c.printStackTrace();
        }
    }
    
  public void ventanaTipoEmpleado(){
        try{
            TipoEmpleadoController tipoEmpleado = (TipoEmpleadoController)cambiarEscena("TipoEmpleadoView.fxml",600 ,400);
            tipoEmpleado.setEscenarioPrincipal(this);
        }catch (Exception e ){
            e.printStackTrace();
        }
        
    }
  
  public void ventanaEmpleado(){
        try{
            EmpleadosController Empleados =(EmpleadosController)cambiarEscena("EmpleadosView.fxml",780 ,479);
            Empleados.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
  
  public void ventanaTipoPlato(){
      try{
          TipoPlatoController TipoPlato =(TipoPlatoController)cambiarEscena("TipoPlatoView.fxml",534 ,419);
          TipoPlato.setEscenarioPrincipal(this);
      }catch (Exception e){
          e.printStackTrace();
      }
  }
  
  public void ventanaPlatos(){
      try{
          PlatosController Platos =(PlatosController)cambiarEscena("PlatosView.fxml",670,449);
          Platos.setEscenarioPrincipal(this);
      }catch (Exception e){
          e.printStackTrace();
      }
  }
  
  public void ventanaServicios(){
      try{
         ServiciosController Servicios =(ServiciosController)cambiarEscena("ServiciosView.fxml",714,431);
         Servicios.setEscenarioPrincipal(this);
      }catch (Exception e){
          e.printStackTrace();
      }
  }
  
  public void ventanaProductos(){
      try{
         ProductosController Productos =(ProductosController)cambiarEscena("ProductosView.fxml",602,426);
         Productos.setEscenarioPrincipal(this);
      }catch (Exception e){
          e.printStackTrace();
      }
  }
  
  public void ventanaServiciosHasPlatos(){
      try{
          ServiciosHasPlatosController ServiciosHP =(ServiciosHasPlatosController)cambiarEscena("ServiciosHasPlatosView.fxml",488,334);
          ServiciosHP.setEscenariPrincipal(this);
      }catch (Exception e){
          e.printStackTrace();
      }
  }
  
  public void ventanaServiciosHasEmpleados(){
      try{
          ServiciosHasEmpleadosController ServiciosHE =(ServiciosHasEmpleadosController)cambiarEscena("ServiciosHasEmpleadosView.fxml",636,415);
          ServiciosHE.setEscenarioPrincipal(this);
      }catch (Exception e){
          e.printStackTrace();
      }
  }
  
  public void ventanaProductosHasPlatos(){
      try{
          ProductosHasPlatosController ProductosHP =(ProductosHasPlatosController)cambiarEscena("ProductosHasPlatosView.fxml",440,361);
          ProductosHP.setEscenariPrincipal(this);
      }catch (Exception e){
          e.printStackTrace();
      }
  }
  
        
    public static void main(String[] args) {
        launch(args);
    }
    public Initializable cambiarEscena (String fxml,int ancho, int alto) throws Exception {
        Initializable resultado = null;
        FXMLLoader cargadorFXML= new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return  resultado; 
    }
}