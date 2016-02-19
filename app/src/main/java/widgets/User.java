package widgets;

/**
 * Created by Faizzy on 12-01-2016.
 */
public class User {

    public String name;
    public String email;
    public String password;

    public User(String name, String email, String password) {
        this.email=email;
        this.name =name;
        this.password = password;

    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
