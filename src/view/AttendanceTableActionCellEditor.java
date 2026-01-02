package view;

import controller.TableActionEvent;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import controller.AttendanceActionEvent;


public class AttendanceTableActionCellEditor extends DefaultCellEditor {
   AttendanceActionEvent attendanceActionEvent;
    public AttendanceTableActionCellEditor (AttendanceActionEvent attendanceTableActionEvent) {
        super (new JCheckBox ()) ;
        this.attendanceActionEvent=attendanceTableActionEvent;
    }

        @Override
        public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int il)
        {
            AttendanceTablePanelAction action = new AttendanceTablePanelAction();
            action.addTableRowActionListener(attendanceActionEvent,jtable);
            action.setBackground(jtable.getSelectionBackground());
            return action;
        }
}