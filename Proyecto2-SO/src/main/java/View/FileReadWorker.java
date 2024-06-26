
package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Controller.Controller;

public class FileReadWorker extends SwingWorker<Void, String[]> {
    private final String filePath;
    private final DefaultTableModel tableModel;
    private final Controller controller; // Agregar una instancia de Controller
    private int selectedComputer; // Agregar una instancia de Controller

    public FileReadWorker(String filePath, DefaultTableModel tableModel, Controller controller, int pComputer) {
        this.filePath = filePath;
        this.tableModel = tableModel;
        this.controller = controller; // Asignar la instancia de Controller recibida como parámetro
        this.selectedComputer = pComputer;
    }
 //PRUEBAAAAAAAAAAAAAAAAAAAAA
    @Override
    protected Void doInBackground() throws Exception {
        try{
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;

            String pid = "";
            String size = "";
            String ptr = "";
            String[] res;

            while ((st = br.readLine()) != null && this.controller.isPaused() != false) {
                tableModel.setRowCount(0);
                ArrayList<String[]> memoryRows = this.controller.readInstructions(st, this.selectedComputer);        
                for (String[] rows : memoryRows) {
                    publish(rows);  //Enviar cada fila al proceso de actualización en el hilo de Swing
                }

                Thread.sleep(1000); // Simular una pausa de 1 segundo
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void process(List<String[]> chunks) {
        for (String[] rowData : chunks) {
            tableModel.addRow(rowData); // Agregar cada fila al modelo de la tabla
        }
    }

    // Agregar métodos para acceder a la instancia de Controller según sea necesario
}

