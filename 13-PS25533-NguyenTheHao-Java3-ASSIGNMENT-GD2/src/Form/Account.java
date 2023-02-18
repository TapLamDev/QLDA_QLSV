package Form;

/**
 *
 * @author haops25533
 */
public class Account {
    private String username;
    private String password;
    private String role;
    private String idUser;

    public Account() {
    }

    public Account(String username, String password, String role, String idUser) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    
}
