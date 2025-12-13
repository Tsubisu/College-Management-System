package controller;

import view.Dashboard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class DashboardController {
    protected Dashboard dashboard;

    protected void buttonListener(){
        dashboard.addMenuButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton) e.getSource();
                System.out.println(btn.getText() + " clicked");
                dashboard.getDashPanel().getContentLayout().show(dashboard.getDashPanel().getContentPanel(),btn.getText());
            }
        });
    }
}