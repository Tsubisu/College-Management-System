package controller;

import view.OtpVerification;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OtpController {
    private final OtpVerification otpView;

    public OtpController(OtpVerification otpView)
    {
        this.otpView= otpView;
        this.otpView.addVerifyButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 System.out.println(otpView.getOtp());
            }
        });
    }

}
