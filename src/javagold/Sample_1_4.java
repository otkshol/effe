package javagold;

class Foo_1_4 {
    final int num1 = 10;
    final int num2;

    Foo_1_4(int i) {
        num2 = i;
    }
}

public class Sample_1_4 {
    public static void main(String[] args) {
        final Foo_1_4 obj1 = new Foo_1_4(100);
        //obj1.num1 = 20; // final修飾子を付けた定数への代入はコンパイルエラーになる
        //obj1 = new FOO(300); // final修飾子を付けた定数への代入はコンパイルエラーになる
        System.out.println("obj1.num1 : " + obj1.num1);
        System.out.println("obj1.num2 : " + obj1.num2);
    }
}
