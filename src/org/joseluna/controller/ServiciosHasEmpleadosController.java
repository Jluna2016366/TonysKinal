package org.joseluna.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.joseluna.bean.Empleados;
import org.joseluna.bean.Servicios;
import org.joseluna.bean.ServiciosHasEmpleados;
import org.joseluna.db.Conexion;
import org.joseluna.system.Principal;

public class ServiciosHasEmpleadosController implements Initializable{

    private enum operaciones{ NUEVO, GUARDAR , ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
private Principal escenarioPrincipal;
private operaciones tipoDeOperacion = operaciones.NINGUNO;
private ObservableList <ServiciosHasEmpleados> listarSerhasEmp;
private ObservableList <Servicios> listarServicios;
private ObservableList <Empleados> listaEmpleado;
private DatePicker fecha ;
@FXML private TextField txtCodigoServicioshasEmpleado;
@FXML private TextField txtHoraEvento;
@FXML private TextField txtLugarEvento;
@FXML private ComboBox cmbCodigoServicio;
@FXML private ComboBox cmbCodigoEmpleado;       
@FXML private GridPane grpFechaEvento;
@FXML private TableView tblServiciohasEmpleado;
@FXML private TableColumn colCodigoServicioshasEmpleado;
@FXML private TableColumn colFechaEvento;
@FXML private TableColumn colHoraEvento;
@FXML private TableColumn colLugarEvento;
@FXML private TableColumn colCodigoServicio;
@FXML private TableColumn colCodigoEmpleado;
@FXML private Button btnNuevo;
@FXML private Button btnEditar;
@FXML private Button btnEliminar;
@FXML private Button btnReportar;
    

@Override
    public void initialize(URL location, ResourceBundle rusources) {
        cargarDatos();
        fecha = new  DatePicker (Locale.ENGLISH);
        fecha.setDateFormat(new  SimpleDateFormat("yyy-MM-dd"));
        fecha.getCalendarView().todayButtonTextProperty().set("Todasy");
        fecha.getCalendarView().setShowWeeks(false);
        fecha.getStylesheets().add("/org/joseluna/resource/DatePicker.css");
        grpFechaEvento.add(fecha,0,0);
        cmbCodigoServicio.setItems(getServicio());
        cmbCodigoEmpleado.setItems(getEmpleados());
    }
    
