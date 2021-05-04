package javagold;

class Gen<T> {
    private T var1;
    //private static T var2; // 型パラメータはstaticメンバには使用できない

    public Gen(T var1) {
        this.var1 = var1;
    }

    public T getVar1() {
        return var1;
    }

    public void setVar1(T var1) {
        this.var1 = var1;
    }
}

public class Sample_3_13 {
    public static void main(String[] args) {
        Gen<String> g1 = new Gen<>("ABC");
        System.out.println(g1.getVar1());
        g1.setVar1("DEF");
        System.out.println(g1.getVar1());
        Gen<Integer> g2 = new Gen<>(1);
        System.out.println(g2.getVar1());
        g2.setVar1(2);
        System.out.println(g2.getVar1());
    }
}
