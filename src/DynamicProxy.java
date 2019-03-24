import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author Syniuk Valentyn
 */
public class DynamicProxy {

    private Object proxy;

    private DynamicProxy(Object value) {
        InvocationHandler handler = new Handler(value);
        proxy = Proxy.newProxyInstance(null, value.getClass().getInterfaces(), handler);
    }

    private Object getProxy() {
        return proxy;
    }

    public static void main(String[] args) {
        System.out.println("Result: " + (new DynamicProxy(30).getProxy()).equals(70));
    }

}