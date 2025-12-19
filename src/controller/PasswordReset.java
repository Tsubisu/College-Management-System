package controller;

import College_Management_System.EmailService;
import dao.LogInDAO;
import view.EmailVerify;
import view.OtpVerification;
import view.ResetPassword;
import view.logIn;
import java.security.SecureRandom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class PasswordReset {
    private final OtpVerification otpView;
    private final EmailVerify emailVerify;
    private final ResetPassword resetPassword;
    private final int otp=generateOtp();
    LogInDAO logInDao = new LogInDAO();

    public PasswordReset(logIn loginView,EmailVerify emailVerify,OtpVerification otpView, ResetPassword resetPassword)
    {
        this.emailVerify=emailVerify;
        this.otpView= otpView;
        this.resetPassword=resetPassword;

        emailVerify.addSubmitButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailVerify.getEmail().getText();
                boolean check= logInDao.userCheck(email);
                if(check)
                {
                    emailVerify.setVisible(false);
                    EmailService.sendEmail(email,"Email Verification","Your Verification Code is \n"+ "  "+otp);
                    otpVerifyController(email);
                }
                else
                {
                  JOptionPane.showMessageDialog(emailVerify,"No user with such E-mail found");
                }
            }
        });
        emailVerify.addReturnButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emailVerify.dispose();
                otpView.dispose();;
                resetPassword.dispose();
                loginView.setVisible(true);
            }
        });
    }


    private int generateOtp()
    {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextInt(900000)+100000;
    }

    private void otpVerifyController(String email)
    {

        otpView.addVerifyButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int enteredOtp = otpView.getOtp();

                System.out.println(enteredOtp);
                if(enteredOtp==otp)
                {
                    System.out.println("Entered otp is correct");

                }
                else
                {
                    System.out.println("Entered otp is not correct");
                }

            }

        });
        otpView.setVisible(true);
    }

}
