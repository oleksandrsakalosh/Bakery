package people;

/**
 * Class for user logged as admin
 */
public class Admin implements Human{
    private final String login;

    /**
     * Creates new admin user
     * @param login name of the user
     */
    public Admin(String login){
        this.login = login;
    }

    /**
     * @return path to .fxml file for gui
     */
    public String getScene() {
        return "../view/AdminScene.fxml";
    }

    /**
     * @return name of the user
     */
    @Override
    public String getLogin() {
        return login;
    }
}
