package javagold;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class Sample_4_6 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(3,1,2);
        // 匿名クラスを使用した場合
        Consumer<List<Integer>> con1 = new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> list) {
                Collections.sort(list);
            }
        };
        // ラムダ式
        Consumer<List<Integer>> con2 = lambdaList -> Collections.sort(lambdaList);

        // staticメソッド参照
        Consumer<List<Integer>> con3 = Collections::sort;
        con3.accept(list);

        System.out.println(con1);
        System.out.println(con2);
        System.out.println(con3);
        System.out.println(list);
    }
}
