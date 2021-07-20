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
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.joseluna.bean.TipoPlato;
import org.joseluna.db.Conexion;
import org.joseluna.system.Principal;


public class TipoPlatoController implements Initializable{
   private Object registro;
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<TipoPlato> listaTipoPlato;
    @FXML private TextField txtcodigoTipoPlatos;
    @FXML private TextField txtdescripcionTipoPlatos;
    @FXML private TableView tblTipoPlato;
    @FXML private TableColumn colcodigoTipoPlatos;
    @FXML private TableColumn coldescripcionTipoPlatos;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnReportar;
    @FXML private Button btnEliminar; 
    
    @Override
    public void initialize(URL lacation, ResourceBundle rusources) {
        cargarDatos();
    }   
   
    public void cargarDatos(){
       tblTipoPlato.setItems(getTipoPlato());
       colcodigoTipoPlatos.setCellValueFactory(new PropertyValueFactory<TipoPlato,Integer>("codigoTipoPlatos"));
       coldescripcionTipoPlatos.setCellValueFactory(new PropertyValueFactory<TipoPlato,String>("descripcionTipoPlatos"));
    }
    
    public ObservableList <TipoPlato>getTipoPlato(){
        ArrayList<TipoPlato> lista= new ArrayList<TipoPlato>();
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_ListarTipoPlato}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new TipoPlato(resultado.getInt("codigoTipoPlatos"),
                                        resultado.getString("descripcionTipoPlatos")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaTipoPlato = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemento(){
        txtcodigoTipoPlatos.setText(String.valueOf(((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlatos()));
        txtdescripcionTipoPlatos.setText(((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem()).getDescripcionTipoPlatos());
    }
    
    public void nuevo(){
         switch(tipoDeOperacion){
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
                 btnEliminar.setDisable(false);
                 btnEditar.setDisable(false);
                 btnReportar.setDisable(false);
                 tipoDeOperacion = operaciones.NINGUNO;
                 cargarDatos();
                 break;
         }
     }

     public void guardar(){
         TipoPlato registro = new TipoPlato();
         registro.setDescripcionTipoPlatos(txtdescripcionTipoPlatos.getText());
         try{
             PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_AgregarTipoPlato(?)}");
             procedimiento.setString(1,registro.getDescripcionTipoPlatos());
             procedimiento.execute();
             listaTipoPlato.add(registro);
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
               btnReportar.setDisable(false);
               tipoDeOperacion = operaciones.NINGUNO;
               break;
           default:
               if (tblTipoPlato.getSelectionModel().getSelectedItem()!=null){
                    int resultado = JOptionPane.showConfirmDialog(null,"Estas segura de Eliminar el registro?", "Eliminar Empresa",JOptionPane. YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(resultado == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_EliminarTipoEmpleado(?)}");
                            procedimiento.setInt(1,((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlatos());
                            procedimiento.execute();
                            listaTipoPlato.remove(tblTipoPlato.getSelectionModel().getSelectedItem());
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
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblTipoPlato.getSelectionModel().getSelectedItem() != null){
                  btnEditar.setText("Actualilzar");
                  btnReportar.setText("Cancelar");
                  btnNuevo.setDisable(true);
                  btnEliminar.setDisable(true);
                  activarControles();
                  tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
            break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReportar.setText("Reporte");
                btnNuevo.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
        }
}
      public void actualizar(){
          try{
           PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_ActualizarTipoPlato(?)}");
           TipoPlato  registro = (TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem();
           registro.setDescripcionTipoPlatos(txtdescripcionTipoPlatos.getText());
           procedimiento.setInt(1,registro.getCodigoTipoPlatos());
           procedimiento.setString(2,registro.getDescripcionTipoPlatos());
           procedimiento.execute();
           limpiarControles();
       }catch(Exception e ){
           e.printStackTrace();
       }
          
      }
      
     
     
     
     public void desactivarControles(){
         txtcodigoTipoPlatos.setEditable(false);
         txtdescripcionTipoPlatos.setEditable(false);
     }
     
     public void activarControles(){
         txtcodigoTipoPlatos.setEditable(false);
         txtdescripcionTipoPlatos.setEditable(true);
     }
     
     public void limpiarControles(){
         txtcodigoTipoPlatos.setText("");
         txtdescripcionTipoPlatos.setText("");
            
    }
     
       public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
     public void menuprincipal(){
        escenarioPrincipal.menuPrincipal();
    }
      public void ventanaPlatos(){
        escenarioPrincipal.ventanaPlatos();
    }

}