package javagold;

class Gen_3_14 {
    private String var1 = "aaa";

    public <T> T method(T value) {
        return value;
    }

    public String getVar1() {
        return var1;
    }
}

public class Sample_3_14 {
    public static void main(String[] args) {
        Gen_3_14 g = new Gen_3_14();
        Integer i = g.method(1);
        System.out.println(i);
        String s1 = g.method("ABC");
        String s2 = g.<String>method("abc");
        System.out.println(s1 + " " + s2);
    }
}
