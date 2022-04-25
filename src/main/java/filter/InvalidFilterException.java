package filter;

/**
 * @author R.Q.
 * description:过滤器字符串格式异常类
 */
public class InvalidFilterException extends Exception {
    InvalidFilterException(String message) {
        super(message);
    }
}
