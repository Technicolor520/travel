package technicolor.travel.exception;

/**
 * 用户已存在异常
 */
public class UserExistsException extends Exception {
    public UserExistsException(){
    }
    public UserExistsException(String errorMsg){
        super(errorMsg);
    }
}
