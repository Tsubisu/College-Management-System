package controller;

import model.User;
import view.Dashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class DashboardController {
    protected Dashboard dashboard;

    protected void buttonListener(){
        dashboard.addMenuButtonListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                if (!btn.getText().equalsIgnoreCase("logout")) {
                    dashboard.getDashPanel().getContentLayout().show(dashboard.getDashPanel().getContentPanel(), btn.getText());
                }
                else
                {
                    JLabel message= new JLabel("Do you want to logout?");
                    int result = JOptionPane.showConfirmDialog(dashboard,message,"Logout",JOptionPane.YES_NO_OPTION);
                    if(result==JOptionPane.YES_OPTION)
                    {
                    dashboard.dispose();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setOpaque(true);


                btn.setBackground(new Color(3, 106, 201,255));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setOpaque(false);
                Point mousePos = e.getPoint();
                if (btn.contains(mousePos)) {
                    mouseEntered(new MouseEvent(   btn,
                            MouseEvent.MOUSE_ENTERED,
                            System.currentTimeMillis(),
                            0,
                            e.getX(),
                            e.getY(),
                            0,
                            false));
                }


            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setOpaque(true);
                btn.setBackground(new Color(148, 189, 250,255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setOpaque(false);

            }
        });
    }
    public void profilePageSetter(User user)
    {
        dashboard.getDashPanel().getProfilePanel().setFirstName(user.getFirstName());
        dashboard.getDashPanel().getProfilePanel().setLastName(user.getLastName());
        dashboard.getDashPanel().getProfilePanel().setEmail(user.getEmail());
        dashboard.getDashPanel().getProfilePanel().setGender(user.getGender());
        dashboard.getDashPanel().getProfilePanel().setAddress(user.getAddress());
        dashboard.getDashPanel().getProfilePanel().setContact(user.getContact());

    }
}