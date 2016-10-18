package akiyama.library.exception;

/**
 * Created by Administrator on 2016/10/17.
 */
public class NetworkException extends Exception{

    public NetworkException(){
        super("网络错误");
    }

    public NetworkException(String msg){
        super(msg);
    }

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetworkException(Throwable cause) {
        super(cause);
    }


}
