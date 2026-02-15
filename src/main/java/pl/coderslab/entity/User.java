package pl.coderslab.entity;

public class User {
    private int id = 0;
    private String userName = "";
    private String email = "";
    private String password = "";

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    protected void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    public User(String userName, String email, String password) {
        setUserName(userName);
        setEmail(email);
        setPassword(password);
    }

    protected User(int id, String userName, String email, String password){
        setId(id);
        setUserName(userName);
        setEmail(email);
        setPassword(password);
    }

    public User(String[] parameters) {
        setId(Integer.parseInt(parameters[0]));
        setUserName(parameters[1]);
        setEmail(parameters[2]);
        setPassword(parameters[3]);
    }

    public void printInfo() {
        System.out.println("ID: " + getId());
        System.out.println("Username: " + getUserName());
        System.out.println("E-mail: " + getEmail());
        System.out.println("Password: " + getPassword());
    }

    public void changeData(String userName, String email, String password){
        this.setUserName(userName);
        this.setEmail(email);
        this.setPassword(password);
    }
}
