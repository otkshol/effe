# ラムダ式と関数オブジェクト (スッキリJava実践編第6章ざっくりまとめ)
## 関数オブジェクト
### 第1級オブジェクト
プログラムの実行中に生成したり、代入できるもの

（例）
- データ
- データ構造
- インスタンス

**Java8からは関数(*function*)** が追加された

（関数の例）
- メソッド

### 関数オブジェクト
```
変数へのメソッド参照の格納

変数名 = クラス名::そのクラスの静的メソッド名
変数名 = インスタンス変数名::そのインスタンスのメソッド名
```

**SAMインターフェース（*single-abstract-method interface*）**　
- 抽象メソッドを1つしか含まないインターフェースのこと

プログラマでいちいちインターフェース宣言をしなくて良いように、`java.util.function`のパッケージのAPIとしてインターフェースが用意されている。

---
## ラムダ式
### ラムダ式の構文
```
(型 引数名 1,型 引数名 2, …) -> {
    処理 1;
    処理 2;
    ︰
    return 戻り値;
}
```
### ラムダ式の例
```
// Heroインスタンスを受け取り、HPを返す
(Hero h) -> { return h.getHp(); }

// 何も受け取らず、現在日時を返す
() -> { return new java.util.Date(); }

// long配列を受け取り、そのコピーを作り、内容を並べ替えて返す
(long[] array ) -> {
    long[] array2 = java.util.Arrays.copyOf(array, array.length);
    java.util.Arrays.sort(array2);
    return array2;
}

// 関数オブジェクトを受け取り、2回呼び出した合計を返す
(IntBinaryOperator func, int a, int b) -> {
    int result = func.applyAsInt(a,b) + func.applyAsInt(a,b);
    return result;
}
```
### ラムダ式の省略
- 代入時はラムダ式の引数宣言における型を省略可能
- ラムダ式の引数が１つの場合は丸カッコを省略可能
- ラムダ式が単一のreturn文の場合、波カッコとreturnを省略可能

---
## Streamによる関数の活用
### 関数オブジェクトのメリット
`java.util.Collection`を実装しているすべてのクラスはJava8から`stream()`メソッドを持つようになった。
このメソッドは各要素に対する一括処理や集計するメソッドをもった`java.util.stream.Stream`のインスタンス
を得ることができる。
代表的なメソッドは`forEach()`メソッドでコレクションの各要素に対して、関数を繰り返し実行してくれる。
リファレンスは[こちら](https://docs.oracle.com/javase/jp/8/docs/api/java/util/stream/Stream.html)

### 並列処理による効率化
`paralleStream()`を使用することで並列処理を可能にして性能改善に大きく貢献できる。
-  バッチ処理の並列化で使用されている

