package org.joseluna.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.joseluna.bean.Empresa;
import org.joseluna.bean.Servicios;
import org.joseluna.db.Conexion;
import org.joseluna.report.GenerarReporte;
import org.joseluna.system.Principal;

public class ServiciosController implements Initializable{

    private enum operaciones{ NUEVO, GUARDAR , ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <Servicios> listarServicios;
    private ObservableList <Empresa> listaEmpresa;
    private DatePicker fecha ;
    @FXML private TextField txtCodigoServicio; 
    @FXML private TextField txtTipoServicio;
    @FXML private TextField txtHoraServicio;
    @FXML private TextField txtLugarservicio;
    @FXML private TextField txtTelefonoContacto;
    @FXML private ComboBox cmbCodigoEmpresa;
    @FXML private GridPane grpFechaServicio;
    @FXML private TableView tblServicios;
    @FXML private TableColumn colCodigoServicio;
    @FXML private TableColumn colFechaServicio;
    @FXML private TableColumn colTipoServicio;
    @FXML private TableColumn colHoraServicio;
    @FXML private TableColumn colLugarServicio;
    @FXML private TableColumn colTelefonoContacto;
    @FXML private TableColumn colCodigoEmpresas;
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
        grpFechaServicio.add(fecha,0,0);
        cmbCodigoEmpresa.setItems(getEmpresa());
        
    }
   
