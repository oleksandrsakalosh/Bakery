package people;

/**
 * Class for user logged as seller
 */
public class Seller implements Human{
    private final String login;

    /**
     * Creates new seller user
     * @param login name of the user
     */
    public Seller(String login){
        this.login = login;
    }

    /**
     * @return path to .fxml file for gui
     */
    public String getScene(){
        return "../view/SellerScene.fxml";
    }

    /**
     * @return name of the user
     */
    @Override
    public String getLogin() {
        return login;
    }
}
