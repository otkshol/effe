package javagold;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Sample_4_11 {
    public static void main(String[] args) {
        Supplier<Foo_4_11> obj1 = Foo_4_11::new;
        System.out.println(obj1.get().a);
        Function<Integer,Foo_4_11> obj2 = Foo_4_11::new;
        System.out.println(obj2.apply(10).a);
        Supplier<List<Foo_4_11>> obj4 = ArrayList<Foo_4_11>::new;
        System.out.println(obj4.get().size());
    }
}

class Foo_4_11 {
    int a = 0;
    Foo_4_11() {
    }
    Foo_4_11(int a) {
        this.a = a;
    }
}

