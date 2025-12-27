package model;
public class Admin extends User{

    public Admin(int userId, String firstName, String lastName, String email,
                 String password, String address, String contact, String gender)
    {
        super(userId, firstName, lastName, email, password, address, contact, gender, "Admin");
    }

}
