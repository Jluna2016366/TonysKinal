package org.joseluna.report;

import java.io.InputStream;
import java.util.Map;
import  net.sf.jasperreports.engine.util.JRLoader;
import  net.sf.jasperreports.engine.JasperReport;
import  net.sf.jasperreports.engine.JasperPrint;
import  net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;
import org.joseluna.db.Conexion;

public class GenerarReporte {
    
    public  static  void  mostrarReporte(String nombreReporte,String titulo,Map parametros){
     InputStream reporte  = GenerarReporte.class.getResourceAsStream(nombreReporte);
     try{
         JasperReport reporteMaestro = (JasperReport)JRLoader.loadObject(reporte);
         JasperPrint repoteImpreso = JasperFillManager.fillReport(reporteMaestro,parametros,Conexion.getIntance().getConexion());
         JasperViewer  visor = new JasperViewer(repoteImpreso,false);
         visor.setTitle(titulo);
         visor.setVisible(true);
      }catch(Exception e ){
         e.printStackTrace();
     }  
    }
 }
