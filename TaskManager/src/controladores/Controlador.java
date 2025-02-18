package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelos.Modelo;
import vistas.Vista;


public class Controlador implements ActionListener {
    
    private Vista vista;
    private Modelo modelo;

    public Controlador(Vista vista, Modelo modelo) {
        
        this.vista = vista;
        this.modelo = modelo;
        
        this.vista.setTitle("Task Manager");
        
        this.vista.update_button.addActionListener(this);
        this.vista.nuevo_button.addActionListener(this);
        this.vista.supr_button.addActionListener(this);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == vista.update_button) {
            
            List<List<String>> tasksListas = modelo.ListarProcesos();
            
            DefaultTableModel model = new DefaultTableModel();
            
            for (int j=0; j<3; j++) {
                model.addColumn(tasksListas.get(0).get(j));
            }
            
            for (int i=1; i < tasksListas.size(); i++) {
                model.addRow(new Object[]{tasksListas.get(i).get(0), tasksListas.get(i).get(1), tasksListas.get(i).get(2)});
            }
            
            vista.tabla_procesos.setModel(model);
            vista.numero_procesos.setText(Integer.toString(tasksListas.size()) + " tasks");
            
        } else if (e.getSource() == vista.nuevo_button) {
            modelo.LanzarProcesos();
            vista.update_button.doClick();
            
        } else if (e.getSource() == vista.supr_button) {
            List<List<String>> tablaProcesos = modelo.ListarProcesos();

            String pid = JOptionPane.showInputDialog(null, "Detener proceso con PID:");
            if (pid != null) {
                modelo.MatarProcesos(pid);
                vista.update_button.doClick();
            } else {
                JOptionPane.showMessageDialog(null, "PID no valido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