    public void cargarDatos(){
        tblServicios.setItems(getServicio());
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<Servicios,Integer>("codigoServicio"));
        colFechaServicio.setCellValueFactory(new PropertyValueFactory<Servicios, Date>("fechaServicio"));
        colTipoServicio.setCellValueFactory(new PropertyValueFactory<Servicios, String>("tipoServicio"));
        colHoraServicio.setCellValueFactory(new PropertyValueFactory<Servicios, String>("horaServicio"));
        colLugarServicio.setCellValueFactory(new PropertyValueFactory<Servicios, String>("lugarServicio"));
        colTelefonoContacto.setCellValueFactory(new PropertyValueFactory<Servicios, String>("telefonoContacto"));
        colCodigoEmpresas.setCellValueFactory(new PropertyValueFactory<Servicios,Integer>("codigoEmpresas"));
    }
    
    
    public void seleccionarElemento(){
        txtCodigoServicio.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicio()));
        fecha.selectedDateProperty().set(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getFechaServicio());
        txtTipoServicio.setText(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getTipoServicio());
        txtHoraServicio.setText(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getHoraServicio());
        txtLugarservicio.setText(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getLugarServicio());
        txtTelefonoContacto.setText(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getTelefonoContacto());
        cmbCodigoEmpresa.getSelectionModel().select(buscarEmpresa(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoEmpresas()));
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
    
    public ObservableList<Empresa> getEmpresa(){
     ArrayList<Empresa> lista = new ArrayList<Empresa>();
     try {
         PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_listarEmpresas}");
         ResultSet resultado = procedimiento.executeQuery();
         while(resultado.next()){
             lista.add(new Empresa(resultado.getInt("codigoEmpresas"),
                                    resultado.getString("nombreEmpresa"),
                                    resultado.getString("direccion"),
                                    resultado.getString("telefono")));
         }
     }catch(Exception e){
        e.printStackTrace();
     }
             
        return listaEmpresa=FXCollections.observableArrayList(lista);
    }
    
  
  public Empresa buscarEmpresa(int codigoEmpresa){
        Empresa resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_BuscarEmpresa(?)}");
            procedimiento.setInt(1, codigoEmpresa);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Empresa(
                                 registro.getInt("codigoEmpresas"),
                                 registro.getString("nombreEmpresa"),
                                 registro.getString("direccion"),
                                 registro.getString("telefono"));
            }            
        }catch(Exception e){
            e.printStackTrace();
        }
                    return resultado;
        }
  public void nuevo(){
        switch (tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(true);
                btnReportar.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(true);
                btnEditar.setDisable(true);
                btnReportar.setDisable(true);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;    
        }
    }
  
  public void guardar(){
        Servicios registro = new Servicios();
        registro.setFechaServicio(fecha.getSelectedDate());
        registro.setTipoServicio(txtTipoServicio.getText());
        registro.setHoraServicio(txtHoraServicio.getText());
        registro.setLugarServicio(txtLugarservicio.getText());
        registro.setTelefonoContacto(txtTelefonoContacto.getText());
        registro.setCodigoEmpresas(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresas());
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_AgregarServicio(?,?,?,?,?,?)}");
            procedimiento.setDate(1, new  java.sql.Date(registro.getFechaServicio().getTime()));
            procedimiento.setString(2,registro.getTipoServicio());
            procedimiento.setString(3, registro.getHoraServicio());
            procedimiento.setString(4, registro.getLugarServicio());
            procedimiento.setString(5, registro.getTelefonoContacto());
            procedimiento.setInt(6, registro.getCodigoEmpresas());
            procedimiento.execute();
            listarServicios.add(registro);
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
                if(tblServicios.getSelectionModel().getSelectedItem()!=null){
                    int resultado = JOptionPane.showConfirmDialog(null, "Estas segura de Eliminar el registro?", "Eliminar Servicio",JOptionPane. YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                      if(resultado == JOptionPane.YES_OPTION){
                      try {
                          PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_EliminarServicio(?,?,?,?,?)}");
                          procedimiento.setInt(1,((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicio());
                          procedimiento.execute();
                          listarServicios.remove(tblServicios.getSelectionModel().getSelectedItem());
                          limpiarControles();
                      }catch(Exception e){
                          e.printStackTrace();
                         }
                    }
                }else{
                      JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
        }
    }
        public void editar(){
      switch (tipoDeOperacion){
          case NINGUNO:
              if(tblServicios.getSelectionModel().getSelectedItem() !=null){
                  btnEditar.setText("Actualizar");
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
              btnReportar.setText("Repote");
              btnEliminar.setDisable(false);
              btnNuevo.setDisable(false);
              tipoDeOperacion = operaciones.NINGUNO;
              cargarDatos();
              break; 
      }
  }
     
  
  public void actualizar(){
      try {
          PreparedStatement  procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_ActualizarServicio(?,?,?,?,?)}");
          Servicios registro = (Servicios)tblServicios.getSelectionModel().getSelectedItem();
          registro.setFechaServicio(fecha.getSelectedDate());
          registro.setTipoServicio(txtTipoServicio.getText());
          registro.setHoraServicio(txtHoraServicio.getText());
          registro.setLugarServicio(txtLugarservicio.getText());
          registro.setTelefonoContacto(txtTelefonoContacto.getText());
          registro.setCodigoEmpresas(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresas());
          procedimiento.setInt(1,registro.getCodigoServicio());
          procedimiento.setDate(2, new  java.sql.Date(registro.getFechaServicio().getTime()));
          procedimiento.setString(3,registro.getTipoServicio());
          procedimiento.setString(4, registro.getHoraServicio());
          procedimiento.setString(5, registro.getLugarServicio());
          procedimiento.setString(6, registro.getTelefonoContacto());
          procedimiento.execute();
      }catch(Exception e){
          e.printStackTrace();
      }
  }
 
   public void generarReporte (){
        switch(tipoDeOperacion){
            case NINGUNO:
                imprimirReporte();
            break;
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
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        int codServicio = Integer.valueOf(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresas());
        parametros.put("codServicio",codServicio);
        GenerarReporte.mostrarReporte("Reporte Servicio.jasper","Reporte Servicio",parametros);                 
    }
  
  
 public void desactivarControles(){
        txtCodigoServicio.setEditable(false);
        txtTipoServicio.setEditable(false);
        txtHoraServicio.setEditable(false);
        txtLugarservicio.setEditable(false);
        txtTelefonoContacto.setEditable(false);
        grpFechaServicio.setDisable(true);
        cmbCodigoEmpresa.setEditable(false);
        cmbCodigoEmpresa.setDisable(true);
    }
    
    public void activarControles (){
        txtCodigoServicio.setEditable(false);
        txtTipoServicio.setEditable(true);
        txtHoraServicio.setEditable(true);
        txtLugarservicio.setEditable(true);
        txtTelefonoContacto.setEditable(true);
        grpFechaServicio.setDisable(false);
        cmbCodigoEmpresa.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoServicio.setText("");
        txtTipoServicio.setText("");
        txtHoraServicio.setText("");
        txtLugarservicio.setText("");
        txtTelefonoContacto.setText("");
        cmbCodigoEmpresa.getSelectionModel().clearSelection();
    }
    public Principal getEscenarioPrincipal(){
     return escenarioPrincipal;
    
   }
    
    public void  setEscenarioPrincipal (Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
        
    }
     public void menuPrincipal (){
        escenarioPrincipal.menuPrincipal();
  
     } 
}
