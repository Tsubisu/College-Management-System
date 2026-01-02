package College_Management_System;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.ui.FlatCheckBoxMenuItemUI;
import view.*;
import controller.*;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.util.ArrayList;


public class demo
{
    public static void main(String[] args)
    {
        try {
            javax.swing.UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        UIManager.put( "Component.arc", 20 );
        UIManager.put( "Button.arc", 20 );
        logIn loginView = new logIn();
        LoginController loginController = new LoginController(loginView);
        loginController.openView();
    }
}