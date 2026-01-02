package view;
import java.awt.*;
import javax.swing. JTable;
import javax.swing.table. DefaultTableCellRenderer;

public class TableActionCellRender extends DefaultTableCellRenderer {

@Override
    public Component getTableCellRendererComponent (JTable jtable, Object o, boolean isSelected, boolean blnl, int row, int il) {
    Component com = super.getTableCellRendererComponent (jtable, o, isSelected, blnl, row, il);
    
    TablePanelAction action = new TablePanelAction ();
    action.setBackground(com.getBackground());

    if (isSelected == false && row % 2 == 0) {
        action.setBackground(Color.WHITE);
    } else {

    }
        action.setBackground(com.getBackground());
    return action;
    }
}