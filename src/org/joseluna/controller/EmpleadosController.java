package org.joseluna.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.joseluna.bean.Empleados;
import org.joseluna.system.Principal;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.joseluna.bean.TipoEmpleado;
import org.joseluna.db.Conexion;


public class EmpleadosController implements Initializable {
private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
   private Principal escenarioPrincipal;
   private operaciones tipoDeOperacion = operaciones.NINGUNO;
   private ObservableList<Empleados> listaEmpleado;
   private ObservableList<TipoEmpleado> listaTipoEmpleado;
   @FXML private TextField txtCodigoEmpleado;
   @FXML private TextField txtNumeroEmpleado;
   @FXML private TextField txtNombreEmpleado;
   @FXML private TextField txtApellidoEmpleado;
   @FXML private TextField txtDireccion;
   @FXML private TextField txtTelefono;
   @FXML private TextField txtGradoCocinero;
   @FXML private ComboBox cmbCodigoTipoEmpleado;
   @FXML private TableView tblEmpleados;
   @FXML private TableColumn colCodigoEmpleado;
   @FXML private TableColumn colNumeroEmpleado;
   @FXML private TableColumn colNombreEmpleado;
   @FXML private TableColumn colApellidoEmpleado;
   @FXML private TableColumn colDireccion;
   @FXML private TableColumn colTelefono;
   @FXML private TableColumn colGradoCocinero;
   @FXML private TableColumn colCodigoTipoEmpleado;
   @FXML private Button btnNuevo;
   @FXML private Button btnEditar;
   @FXML private Button btnReporte;
   @FXML private Button btnEliminar;
    @Override
    public void initialize(URL location, ResourceBundle rusources) {
        cargarDatos();
    }
    public void cargarDatos(){
        tblEmpleados.setItems(getEmpleado());
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<TipoEmpleado,Integer>("CodigoEmpleado"));
        colNumeroEmpleado.setCellValueFactory(new PropertyValueFactory<TipoEmpleado,String>("NumeroEmpleado"));
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<TipoEmpleado,String>("NombreEmpleado"));
        colApellidoEmpleado.setCellValueFactory(new PropertyValueFactory<TipoEmpleado,String>("ApellidoEmpleado"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<TipoEmpleado,String>("Direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<TipoEmpleado,String>("TElefono")); 
        colGradoCocinero.setCellValueFactory(new PropertyValueFactory<TipoEmpleado,String>("GradoCocinero"));
        colCodigoTipoEmpleado.setCellValueFactory(new PropertyValueFactory<TipoEmpleado,Integer>("CodigoTipoEmpleado"));       
                
    }
    
    public void seleccionarEmpelado(){
    txtCodigoEmpleado.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
    txtNumeroEmpleado.setText((((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNumeroEmpleado()));
    txtNombreEmpleado.setText((((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNombreEmpleado()));
    txtApellidoEmpleado.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidoEmpleado());
    txtDireccion.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getDireccionEmpleado());
    txtTelefono.setText((((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoContacto()));
    txtGradoCocinero.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getGradoCocinero());
    cmbCodigoTipoEmpleado.getSelectionModel().select(buscarTipoEmplado(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado()));  
}
 
   public ObservableList<Empleados>getEmpleado(){
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
       try{
           PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_listarEmpleado}");
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
   
   public TipoEmpleado buscarTipoEmplado(int codigoTipoEmpleado){
       TipoEmpleado resultado = null;
       try{
           PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_BuscarTipoEmpleado(?)}");
           procedimiento.setInt(1, codigoTipoEmpleado);
           ResultSet registro = procedimiento.executeQuery();
           while(registro.next()){
               resultado = new TipoEmpleado(registro.getInt("codigoTipoEmpleado"),
                                            registro.getString("descripcion"));  
           }
       }catch(Exception e){
           e.printStackTrace();
       }
       return resultado;
           
       }
   
   public void nuevo(){
         switch(tipoDeOperacion){
             case NINGUNO:
                 activarControles();
                 btnNuevo.setText("Guardar");
                 btnEliminar.setText("Cancelar");
                 btnEliminar.setDisable(false);
                 btnEditar.setDisable(true);
                 btnReporte.setDisable(true);
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
                 btnReporte.setDisable(true);
                 tipoDeOperacion = operaciones.NINGUNO;
                 cargarDatos();
                 break;
         }
     }
   
   
   public void guardar(){
       Empleados registro = new Empleados();
       registro.setNumeroEmpleado(txtNumeroEmpleado.getText());
       registro.setNombreEmpleado(txtNombreEmpleado.getText());
       registro.setApellidoEmpleado(txtApellidoEmpleado.getText());
       registro.setDireccionEmpleado(txtDireccion.getText());
       registro.setTelefonoContacto(txtTelefono.getText());
       registro.setGradoCocinero(txtGradoCocinero.getText());  
       registro.setCodigoTipoEmpleado(((TipoEmpleado)cmbCodigoTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
       try{
           PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_AgregarEmpleado(?, ?, ?, ?, ?, ?, ?)}");
           procedimiento.setString(1, registro.getNumeroEmpleado());
           procedimiento.setString(2, registro.getNombreEmpleado());
           procedimiento.setString(3, registro.getApellidoEmpleado());
           procedimiento.setString(4, registro.getDireccionEmpleado());
           procedimiento.setString(5, registro.getTelefonoContacto());
           procedimiento.setString(6, registro.getGradoCocinero());
           procedimiento.setInt(7, registro.getCodigoTipoEmpleado());
       }catch(Exception e ){
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
               btnReporte.setDisable(false);
               tipoDeOperacion = operaciones.NINGUNO;
               break;
           default:
               if (tblEmpleados.getSelectionModel().getSelectedItem()!=null){
                    int resultado = JOptionPane.showConfirmDialog(null,"Estas segura de Eliminar el registro?", "Eliminar Empresa",JOptionPane. YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(resultado == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_EliminarTipoEmpleado(?)}");
                            procedimiento.setInt(1,((TipoEmpleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
                            procedimiento.execute();
                            listaTipoEmpleado.remove(tblEmpleados.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
               }else{
                   JOptionPane.showMessageDialog(null,"Debe Seleccionar un Elemento");
               }
        }
   }
   
     public void editar(){
     switch (tipoDeOperacion){
         case NINGUNO:
             if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
                 btnEditar.setText("Actualizar");
                 btnReporte.setText("Cancelar");
                 btnNuevo.setDisable(true);
                 btnEliminar.setDisable(true);
                 activarControles();
                 tipoDeOperacion = operaciones.ACTUALIZAR;
             }else{
                 JOptionPane.showMessageDialog(null, "Debe Selecionar Un Elmento ");
             }
         break;
         case ACTUALIZAR:
             actualizar();
             btnEditar.setText("Editar");
             btnReporte.setText("Reporte");
             btnEliminar.setDisable(false);
             btnNuevo.setDisable(true);
             tipoDeOperacion = operaciones.NINGUNO;
             cargarDatos();
             break; 
     }
     
 }
     
     public void actualizar(){
      try{
      PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_ActualizarEmpleado(?, ?, ?, ?, ?, ?, ?) } ");
      Empleados registro = (Empleados)tblEmpleados.getSelectionModel().getSelectedItem();
      registro.setNumeroEmpleado(txtNumeroEmpleado.getText());
      registro.setNombreEmpleado(txtNombreEmpleado.getText());
      registro.setApellidoEmpleado(txtApellidoEmpleado.getText());
      registro.setDireccionEmpleado(txtDireccion.getText());
      registro.setTelefonoContacto(txtTelefono.getText());
      registro.setGradoCocinero(txtGradoCocinero.getText());
      procedimiento.setInt(1,registro.getCodigoEmpleado());
      procedimiento.setString(2, registro.getNumeroEmpleado());
      procedimiento.setString(3, registro.getNombreEmpleado());
      procedimiento.setString(4, registro.getApellidoEmpleado());
      procedimiento.setString(5, registro.getDireccionEmpleado());
      procedimiento.setString(6, registro.getTelefonoContacto());
      procedimiento.setString(7,registro.getGradoCocinero());
      procedimiento.execute();
      }catch(Exception e){
          e.printStackTrace();
      }
  }
     
   
   
    public void desactivarControles(){
         txtCodigoEmpleado.setEditable(false);
         txtNumeroEmpleado.setEditable(false);
         txtNombreEmpleado.setEditable(false);
         txtApellidoEmpleado.setEditable(false);
         txtDireccion.setEditable(false);
         txtTelefono.setEditable(false);
         txtGradoCocinero.setEditable(false);        
         cmbCodigoTipoEmpleado.setEditable(false);        
     }
     
     public void activarControles(){
         txtCodigoEmpleado.setEditable(false);
         txtNumeroEmpleado.setEditable(true);
         txtNombreEmpleado.setEditable(true);
         txtApellidoEmpleado.setEditable(true);
         txtDireccion.setEditable(true);
         txtTelefono.setEditable(true);
         txtGradoCocinero.setEditable(true);        
         cmbCodigoTipoEmpleado.setEditable(true); 
     }
     
     public void limpiarControles(){
         txtCodigoEmpleado.setText("");
         txtNumeroEmpleado.setText("");
        txtNombreEmpleado.setText("");
        txtApellidoEmpleado.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtGradoCocinero.setText("");
    }
            
 
       public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    
   }
    
    public void  setEscenarioPrincipal (Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
        
    }
     public void ventanaTipoEmpelado(){
        escenarioPrincipal.ventanaTipoEmpleado();
    }
    
}
