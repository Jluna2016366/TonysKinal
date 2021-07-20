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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.joseluna.bean.Platos;
import org.joseluna.bean.Productos;
import org.joseluna.bean.ProductosHasPlatos;
import org.joseluna.db.Conexion;
import org.joseluna.system.Principal;


public class ProductosHasPlatosController implements Initializable{

    private Principal escenariPrincipal;
    private  ObservableList <ProductosHasPlatos> listarProHaPla;
    private  ObservableList <Productos> listarProductos;
    private  ObservableList <Platos> listarPlatos;
    @FXML private ComboBox  cmbcodigoProducto;
    @FXML private ComboBox cmbcodigoPlatos;
    @FXML private TableView tblProductosHasPlatos;
    @FXML private TableColumn colcodigoProducto;
    @FXML private TableColumn colcodigoPlatos;
    
    @Override
    public void initialize(URL location, ResourceBundle rusources) {
       cargarDatos();
    }
    
    public void cargarDatos(){
        tblProductosHasPlatos.setItems(getProductosHasPlatos());
        colcodigoProducto.setCellValueFactory(new PropertyValueFactory<ProductosHasPlatos,Integer>("codigoProducto"));
        colcodigoPlatos.setCellValueFactory(new PropertyValueFactory<ProductosHasPlatos,Integer>("codigoPlatos"));
    }
    
    public void seleccionarElemento(){
        cmbcodigoProducto.getSelectionModel().select(buscarProductos(((ProductosHasPlatos)tblProductosHasPlatos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        cmbcodigoPlatos.getSelectionModel().select(buscarPlatos(((ProductosHasPlatos)tblProductosHasPlatos.getSelectionModel().getSelectedItem()).getCodigoPlatos()));
    }

    
    
    public ObservableList <ProductosHasPlatos> getProductosHasPlatos(){
        ArrayList<ProductosHasPlatos> lista = new ArrayList<ProductosHasPlatos>();
        try{
           PreparedStatement   procedimietno = Conexion.getIntance().getConexion().prepareCall("{call sp_ListarProductosHasPlatos}");
           ResultSet resultado = procedimietno.executeQuery();
           while(resultado.next()){
                   lista.add(new ProductosHasPlatos(resultado.getInt("codigoProducto"),
                                                    resultado.getInt("codigoPlatos")));
               
           }
            
        }catch(Exception e){
          e.printStackTrace();
        }
        return listarProHaPla = FXCollections.observableArrayList(lista);
    }
    
    
    
    public ObservableList <Productos> getProductos(){
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_ListarProducto}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
               lista.add(new Productos(resultado.getInt("codigoProducto"),
                                       resultado.getString("nombreProducto"),
                                       resultado.getString("cantidad")));
            }
        }catch(Exception e ){
        e.printStackTrace();
        } 
        return listarProductos = FXCollections.observableArrayList(lista);
    }
    
      
      
      public Productos buscarProductos(int codigoProducto){
         Productos resultado = null;
          try{
           PreparedStatement procedimiento  = Conexion.getIntance().getConexion().prepareCall("{call sp_BuscarProducto(?)}");
           procedimiento.setInt(1, codigoProducto);
           ResultSet registro = procedimiento.executeQuery();   
           while(registro.next()){
             resultado = new Productos(registro.getInt("codigoProducto"),
                                       registro.getString("nombreProducto"),
                                       registro.getString("cantidad"));
           }                          
      }catch(Exception e){
          e.printStackTrace();
      }
             return resultado;

      }
   
      
         
    public ObservableList<Platos>getPlatos(){
        ArrayList<Platos> lista = new ArrayList<Platos>();
        try{
            PreparedStatement procedimiento =  Conexion.getIntance().getConexion().prepareCall("{call sp_ListarPlato}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Platos(resultado.getInt("codigoPlatos"),
                                resultado.getString("cantidad"),
                                resultado.getString("nombrePlato"),
                                resultado.getString("descripcionPlato"),
                                resultado.getDouble("precioPlato"),
                                resultado.getInt("CodigoTipoPlatos")));
                
            }
        }catch(Exception e ){
            e.printStackTrace();
        }
        return  listarPlatos = FXCollections.observableArrayList(lista);
    }
    
    
    
    
      public Platos buscarPlatos(int codigoPlatos){
         Platos resultado = null;
          try{
           PreparedStatement procedimiento  = Conexion.getIntance().getConexion().prepareCall("{call sp_BuscarPlato(?)}");
           procedimiento.setInt(1,codigoPlatos);
           ResultSet registro = procedimiento.executeQuery();   
           while(registro.next()){
                resultado = new Platos(registro.getInt("codigoPlatos"),
                                       registro.getString("cantidad"),
                                       registro.getString("nombrePlato"),
                                       registro.getString("descripcionPlato"),
                                       registro.getDouble("precioPlato"),
                                       registro.getInt("CodigoTipoPlatos"));
           }                          
      }catch(Exception e){
          e.printStackTrace();
      }
           return resultado;
      }
       
 
      
    public Principal getEscenariPrincipal() {
        return escenariPrincipal;
    }

    public void setEscenariPrincipal(Principal escenariPrincipal) {
        this.escenariPrincipal = escenariPrincipal;
    }
    
     public void menuprincipal(){
        escenariPrincipal.menuPrincipal();
    }
}
