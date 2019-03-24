import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

class Reflection {

    Reflection() {
    }

    /* рефлексия по имени класса */
    public static String disassembleClass(String nameClass) {
        Class cls;
        try {
            cls = Class.forName(nameClass);
            nameClass = disassembleClass(cls);
        } catch (ClassNotFoundException ignored) {
        }
        return nameClass;
    }

    /* рефлексия по объекту типа Class */
    public static String disassembleClass(Class cls) {
        String str;
        if (cls.isPrimitive()) str = ifPrimitive(cls);
        else if (cls.isArray()) str = ifArray(cls);
        else if (cls.isInterface()) str = ifInterface(cls);
        else str = ifClass(cls);
        return str;
    }

    // если примитивный тип
    private static String ifPrimitive(Class cls) {
        return cls.getSimpleName();
    }

    private static String ifArray(Class cls) {
        return (cls.getSimpleName());
    }

    private static String ifInterface(Class cls) {
        return "\tModifiers - " + getModifiers(cls.getModifiers()) + "\n" +
                "\tPackage name - " + cls.getPackage().getName() + "\n" +
                "\tName - " + cls.getSimpleName() + "\n" +
                "\tSuperclass - " + cls.getSuperclass() + "\n\n" +
                "\tInterfaces: \n" + getInterfaces(cls.getInterfaces()) + "\n" +
                "\tFields: " + "\n" + getFields(cls.getFields()) + getFields(cls.getDeclaredFields()) +
                "\tMethods: " + "\n" + getMethods(cls.getMethods()) + getMethods(cls.getDeclaredMethods());
    }

    // если класс (интерфейс + конструкторы)
    private static String ifClass(Class cls) {
        return ifInterface(cls) +
                "\tConstructors:\n" + getConstructors(cls.getConstructors());
    }

    private static String getModifiers(int n) {
        StringBuilder builder = new StringBuilder();
        if (Modifier.isPublic(n)) builder.append("public ");
        if (Modifier.isProtected(n)) builder.append("protected ");
        if (Modifier.isPrivate(n)) builder.append("private ");
        if (Modifier.isStatic(n)) builder.append("static ");
        if (Modifier.isFinal(n)) builder.append("final ");
        if (Modifier.isAbstract(n)) builder.append("abstract ");
        if (Modifier.isInterface(n)) builder.append("interface ");
        if (Modifier.isNative(n)) builder.append("native ");
        if (Modifier.isSynchronized(n)) builder.append("synchronized ");
        if (Modifier.isStrict(n)) builder.append("strict ");
        if (Modifier.isTransient(n)) builder.append("transient ");
        if (Modifier.isVolatile(n)) builder.append("volatile ");
        return builder.toString();
    }

    private static String getFields(Field[] fields) {
        StringBuilder builder = new StringBuilder();
        for (Field field : fields) {
            builder.append(field.toString());
            builder.append("\n");
        }
        return builder.toString();
    }

    private static String getMethods(Method[] methods) {
        StringBuilder builder = new StringBuilder();
        for (Method method : methods) {
            builder.append(method);
            builder.append("\n");
        }
        return builder.toString();
    }

    private static String getConstructors(Constructor[] constructors) {
        StringBuilder builder = new StringBuilder();
        for (Constructor constructor : constructors) {
            builder.append(constructor);
            builder.append("\n");
        }
        return builder.toString();
    }

    private static String getInterfaces(Class[] interfaces) {
        StringBuilder builder = new StringBuilder();
        for (Class anInterface : interfaces) {
            builder.append(anInterface);
            builder.append("\n");
        }
        return builder.toString();
    }

    /* рефлексия по объекту + вызов метода */
    public static void disassembleClass(DemoClass object) throws IllegalAccessException, FunctionNotFoundException {

        boolean flag = false;
        Scanner in = new Scanner(System.in);

        Class cls = object.getClass();
        Method[] methods = cls.getMethods();

        System.out.println(disassembleObject(object));

        System.out.print("Enter name method: ");
        String nameMethod = in.next();
        for (Method method : methods) {
            if (method.getName().equals(nameMethod)) flag = true;

            if (method.getName().equals(nameMethod)) {
                try {
                    System.out.println("Result: " + method.invoke(object));
                    flag = true;
                } catch (Exception ignored) {
                }
            }
        }
        if (!flag) throw new FunctionNotFoundException("Method not found!");
    }

    // разбор объекта на составляющие
    private static String disassembleObject(DemoClass object) throws IllegalArgumentException,
            IllegalAccessException, SecurityException {

        Class cls = object.getClass();
        StringBuilder builder = new StringBuilder();

        builder.append("\tClass: ").append(getModifiers(cls.getModifiers())).append(cls.getName());

        builder.append("\n\tFields:" + "\n");
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            builder.append(field.toString()).append(" = ").append(field.get(object));
            builder.append("\n");
        }

        builder.append("\tMethods:" + "\n");
        Method[] methods = cls.getMethods();
        builder.append(getMethods(methods));
        return builder.toString();
    }

    // запуск метода по заданому имени класса, названию метода и списка передаваемых параметров
    public static void tryRunMethod(DemoClass object, String nameMethod, Object... parameters) throws SecurityException,
            IllegalArgumentException, FunctionNotFoundException {

        boolean flag = false;
        Class cls = object.getClass();
        Method[] methods = cls.getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().equals(nameMethod)) {
                try {
                    method.invoke(object, parameters);
                    flag = true;
                } catch (Exception ignored) {
                }
            }
        }
        if (!flag) throw new FunctionNotFoundException("Method not found!");
    }

}
