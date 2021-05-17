package system;

/**
 * Exception for cases when user entered wrong login or password
 */
public class LogInException extends Exception{
    private final String text;

    //default text of exception
    public LogInException(){
        this.text = "You entered wrong login or password";
    }

    /**
     * @return text of exception
     */
    public String getText(){
        return this.text;
    }
}
