package model;
public class User {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;
    protected String address;
    protected String gender;
    protected String contact;
    protected String role;
    protected int userId;


    public User(int userId, String email,String password,String role)
    {
        this.userId=userId;
        this.email=email;
        this.password=password;
        this.role=role;
    }

    public User(int userId, String firstName,String lastName, String email, String password, String address, String contact, String gender, String role)
    {
     this.userId=userId;
     this.firstName= firstName;
     this.lastName= lastName;
     this.email= email;
     this.password=password;
     this.address=address;
     this.contact=contact;
     this.gender=gender;
     this.role=role;

    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setGender(String gender){
        this.gender= gender;
    }

    public String getGender()
    {
        return gender;
    }

    public void setRole(String role)
    {
        this.role= role;
    }

    public String getRole()
    {
        return role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