  public void cargarDatos(){
        tblServiciohasEmpleado.setItems(getSerhasEmp());
        colCodigoServicioshasEmpleado.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados,Integer>("codigoServicioshasEmpleados"));
        colFechaEvento.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados,Date>("fechaEvento"));
        colHoraEvento.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados,String>("horaEvento"));
        colLugarEvento.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados,String>("lugarEvento"));
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados,Integer>("codigoServicio"));
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados,Integer>("codigoEmpleado"));
    }
    
    public void seleccionarElemento(){
        txtCodigoServicioshasEmpleado.setText(String.valueOf(((ServiciosHasEmpleados)tblServiciohasEmpleado.getSelectionModel().getSelectedItem()).getCodigoServicioshasEmpleados()));  
        fecha.selectedDateProperty().set(((ServiciosHasEmpleados)tblServiciohasEmpleado.getSelectionModel().getSelectedItem()).getFechaEvento());
        txtHoraEvento.setText(((ServiciosHasEmpleados)tblServiciohasEmpleado.getSelectionModel().getSelectedItem()).getHoraEvento());
        txtLugarEvento.setText(((ServiciosHasEmpleados)tblServiciohasEmpleado.getSelectionModel().getSelectedItem()).getLugarEvento());
        cmbCodigoServicio.getSelectionModel().select(buscarServicio(((ServiciosHasEmpleados)tblServiciohasEmpleado.getSelectionModel().getSelectedItem()).getCodigoServicio()));
        cmbCodigoEmpleado.getSelectionModel().select(buscarEmpleado(((ServiciosHasEmpleados)tblServiciohasEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));  
    }
    
    
        
       public ObservableList <ServiciosHasEmpleados> getSerhasEmp(){
           ArrayList<ServiciosHasEmpleados> lista = new ArrayList<ServiciosHasEmpleados>();
           try{
              PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_ListarServicioshasEmpleados}");
              ResultSet resultado = procedimiento.executeQuery();
               while(resultado.next()){
                    lista.add(new ServiciosHasEmpleados(resultado.getInt("codigoServicioshasEmpleados"),
                                        resultado.getDate("fechaEvento"),
                                        resultado.getString("horaEvento"),
                                        resultado.getString("lugarEvento"),
                                        resultado.getInt("codigoServicio"),
                                        resultado.getInt("codigoEmpleado")));
               }
           }catch(Exception e ){
              e.printStackTrace();
           }
                return listarSerhasEmp = FXCollections.observableArrayList(lista);
   
       }
    

      public ObservableList <Servicios> getServicio(){
        ArrayList<Servicios> lista = new ArrayList<Servicios>();
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_listarServicio}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Servicios(resultado.getInt("codigoServicio"),
                                       resultado.getDate("fechaServicio"),
                                       resultado.getString("tipoServicio"),
                                       resultado.getString("horaServicio"),
                                       resultado.getString("lugarServicio"),
                                       resultado.getString("telefonoContacto"),
                                       resultado.getInt("codigoEmpresas")));
            }
        }catch(Exception e ){
        e.printStackTrace();
        } 
        return listarServicios = FXCollections.observableArrayList(lista);
    }
      
      
      public Servicios buscarServicio(int codigoServicio){
         Servicios resultado = null;
          try{
           PreparedStatement procedimiento  = Conexion.getIntance().getConexion().prepareCall("{call sp_BuscarServicio(?)}");
           procedimiento.setInt(1, codigoServicio);
           ResultSet registro = procedimiento.executeQuery();   
           while(registro.next()){
             resultado = new Servicios(registro.getInt("codigoServicio"),
                                          registro.getDate("fechaServicio"),
                                          registro.getString("tipoServicio"),
                                          registro.getString("horaServicio"),
                                          registro.getString("lugarServicio"),
                                          registro.getString("telefonoContacto"),
                                          registro.getInt("codigoEmpresas")
                                      );
           }                          
      }catch(Exception e){
          e.printStackTrace();
      }
             return resultado;

      }
      
      
      
      
    public ObservableList<Empleados>getEmpleados(){
    ArrayList<Empleados> lista = new ArrayList<Empleados>();
       try{
           PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_ListarEmpleado}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                         resultado.getString("numeroEmpleado"),
                        resultado.getString("nombreEmpleado"),
                        resultado.getString("apellidoEmpleado"),
                        resultado.getString("direccionEmpleado"),
                        resultado.getString("telefonoContacto"),
                        resultado.getString("gradoCocinero"),
                        resultado.getInt("codigoTipoEmpleado")));
            }
       }catch(Exception e ){
           e.printStackTrace();
       }
       
       return listaEmpleado = FXCollections.observableArrayList(lista);
   }
    
    
      public Empleados buscarEmpleado(int codigoEmpleado){
     Empleados resultado = null;
       try{
           PreparedStatement procedimiento  = Conexion.getIntance().getConexion().prepareCall("{call sp_BuscarEmpleado(?)}");
           procedimiento.setInt(1, codigoEmpleado);
           ResultSet registro = procedimiento.executeQuery();
           while(registro.next()){
             resultado = new Empleados(registro.getInt("codigoEmpleado"),
                                            registro.getString("numeroEmpleado"),
                                            registro.getString("nombreEmpleado"),
                                            registro.getString("apellidoEmpleado"),
                                            registro.getString("direccionEmpleado"),
                                            registro.getString("telefonoContacto"),
                                            registro.getString("gradoCocinero"),
                                            registro.getInt("codigoTipoEmpleado")
                     
             );
               
           }
       }catch(Exception e){
           e.printStackTrace();
       }
       return resultado;
           
       }
      
      
      public void nuevo (){
        switch (tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(true);
                btnReportar.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
                break ;
            case  GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);
                btnReportar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break ;
                
        }
    }
      
      public void guardar(){
       ServiciosHasEmpleados registro = new ServiciosHasEmpleados();
       registro.setFechaEvento((java.sql.Date) fecha.getSelectedDate());
       registro.setHoraEvento(txtHoraEvento.getText());
       registro.setLugarEvento(txtLugarEvento.getText());
       registro.setCodigoServicio(((Servicios)cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
       registro.setCodigoEmpleado(((Empleados)cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
       try{
           PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call  sp_AgregarServicioshasEmpleados(?,?,?,?,?)}");
           procedimiento.setDate(1, new java.sql.Date(registro.getFechaEvento().getTime()));
           procedimiento.setString(2,registro.getHoraEvento());
           procedimiento.setString(3, registro.getLugarEvento());
           procedimiento.setInt(4,registro.getCodigoServicio());
           procedimiento.setInt(5,registro.getCodigoEmpleado());
           procedimiento.executeQuery();
           listarSerhasEmp.add(registro);
       }catch(Exception e){
           e.printStackTrace();
       }
          
   }
      
      
      public void eliminar(){
      switch(tipoDeOperacion){
          case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnNuevo.setDisable(false);
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);
                btnReportar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
          default:
              if(tblServiciohasEmpleado.getSelectionModel().getSelectedItem() !=null){
                        int resultado = JOptionPane.showConfirmDialog(null, "Estas seguro de Eliminar el registro?", "Eliminar Registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(resultado == JOptionPane.YES_OPTION){
                            try{
                                PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{ call sp_EliminarServicioshasEmpleados(?)}");
                                procedimiento.setInt(1,((ServiciosHasEmpleados)tblServiciohasEmpleado.getSelectionModel().getSelectedItem()).getCodigoServicioshasEmpleados());    
                                procedimiento.execute();
                                listarSerhasEmp.remove(tblServiciohasEmpleado.getSelectionModel().getSelectedItem());
                                limpiarControles();
                            }catch(Exception e ){
                                e.printStackTrace(); 
                            }
                        }
                        }else{
                      JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
          
        }
  }
      
     
      
  public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblServiciohasEmpleado.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReportar.setDisable(false);
                    btnReportar.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe Seleccionar un Elemento");
            }
            break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReportar.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        } 
  }
         
  
  public void  actualizar(){
    try{
     PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_ActualizarServicioshasEmpleados( ?, ?, ?, ?)}");
        ServiciosHasEmpleados registro = (ServiciosHasEmpleados)tblServiciohasEmpleado.getSelectionModel().getSelectedItem();
            registro.setFechaEvento((java.sql.Date) fecha.getSelectedDate());
            registro.setHoraEvento(txtHoraEvento.getText());
            registro.setLugarEvento(txtLugarEvento.getText());
                procedimiento.setInt(1, registro.getCodigoServicioshasEmpleados());
                procedimiento.setDate(2, new java.sql.Date(registro.getFechaEvento().getTime()));
                procedimiento.setString(3, registro.getHoraEvento());
                procedimiento.setString(4, registro.getLugarEvento()); 
                procedimiento.execute();
    } catch(Exception e ) {
        e.printStackTrace();
    }

  }
      
       public void cancelar(){
       switch(tipoDeOperacion){
           case ACTUALIZAR:
               desactivarControles();
               limpiarControles();
               btnEditar.setText("Editar");
               btnEditar.setDisable(false);
               btnReportar.setText("Reporte");
               btnReportar.setDisable(false);
               btnNuevo.setDisable(false);
               btnEliminar.setDisable(false);
               tipoDeOperacion = operaciones.NINGUNO;
               break;
            }
   }
       
      
     public void desactivarControles(){
         grpFechaEvento.setDisable(false);
         txtHoraEvento.setEditable(false);
         txtLugarEvento.setEditable(false);
         cmbCodigoServicio.setDisable(false);
         cmbCodigoEmpleado.setDisable(false);
     } 
     
     
     public void activarControles(){
         grpFechaEvento.setDisable(false);
         txtHoraEvento.setEditable(true);
         txtLugarEvento.setEditable(true);
         cmbCodigoServicio.setDisable(false);
         cmbCodigoEmpleado.setDisable(false);
     }
     
     public void limpiarControles(){
         txtHoraEvento.setText("");  
         txtLugarEvento.setText("");
         cmbCodigoServicio.getSelectionModel().getSelectedItem();
         cmbCodigoEmpleado.getSelectionModel().getSelectedItem();
     }
      

      public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal (){
        escenarioPrincipal.menuPrincipal();
  
     }
     
    public void menuprincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
