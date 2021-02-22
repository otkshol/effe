package effe.equals;

public class PhoneNumber {
    // 電話番号は000-000-0000の形式であり、byteだと桁が足りないかつshortだと足りのでshort型で宣言されている
    private final short areaCode, prefix, lineNum;

    public PhoneNumber(int areCode, int prefix, int lineNum){
        this.areaCode = rangeCheck(areCode, 999, "area code");
        this.prefix = rangeCheck(prefix, 999, "prefix");
        this.lineNum = rangeCheck(lineNum, 9999, "lineNum");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }

    @Override public boolean equals(Object o) {
        // 物理的等価をチェック(反射律に相当)
        if (o == this)
            return true;
        // PhoneNumber型であるかチェック（nullチェックもここに含まれている）
        if (!(o instanceof PhoneNumber))
            return false;
        // PhoneNumber型へキャスト
        PhoneNumber pn = (PhoneNumber) o;
        // 一番可変であるlineNumから検証している
        return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
    }
}
