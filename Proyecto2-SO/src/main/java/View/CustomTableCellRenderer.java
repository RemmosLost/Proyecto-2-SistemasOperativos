package View;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    private int pidColumnIndex;
    private Map<Object, Color> pidColorMap;

    public CustomTableCellRenderer(int pidColumnIndex) {
        this.pidColumnIndex = pidColumnIndex;
        this.pidColorMap = new HashMap<>();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Object pidValue = table.getValueAt(row, pidColumnIndex);
        Color rowColor = pidColorMap.computeIfAbsent(pidValue, k -> generateRandomColor()); // Obtener o generar un color para el valor de PID

        rendererComponent.setBackground(rowColor);

        return rendererComponent;
    }

    private Color generateRandomColor() {
        return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
    }
}
