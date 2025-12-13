package controller;
import College_Management_System.DashboardSetUp;
import dao.LogInDAO;
import model.User;
import view.logIn;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private final LogInDAO loginDao = new LogInDAO();
    private final logIn loginView;
    private final User user = new User();




    public LoginController(logIn login)
    {
        this.loginView=login;
        this.loginView.addLoginButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email=loginView.getEmailField().getText();
                String password=loginView.getPasswordField().getText();
                try {
                    boolean check = loginDao.loginCheck(email,password);

                    if(check) {
                        JOptionPane.showMessageDialog(loginView, "Successful");
                        user.setEmail(email);
                        user.setPassword(password);
                        loginDao.userSetup(user);
                        new DashboardSetUp().openDashboard(user);
                        closeView();
                    }
                    else {
                        JOptionPane.showMessageDialog(loginView, "No such user found");
                    }
                }
                catch(Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    public void openView()
    {
        this.loginView.setVisible(true);
    }

    public void closeView()
    {
        loginView.dispose();
    }




}
