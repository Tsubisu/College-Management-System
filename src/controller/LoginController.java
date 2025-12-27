package controller;
import College_Management_System.DashboardSetUp;
import dao.LogInDAO;
import dao.UserData;
import model.User;
import view.EmailVerify;
import view.OtpVerification;
import view.ResetPassword;
import view.logIn;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private final LogInDAO loginDao = new LogInDAO();
    private final logIn loginView;
    private User user;




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
                    user=new User(loginDao.getUserid(email),email,password, loginDao.getUserRole(email));

                    DashboardSetUp dashboardSetUp = new DashboardSetUp();
                    dashboardSetUp.openDashboard(user);
                    loginView.dispose();
                }


            }
        });

        this.loginView.addForgetPasswordButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginView.setVisible(false);
                EmailVerify emailVerify = new EmailVerify();
                OtpVerification otpView = new OtpVerification();
                ResetPassword resetPassword= new ResetPassword();

                PasswordReset passwordReset = new PasswordReset(loginView,emailVerify,otpView,resetPassword);
                emailVerify.setVisible(true);
            }
        });
    }

    public void openView() {
        this.loginView.setVisible(true);
    }
}
