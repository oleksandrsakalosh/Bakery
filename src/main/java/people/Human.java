package people;

/**
 * Interface for all users
 */
public interface Human {

    /**
     * @return path to .fxml file for gui
     */
    String getScene();

    /**
     * @return name of the user
     */
    String getLogin();
}
