package College_Management_System;
import view.*;
import controller.*;

public class demo
{
    public static void main(String[] args)
    {

        logIn loginView = new logIn();
        LoginController loginController = new LoginController(loginView);
        loginController.openView();
    }
}