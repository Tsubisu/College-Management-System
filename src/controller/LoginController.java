package controller;
import College_Management_System.DashboardSetUp;
import dao.LogInDAO;
import model.User;
import view.OtpVerification;
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
            public void actionPerformed(ActionEvent a) {
                String email=loginView.getEmailField().getText();
                String password=loginView.getPasswordField().getText();

                boolean emailFlag = loginDao.userCheck(email);
                boolean passwordFlag= false;
                try
                    {
                        if(emailFlag)
                            passwordFlag= loginDao.loginCheck(email,password);
                        else
                            JOptionPane.showMessageDialog(loginView,"No user with such E-mail Found");
                    }
                catch (Exception e)
                {
                    System.out.println(e);
                }
                if (emailFlag && !passwordFlag)
                {
                    JOptionPane.showMessageDialog(loginView,"Incorrect Password");
                }
                if(emailFlag && passwordFlag)
                {
                    JOptionPane.showMessageDialog(loginView,"LogIn Successful");
                    loginView.dispose();
                }


            }
        });

        this.loginView.addForgetPasswordButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginView.setVisible(false);
                OtpVerification otpView = new OtpVerification();
                OtpController otpController = new OtpController(otpView);
                otpView.setVisible(true);
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
