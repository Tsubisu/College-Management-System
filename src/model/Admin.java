package model;
public class Admin extends User{
    private int adminId;

    public Admin(int userId, String firstName, String lastName, String email,
                 String password, String address, String contact, String gender)
    {
        super(userId, firstName, lastName, email, password, address, contact, gender, "Admin");
    }
    public Admin(int userId,int adminId, String firstName, String lastName, String email,
                 String password, String address, String contact, String gender)
    {
        super(userId, firstName, lastName, email, password, address, contact, gender, "Admin");
        this.adminId=adminId;
    }

    public Admin(){}

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}
