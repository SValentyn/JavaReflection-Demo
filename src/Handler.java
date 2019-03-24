import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Handler implements InvocationHandler {

    private Object hidden;

    public Handler(Object hidden) {
        this.hidden = hidden;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long time = System.nanoTime();

        System.out.print(hidden + ".".concat(method.getName()).concat("("));
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i]);
            if (i != args.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(")".concat(System.lineSeparator()));

        Object object = method.invoke(hidden, args);
        System.out.println("Time: ".concat(String.valueOf(System.nanoTime() - time).
                concat(" ns").concat(System.lineSeparator())));
        return object;
    }
}
