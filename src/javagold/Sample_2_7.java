package javagold;

abstract class X {
    static void methodA() {
        System.out.println("methodA()");
    }

    void methodB() {
        System.out.println("methodB()");
    }
}

class Y extends X {
}

public class Sample_2_7 {
    public static void main(String[] args) {
        X.methodA();
        // X obj1 = new X();
        // obj1.methodB();
        Y obj2 = new Y();
        obj2.methodB();
    }
}
