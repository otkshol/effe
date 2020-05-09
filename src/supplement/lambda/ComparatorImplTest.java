package supplement.lambda;

import java.util.Comparator;

/**
 * 参考サイト
 * https://engineer-club.jp/java-lambda-expression
 */
public class ComparatorImplTest {
    public static void main(String[] args) {
        // ラムダ式を用いない書き方
        Comparator<String> unUseLambda = new ComparatorImpl();
        System.out.println(unUseLambda.compare("ABC", "DEF"));

        // ラムダ式を用いた書き方
        Comparator<String> useLambda = (s1, s2) -> s1.compareTo(s2);
        System.out.println(useLambda.compare("ABC", "DEF"));
    }
}
