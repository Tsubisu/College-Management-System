package view;

import controller.TableActionEvent;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;


public class TableActionCellEditor extends DefaultCellEditor {
    TableActionEvent tableActionEvent;
    public TableActionCellEditor (TableActionEvent tableActionEvent) {
        super (new JCheckBox ()) ;
        this.tableActionEvent=tableActionEvent;
    }

        @Override
        public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int il)
        {
            TablePanelAction action = new TablePanelAction ();
            action.addTableRowActionListener(tableActionEvent,jtable);
            action.setBackground(jtable.getSelectionBackground());
            return action;
        }
}