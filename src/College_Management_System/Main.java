package College_Management_System;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import dao.Module;
import model.Chapter;
import view.*;
import controller.*;

import javax.swing.*;
import java.util.Optional;


public class Main
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