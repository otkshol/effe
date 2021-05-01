package javagold;

interface Foo_2_9 {
    static void method() {
        System.out.println("Foo : method()");
    }
}

class Sample_2_9 {
    public static void main(String[] args) {
        Foo_2_9.method(); // interfaceの具象メソッドを呼び出している
        //Foo_2_9 obj = new Foo_2_9(); interfaceはインスタンス化できない
        //obj.method();
    }
}
