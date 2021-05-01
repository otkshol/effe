package javagold;

interface MyInter {
    void methodA();
}

class Outer {
    void method() {
        new MyInter() { // ここで無名の実装クラスがインスタンス化される
            public void methodA() {
                System.out.println("methodA");
            }
        }.methodA(); //匿名クラスのメソッド呼び出し
    }
}

public class Sample_2_23 {
    public static void main(String[] args) {
        new Outer().method();
    }
}
