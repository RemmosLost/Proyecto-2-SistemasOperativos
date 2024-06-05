package View;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TableUpdateWorker extends SwingWorker<Void, Object[]> {
    private DefaultTableModel tableModel;
    private JTable table;
    private Object[] data;

    public TableUpdateWorker(DefaultTableModel tableModel, JTable table, Object[] data) {
        tableModel.setRowCount(0);
        this.tableModel = tableModel;
        this.table = table;
        this.data = data;
    }
    
    /*public TableUpdateWorker(DefaultTableModel tableModel, JTable table, ArrayList<String[]> data) {
        tableModel.setRowCount(0);
        this.tableModel = tableModel;
        this.table = table;
        this.data = data;
    }*/
    
    
    //PRUEBAAAAAAAAAAAAAAAAAAAAA
    @Override
    protected Void doInBackground() throws Exception {
        tableModel.setRowCount(0);
        // Simulación de datos para actualizar la tabla
        for (Object o: data) {
            Object[] rowData = this.data; // Datos de ejemplo
            publish(data); // Enviar datos para actualizar la tabla en el hilo de Swing
            Thread.sleep(1000); // Simular pausa de 1 segundo
        }
        return null;
    }

    @Override
    protected void process(List<Object[]> chunks) {
        for (Object[] rowData : chunks) {
            tableModel.addRow(rowData); // Agregar cada fila al modelo de la tabla
        }
    }
}
