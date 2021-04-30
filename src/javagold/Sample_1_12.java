package javagold;

class Foo_1_12 {
    String str = "Hello";
}

public class Sample_1_12 {
    public static void main(String[] args) {
        Foo_1_12 f1 = new Foo_1_12();
        Foo_1_12 f2 = new Foo_1_12();
        System.out.println("f1: " + f1.hashCode());
        System.out.println("f2: " + f2.hashCode());
        Foo_1_12 f3 = new Foo_1_12();
        Foo_1_12 f4 = f3;
        System.out.println("f3: " + f3.hashCode());
        System.out.println("f4: " + f4.hashCode());
    }
}
