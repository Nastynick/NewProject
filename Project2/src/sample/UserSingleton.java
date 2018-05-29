package sample;

public class UserSingleton {
        private static UserSingleton myUser;
        private String username;
        private boolean admin;

    private UserSingleton() {

    }
    public static UserSingleton getInstance () {
        if (myUser == null) {
            myUser = new UserSingleton();
        }
        return myUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
