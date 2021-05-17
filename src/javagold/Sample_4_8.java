package javagold;

import java.util.Arrays;
import java.util.List;

public class Sample_4_8 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(3,1,2);
        // インスタンスメソッド参照
        list.forEach(System.out::print);
    }
}
