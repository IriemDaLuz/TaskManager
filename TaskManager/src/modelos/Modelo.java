package modelos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class Modelo {

    public void LanzarProcesos() {
        String proceso = JOptionPane.showInputDialog(null, "Nombre del proceso:");
        
        ProcessBuilder pb = new ProcessBuilder(proceso);

        try {
            pb.start(); 
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al lanzar el proceso");
            e.printStackTrace();
        }
    }
    
    public int MatarProcesos(String pid) {
        ProcessBuilder pb = new ProcessBuilder("kill", "-9", pid);

        try {
            Process p = pb.start();
            int codRet = p.waitFor();
            if (codRet != 0) {
                System.out.println("Error al eliminar el proceso ");
            } else {
                System.out.println("Proceso eliminado.");
            }
            return codRet;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public List<List<String>> ListarProcesos() {
        List<List<String>> tablaProcesos = new ArrayList<>();
        ProcessBuilder pb = new ProcessBuilder("ps", "-eo", "pid,euser,comm");
        
        try {
            Process p = pb.start();
            BufferedReader lector = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String linea;
            while ((linea = lector.readLine()) != null) {          
                List<String> filaProceso = new ArrayList<>();
                filaProceso.add(linea.substring(0, 7).trim()); 
                filaProceso.add(linea.substring(8, 15).trim());  
                filaProceso.add(linea.substring(16).trim()); 
                tablaProcesos.add(filaProceso);
            }
            int codRet = p.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException exc) {
            exc.printStackTrace();
        }
        return tablaProcesos;
    }
}
