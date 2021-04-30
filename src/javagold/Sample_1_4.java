package javagold;

class Foo {
    final int num1 = 10;
    final int num2;
    Foo(int i) {num2 = i;}
}

public class Sample_1_4 {
    public static void main(String[] args) {
        final Foo obj1 = new Foo(100);
        //obj1.num1 = 20; // final修飾子を付けた定数への代入はコンパイルエラーになる
        //obj1 = new FOO(300); // final修飾子を付けた定数への代入はコンパイルエラーになる
        System.out.println("obj1.num1 : " + obj1.num1);
        System.out.println("obj1.num2 : " + obj1.num2);
    }
}
