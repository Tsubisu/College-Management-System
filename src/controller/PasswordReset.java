package controller;

import College_Management_System.EmailService;
import dao.LogInDAO;
import dao.PasswordUpdate;
import jakarta.mail.MessagingException;
import view.EmailVerify;
import view.OtpVerification;
import view.ResetPassword;
import view.logIn;

import javax.swing.*;
import java.security.SecureRandom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordReset {
    private final OtpVerification otpView;
    private final ResetPassword resetPassword;
    private final int otp=generateOtp();
    LogInDAO logInDao = new LogInDAO();
    private String currentPassword;
    private String userEmail;
    private final EmailVerify emailVerify;
    private final logIn loginView;
    public PasswordReset(logIn loginView,EmailVerify emailVerify,OtpVerification otpView, ResetPassword resetPassword)
    {
        this.otpView= otpView;
        this.resetPassword=resetPassword;
        this.emailVerify= emailVerify;
        this.loginView= loginView;


        emailVerify.addSubmitButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailVerify.getEmail().getText();
                boolean check= logInDao.userCheck(email);
                if(check)
                {
                    userEmail=email;
                    currentPassword=logInDao.getCurrentPassword(email);
                    emailVerify.setVisible(false);
                    EmailService.sendEmail(email,"Email Verification","Your Verification Code is \n"+ "  "+otp);
                    otpVerifyController(email);
                }
                else
                {
                    JOptionPane.showMessageDialog(emailVerify,"No user with such E-mail found ");
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

                    PasswordResetController();
                    otpView.setVisible(false);


                }
                else
                {
                    JOptionPane.showMessageDialog(otpView,"You have entered incorrect OTP");
                }

            }

        });
        otpView.setVisible(true);
    }
    private void PasswordResetController()
    {
        resetPassword.setVisible(true);
        resetPassword.addConfirmButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = resetPassword.getNewPassword().getText();
                String rePassword= resetPassword.getReNewPassword().getText();
                if(password.equals(rePassword) && !password.equals(currentPassword))
                {
                    System.out.println("Reset Password successful");
                    PasswordUpdate passwordUpdate = new PasswordUpdate();
                    passwordUpdate.updatePassword(userEmail,password);
                    close();
                    loginView.setVisible(true);
 
                } else if (!password.equals(rePassword)) {
                    System.out.println("Entered password do not match");
                    
                }
                else
                {
                    System.out.println("Entered password can not be same as current password");
                }


            }
        });
    }
private void close()
    {
        emailVerify.dispose();
        otpView.dispose();
        resetPassword.dispose();
    }
}