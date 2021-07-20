
package org.joseluna.db;

import java.sql.Connection;
import com.mysql.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {

    public static Object getInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  private Connection conexion;
  private static Conexion instancia; 
  
  public Conexion() {
      try{
          Class.forName("com.mysql.jdbc.Driver").newInstance();
          conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/BDTonysKINAL2016366?useSSL=false","root","admin");
      }catch(ClassNotFoundException e){
          e.printStackTrace();
      }catch(InstantiationException e){
           e.printStackTrace();
      }catch(IllegalAccessException e){
          e.printStackTrace();
      }catch(SQLException e){
            e.printStackTrace();      
      }
}


public static Conexion getIntance() {
    if(instancia == null){
        instancia = new Conexion();

}

return instancia;
}

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
 

}