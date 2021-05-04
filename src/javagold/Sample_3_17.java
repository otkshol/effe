package javagold;

import java.util.ArrayList;
import java.util.List;

class X_3_17 {
    public String toString() {
        return "X";
    }
}

class Y_3_17 extends X_3_17 {
    public String toString() {
        return "Y";
    }
}

public class Sample_3_17 {

    // 引数で受け取るリストの要素はXクラスまたはそのサブクラス
    public static void method1(List<? extends X_3_17> list) {
        //list.add(new X_3_17()); ワイルドカード（？）を使用している場合、型が確定していないのでaddをするとコンパイルエラーになる
        //list.add(new Y_3_17());ワイルドカード（？）を使用している場合、型が確定していないのでaddをするとコンパイルエラーになる
        System.out.print(list.get(0) + " ");
    }

    // 引数で受け取るリストの要素はYクラスまたはそのスーパークラス
    public static void method2(List<? super Y_3_17> list) {
        //list.add(new X_3_17()); ワイルドカード（？）を使用している場合、型が確定していないのでaddをするとコンパイルエラーになる
        list.add(new Y_3_17()); // < ? super T>のみTが一致する型のオブジェクト追加が可能
        System.out.print(list.get(0) + " ");
    }

    public static void main(String[] args) {
        List<X_3_17> l1 = new ArrayList<>();
        l1.add(new X_3_17());
        List<Y_3_17> l2 = new ArrayList<>();
        l2.add(new Y_3_17());
        method1(l1);
        method1(l2);
        method2(l1);
        method2(l2);
        Integer i = 0;
    }
}
