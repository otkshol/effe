package supplement.lambda;

import java.util.Comparator;
import java.util.Objects;

/**
 * 参考サイト
 * https://engineer-club.jp/java-lambda-expression
 */
public class ComparatorImpl implements Comparator<String> {
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
}
