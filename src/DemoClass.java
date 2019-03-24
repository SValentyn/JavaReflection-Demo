public class DemoClass {

    private double a;
    private double b;

    public DemoClass(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double findHypotenuse() {
        return Math.hypot(a, b);
    }

    public void runMe(String string) {
        System.out.println(string + "launch!");
    }

}
