package controller;

import model.User;
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
    public void profilePageSetter(User user)
    {
        System.out.print(user.getEmail());
        dashboard.getDashPanel().getProfilePanel().setFirstName(user.getFirstName());
        dashboard.getDashPanel().getProfilePanel().setLastName(user.getLastName());
        dashboard.getDashPanel().getProfilePanel().setEmail(user.getEmail());
        dashboard.getDashPanel().getProfilePanel().setGender(user.getContact());
        dashboard.getDashPanel().getProfilePanel().setAddress(user.getAddress());
        dashboard.getDashPanel().getProfilePanel().setContact(user.getContact());


    }
}