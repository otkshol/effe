package javagold;

class Foo_1_13 {
    private int num;
    public boolean equals(Object o) {
        if ((o instanceof Foo_1_13) && (((Foo_1_13)o).num == this.num)) {
            return true;
        } else{
            return false;
        }
    }

    public int hashCode() {
        return num * 5;
    }
}

public class Sample_1_13 {
    public static void main(String[] args) {
        Foo_1_13 f1 = new Foo_1_13();
        Foo_1_13 f2 = new Foo_1_13();
        System.out.println("f1.equals(f2): " + f1.equals(f2));
    }
}
