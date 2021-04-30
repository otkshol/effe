package javagold;

class Foo {
}

class Bar_1_11 {
}

public class Sample_1_11 {
    public static void main(String[] args) {
        Foo f1 = new Foo();
        Foo f2 = new Foo();
        System.out.println("f1.equals(f2) : " + f1.equals(f2));

        Foo f3 = new Foo();
        Foo f4 = f3;
        System.out.println("f1.equals(f4) : " + f3.equals(f4));
        Bar_1_11 b1 = new Bar_1_11();
        System.out.println("f1.equals(b1) : " + f3.equals(b1));
        System.out.println("f1.equals(null) : " + f3.equals(null));
    }
}
