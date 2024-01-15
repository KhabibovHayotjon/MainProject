package alif.com.mainproject.exception;

public class AppLoginException extends RuntimeException {
    public AppLoginException(String password_or_userName_is_incorrect) {
        super(password_or_userName_is_incorrect);
    }
}
