package supplement.lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Streamを使用する練習をしていく、
 *
 * 参考記事
 * https://qiita.com/sano1202/items/64593e8e981e8d6439d3#void-foreachconsumer
 * https://www.unitrust.co.jp/8367
 *
 */

public class Stream {

    public static void main(String[] args) {
        // forEach(consumer<T>) 終端操作
        int[] numbers = {-2, 4, -6, 68, 23,-9, 22};
        List<Integer> numbersList = new ArrayList<>();
        for (int n : numbers) {
            numbersList.add(n);
        }
        numbersList.stream().forEach((i) ->
            System.out.println(i + " ")
        );

        System.out.println("----------------------------");

        // filter(Predicate<T>) 中間操作
        numbersList.stream().filter((i) -> { return i > 0; })
                            .forEach((i) -> { System.out.println("filter " + i); });

        System.out.println("----------------------------");

        // map(Function<T,R>)  中間操作
        numbersList.stream().filter((i) -> { return i > 0;})
                            .map((i) -> {return "*" + i + "*";})
                            .forEach((s) -> {System.out.println(s + "");});

        System.out.println("----------------------------");

        // sorted(Comparison<T>) 中間操作
        numbersList.stream().filter((i) -> { return i >= 0;})
                            .sorted((i1, i2) -> {return i1- i2;})
                            .map((i) -> { return "**" + i + "**";})
                            .forEach((s) -> {System.out.println(s + "");});

        System.out.println("----------------------------");

        



    }
}
