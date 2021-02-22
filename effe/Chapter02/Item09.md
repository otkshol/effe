## *try-finally*よりも*try-with-resource*を選ぶ

Javaライブラリにはcloseメソッドを呼び出して手作業でcloseする必要がある資源がある
- 例
    - `InputStream`
    - `OutputStream`
    - `java.sql.Connection`

クローズ漏れがあると、性能劣化の原因になる。
セーフティネットとしてファイナライザがあるが、うまく働かない

---
## 資源をcloseする方法
#### ケース：ファイルパスを引数に対象ファイルの1行目を返すメソッドを考える（参考）[BufferedReaderのリファレンス](https://docs.oracle.com/javase/jp/8/docs/api/java/io/BufferedReader.html)

```
// try-finallyの例 (歴史的に使われていたが現在は最善の方法ではない)

static String firstLineOfFile(String path) throws IOExpecton {
    BufferedReader br = new BufferedReader(new FileReader(path));
    try {
        return br.readLine();
    } finally {
        br.close();
    }
}

```
引数が１つの場合、try-finallyの例は悪くなさそうだが引数が複数のケースを考えるとデメリットがみえてくる

#### ケース：ファイルパスを引数に対象ファイルの1行目を返すメソッドで考える（参考）[BufferedReaderのリファレンス](https://docs.oracle.com/javase/jp/8/docs/api/java/io/BufferedReader.html)

```
// 複数資源に対するtry-finallyは不格好になる

static void copy(String src, String dst) throws IOExpecton {
    InputStream in = new FileInputStream(src);
    try {
        OutputStream out = new FileOutputStream(dst);
        try {
            // BUFFER_SIZEは別で定義？
            byte[] buf = new byte[BUFFER_SIZE];
            int n;
            // これ以上読める文字列がないときにintの-1が返される。これ以上読めるかどうかを表すためにcharをintで返す背景がある
            while ((n = in.read(buf)) >= 0)
                out.write(buf, 0, n);
        } finally {
            out.close();
        }
    } finally {
        in.close();
    }
}

```

優れたプログラマ（Joshua Blochのドヤ込）でもcloseメソッドの使い方を誤ってしまう。
- Joshua Blochの著『*java Puzzulers*』の88ページ
- 2007年時点のJavaライブラリの3分の2

#### try-finallyの欠点
- tryブロックとfinallyブロックの両方で、例外をスローできるので**finallyブロックの例外がtryブロックの例外を隠蔽してしまう**ケースがある。メンテナンスが困難になる要因になりえる。
---

Java７から*try-with-resource*が導入されて、上記の課題が解決された。構文を使用するためには[`AutoCloseableインタフェース`](https://docs.oracle.com/javase/jp/8/docs/api/java/lang/AutoCloseable.html)を実装する必要がある。

```
// 1つ目の例をtry-with-resourceで書き直す

static String firstLineOfFile(String path) throws IOExpecton { 
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        return br.readLine();
    } 
}

```

```
// 2つ目の例をtry-with-resourceで書き直す

static void copy(String src, String dst) throws IOExpecton { 
    try (InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst)){
            byte[] buf = new byte[BUFFER_SIZE];
            int n;
            while ((n = in.read(buf)) >= 0)
                out.write(buf, 0, n);
        }
    }

```
### try-finallyとの差分
- finallyブロックが不要
- tryブロックの中身を引数扱いとして書く
- tryブロックと（省略されている）finallyブロックの両方で、例外が発生するとtryブロックの例外がスローされる
    - `Throwable`の`getSuppressed`メソッドを使えば隠蔽されている例外にアクセス可能


## まとめ
**`try-with-resource`の方が圧倒的に便利なので使いましょう！**
### メリット
- finallyブロックの記述が必要なくなり可読性がよくなる
- 最初に発生した例外を投げるのでメンテナンスがしやすい
- close漏れがなくなる



