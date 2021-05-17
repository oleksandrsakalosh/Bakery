package people;

/**
 * Class for user logged as baker
 */
public class Baker implements Human{
    private final String login;

    /**
     * Creates new baker user
     * @param login name of the user
     */
    public Baker(String login){
        this.login = login;
    }

    /**
     * @return path to .fxml file for gui
     */
    public String getScene(){
        return "../view/BakerScene.fxml";
    }

    /**
     * @return name of the user
     */
    @Override
    public String getLogin() {
        return login;
    }
}
