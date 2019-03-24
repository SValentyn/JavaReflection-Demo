/**
 * @author Syniuk Valentyn
 */
public class ReflectionMain {

    public static void main(String[] args) throws SecurityException, IllegalArgumentException,
            IllegalAccessException, FunctionNotFoundException {

        System.out.println(Reflection.disassembleClass("java.lang.String"));

        DemoClass demoClass = new DemoClass(3.0, 4.0);
        Reflection.disassembleClass(demoClass);

        Reflection.tryRunMethod(demoClass, "runMe", "successful ");

    }
}
