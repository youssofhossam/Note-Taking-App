public class SecureNote extends Note {
    private String password;

    public SecureNote(){}
    public SecureNote(String title, String password) {
        super(title);
        this.password = password;
    }

    public boolean checkPassword(String inputPassword) {return this.password.equals(inputPassword);}

    public void setPassword(String password) {this.password = password;}

    public String getPassword() {return password;}
}
